package org.acme.payment.model;

import jakarta.persistence.*;
import org.acme.order.model.Order;
import org.acme.shared.model.BaseEntity;

import java.math.BigDecimal;

@Entity
@Table(name = "payments")
public class Payment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    public Order order;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    public BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "method", nullable = false, length = 10)
    public PaymentMethod method;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 10)
    public PaymentStatus status;

    @Column(name = "cash_tendered", precision = 10, scale = 2)
    public BigDecimal cashTendered;

    @Column(name = "change_amount", precision = 10, scale = 2)
    public BigDecimal changeAmount;

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public PaymentMethod getMethod() { return method; }
    public void setMethod(PaymentMethod method) { this.method = method; }

    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }

    public BigDecimal getCashTendered() { return cashTendered; }
    public void setCashTendered(BigDecimal cashTendered) { this.cashTendered = cashTendered; }

    public BigDecimal getChangeAmount() { return changeAmount; }
    public void setChangeAmount(BigDecimal changeAmount) { this.changeAmount = changeAmount; }
}