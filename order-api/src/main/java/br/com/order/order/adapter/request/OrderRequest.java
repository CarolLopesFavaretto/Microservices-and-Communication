package br.com.order.order.adapter.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private Long userId;
    private String itemDescription;
    private Integer itemQuantity;
    private Double itemPrice;
    private Double totalValue;
}
