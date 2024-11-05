package br.com.nexus.goat.models;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import br.com.nexus.goat.enums.OrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity(name = "tb_orders")
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer status;
    private String paymentMethod;
    private Long orderNumber;

    @CreationTimestamp
    private Instant orderDate;

    @ManyToOne
    private Address address;

    @OneToMany(mappedBy = "id.order")
    private Set<OrderProduct> products = new HashSet<>();

    public Order() {
    }

    public Order(Long id, OrderStatus status, String paymentMethod, Long orderNumber) {
        this.id = id;
        setStatus(status);
        this.paymentMethod = paymentMethod;
        this.orderNumber = orderNumber;
    }

    public Long getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return OrderStatus.valueOf(status);
    }

    public void setStatus(OrderStatus status) {
        if (status != null)
            this.status = status.getCode();
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<OrderProduct> getProducts(){
        return products;
    }

    public Double getTotal() {
        double sum = 0.0;
        for (OrderProduct x : products) {
            sum += x.getSubTotal();
        }
        return sum;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Order other = (Order) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", status=" + status + ", paymentMethod=" + paymentMethod + ", orderNumber="
                + orderNumber + ", orderDate=" + orderDate + ", address=" + address + ", products=" + products + "]";
    }
}
