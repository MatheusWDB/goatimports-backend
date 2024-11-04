package br.com.nexus.goat.models.dto;

import java.util.List;

import br.com.nexus.goat.enums.OrderStatus;
import lombok.Data;

@Data
public class OrderDTO {
    private Integer status;
    private String paymentMethod;
    private Long orderNumber;
    private List<Products> products;

    @Data
    public static class Products {
        private Long id;
        private Integer quantity;
    }

    public OrderStatus getStatus() {
        return OrderStatus.valueOf(status);
    }

    public void setStatus(OrderStatus status) {
        if (status != null)
            this.status = status.getCode();
    }
}