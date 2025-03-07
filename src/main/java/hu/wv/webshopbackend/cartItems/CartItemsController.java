package hu.wv.webshopbackend.cartItems;


import hu.wv.webshopbackend.products.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cart")
public class CartItemsController {
    private final CartItemsService cartItemsService;


    public CartItemsController(CartItemsService cartItemsService) {
        this.cartItemsService = cartItemsService;
    }

    @GetMapping("/{id}")
    public CartItems getCartItems(@PathVariable Long id) {
        return cartItemsService.getById(id);
    }
    @GetMapping()
    public List<CartItems> getAllCartItems() {
        return  cartItemsService.findAll();
    }

    @DeleteMapping("/{id}")
    public  List<CartItems>  deleteCartItems(@PathVariable Long id) {
        return cartItemsService.deleteById(id);

    }
    @DeleteMapping("/all/{userId}")
    public ResponseEntity deleteAllCartItems(@PathVariable Long userId) {
       return cartItemsService.deleteAllCartItems(userId);
    }

    @PatchMapping("/{id}")
    public CartItems updateCartItemsQuantity(@PathVariable Long id, @RequestBody UpdateCartDTO quantity) {
        return cartItemsService.updateCartItemsQuantity(id, quantity);
    }

    @PostMapping("/{userId}")
    public CartItems addItemToCart(@PathVariable final Long userId,  @RequestBody final CartItemCreateDTO cartItem) {
        return cartItemsService.addItemToCart(userId,cartItem);
    }
}
