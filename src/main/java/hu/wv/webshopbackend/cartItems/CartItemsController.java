package hu.wv.webshopbackend.cartItems;


import hu.wv.webshopbackend.products.Product;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cart")
public class CartItemsController {
    private final CartItemsService cartItemsService;


    public CartItemsController(CartItemsService cartItemsService) {
        this.cartItemsService = cartItemsService;
    }

    @PostMapping("/{userId}")
    public CartItems addItemToCart(@PathVariable final Long userId,  @RequestBody final CartItemCreateDTO cartItem) {
        return cartItemsService.addItemToCart(userId,cartItem);
    }
}
