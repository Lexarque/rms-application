package org.acme.inventories.dto;

public class CreateInventoryItemRequest {
    public String sku;
    public String name;
    public String category;
    public String unit;
    public Integer qtyOnHand;
    public Integer reorderLevel;
    public String status;
}
