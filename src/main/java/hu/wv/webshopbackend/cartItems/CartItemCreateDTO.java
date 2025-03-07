package hu.wv.webshopbackend.cartItems;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemCreateDTO {
    private int quantity;
    private Long itemId;



}
