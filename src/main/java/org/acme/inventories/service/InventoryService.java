package org.acme.inventories.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.acme.inventories.dto.CreateInventoryItemRequest;
import org.acme.inventories.dto.CreateInventoryTransactionRequest;
import org.acme.inventories.dto.UpdateInventoryItemRequest;
import org.acme.inventories.model.InventoryItem;
import org.acme.inventories.model.InventoryStatus;
import org.acme.inventories.model.InventoryTransaction;
import org.acme.inventories.model.InventoryTxnType;
import org.acme.inventories.repository.InventoryItemRepository;
import org.acme.inventories.repository.InventoryTransactionRepository;

import java.util.List;
import java.util.Locale;

@ApplicationScoped
public class InventoryService {

    private final InventoryItemRepository itemRepository;
    private final InventoryTransactionRepository transactionRepository;

    public InventoryService(InventoryItemRepository itemRepository, InventoryTransactionRepository transactionRepository) {
        this.itemRepository = itemRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<InventoryItem> listItems(String search, String category, String status, Boolean lowStock) {
        List<InventoryItem> items = itemRepository.listAll();
        return items.stream()
                .filter(item -> search == null || search.isBlank()
                        || containsIgnoreCase(item.sku, search)
                        || containsIgnoreCase(item.name, search))
                .filter(item -> category == null || category.isBlank()
                        || equalsIgnoreCase(item.category, category))
                .filter(item -> status == null || status.isBlank()
                        || item.status.name().equalsIgnoreCase(status))
                .filter(item -> lowStock == null || !lowStock || item.qtyOnHand <= item.reorderLevel)
                .toList();
    }

    public InventoryItem getItemById(Long id) {
        return itemRepository.findByIdOptional(id)
                .orElseThrow(() -> badRequest(Response.Status.NOT_FOUND, "Inventory item not found"));
    }

    @Transactional
    public InventoryItem createItem(CreateInventoryItemRequest request) {
        validateCreateItemRequest(request);

        String normalizedSku = request.sku.trim().toUpperCase(Locale.ROOT);
        if (itemRepository.findBySku(normalizedSku).isPresent()) {
            throw badRequest(Response.Status.CONFLICT, "SKU already exists");
        }

        InventoryItem item = new InventoryItem();
        item.sku = normalizedSku;
        item.name = request.name.trim();
        item.category = request.category.trim();
        item.unit = request.unit.trim();
        item.qtyOnHand = nonNullOrDefault(request.qtyOnHand, 0);
        item.reorderLevel = nonNullOrDefault(request.reorderLevel, 0);
        item.status = parseStatus(request.status, InventoryStatus.ACTIVE);

        itemRepository.persist(item);
        return item;
    }

    @Transactional
    public InventoryItem updateItem(Long id, UpdateInventoryItemRequest request) {
        if (request == null) {
            throw badRequest(Response.Status.BAD_REQUEST, "Request body is required");
        }

        InventoryItem item = getItemById(id);

        if (request.name != null) {
            item.name = requireText(request.name, "name");
        }
        if (request.category != null) {
            item.category = requireText(request.category, "category");
        }
        if (request.unit != null) {
            item.unit = requireText(request.unit, "unit");
        }
        if (request.reorderLevel != null) {
            if (request.reorderLevel < 0) {
                throw badRequest(Response.Status.BAD_REQUEST, "reorderLevel cannot be negative");
            }
            item.reorderLevel = request.reorderLevel;
        }
        if (request.status != null) {
            item.status = parseStatus(request.status, item.status);
        }

        return item;
    }

    @Transactional
    public void deactivateItem(Long id) {
        InventoryItem item = getItemById(id);
        item.status = InventoryStatus.INACTIVE;
    }

    @Transactional
    public InventoryTransaction createTransaction(Long itemId, CreateInventoryTransactionRequest request) {
        validateTransactionRequest(request);

        InventoryItem item = getItemById(itemId);
        InventoryTxnType txnType = parseTxnType(request.txnType);
        int quantity = request.quantity;

        int currentQty = item.qtyOnHand;
        int newQty;

        if (txnType == InventoryTxnType.IN) {
            newQty = currentQty + quantity;
        } else if (txnType == InventoryTxnType.OUT) {
            newQty = currentQty - quantity;
            if (newQty < 0) {
                throw badRequest(Response.Status.BAD_REQUEST, "Insufficient stock for OUT transaction");
            }
        } else {
            String note = request.note == null ? "" : request.note.toLowerCase(Locale.ROOT);
            boolean decrease = note.contains("-") || note.contains("decrease") || note.contains("deduct");
            newQty = decrease ? currentQty - quantity : currentQty + quantity;
            if (newQty < 0) {
                throw badRequest(Response.Status.BAD_REQUEST, "Adjustment cannot produce negative stock");
            }
        }

        item.qtyOnHand = newQty;

        InventoryTransaction txn = new InventoryTransaction();
        txn.item = item;
        txn.txnType = txnType;
        txn.quantity = quantity;
        txn.note = trimToNull(request.note);
        txn.refNo = trimToNull(request.refNo);
        txn.createdBy = trimToNull(request.createdBy);

        transactionRepository.persist(txn);
        return txn;
    }

    public List<InventoryTransaction> listTransactions(Long itemId, String txnType) {
        List<InventoryTransaction> transactions = transactionRepository.listAll();
        return transactions.stream()
                .filter(txn -> itemId == null || txn.item.id.equals(itemId))
                .filter(txn -> txnType == null || txnType.isBlank()
                        || txn.txnType.name().equalsIgnoreCase(txnType))
                .toList();
    }

    public List<InventoryTransaction> listItemTransactions(Long itemId) {
        getItemById(itemId);
        return transactionRepository.find("item.id = ?1 order by createdAt desc", itemId).list();
    }

    private void validateCreateItemRequest(CreateInventoryItemRequest request) {
        if (request == null) {
            throw badRequest(Response.Status.BAD_REQUEST, "Request body is required");
        }

        requireText(request.sku, "sku");
        requireText(request.name, "name");
        requireText(request.category, "category");
        requireText(request.unit, "unit");

        if (request.qtyOnHand != null && request.qtyOnHand < 0) {
            throw badRequest(Response.Status.BAD_REQUEST, "qtyOnHand cannot be negative");
        }
        if (request.reorderLevel != null && request.reorderLevel < 0) {
            throw badRequest(Response.Status.BAD_REQUEST, "reorderLevel cannot be negative");
        }
    }

    private void validateTransactionRequest(CreateInventoryTransactionRequest request) {
        if (request == null) {
            throw badRequest(Response.Status.BAD_REQUEST, "Request body is required");
        }

        parseTxnType(request.txnType);

        if (request.quantity == null || request.quantity <= 0) {
            throw badRequest(Response.Status.BAD_REQUEST, "quantity must be > 0");
        }
    }

    private InventoryStatus parseStatus(String value, InventoryStatus defaultStatus) {
        if (value == null || value.isBlank()) {
            return defaultStatus;
        }
        try {
            return InventoryStatus.valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            throw badRequest(Response.Status.BAD_REQUEST, "Invalid status. Allowed: ACTIVE, INACTIVE");
        }
    }

    private InventoryTxnType parseTxnType(String value) {
        if (value == null || value.isBlank()) {
            throw badRequest(Response.Status.BAD_REQUEST, "txnType is required");
        }
        try {
            return InventoryTxnType.valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            throw badRequest(Response.Status.BAD_REQUEST, "Invalid txnType. Allowed: IN, OUT, ADJUST");
        }
    }

    private String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw badRequest(Response.Status.BAD_REQUEST, fieldName + " is required");
        }
        return value.trim();
    }

    private Integer nonNullOrDefault(Integer value, int defaultValue) {
        return value == null ? defaultValue : value;
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private boolean containsIgnoreCase(String source, String search) {
        return source != null && source.toLowerCase(Locale.ROOT).contains(search.toLowerCase(Locale.ROOT));
    }

    private boolean equalsIgnoreCase(String left, String right) {
        return left != null && left.equalsIgnoreCase(right);
    }

    private WebApplicationException badRequest(Response.Status status, String message) {
        return new WebApplicationException(Response.status(status)
                .entity(new ErrorMessage(message))
                .build());
    }

    public record ErrorMessage(String message) {
    }
}
