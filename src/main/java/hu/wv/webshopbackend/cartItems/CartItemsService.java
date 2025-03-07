package hu.wv.webshopbackend.cartItems;

import hu.wv.webshopbackend.exception.ProductNotFoundException;
import hu.wv.webshopbackend.exception.UserNotFoundException;
import hu.wv.webshopbackend.products.Product;
import hu.wv.webshopbackend.products.ProductRepository;
import hu.wv.webshopbackend.products.ProductService;
import hu.wv.webshopbackend.user.User;
import hu.wv.webshopbackend.user.UserRepository;
import hu.wv.webshopbackend.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class CartItemsService {

    private final CartItemsRepository cartItemsRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    public CartItemsService(CartItemsRepository cartItemsRepository, CartItemsRepository cartItemsRepository1, UserRepository userRepository, ProductService productService, ProductRepository productRepository) {
        this.cartItemsRepository = cartItemsRepository1;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public CartItems addItemToCart( Long userId, CartItemCreateDTO cartItemBody) {
        System.out.println(cartItemBody);
        Optional<Product> exsistingProduct = Optional.ofNullable(productRepository.findById(cartItemBody.getItemId()).orElseThrow(() -> new ProductNotFoundException("Product not found")));
        Optional<User> existingUser = Optional.ofNullable(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found")));



        return cartItemsRepository.save(CartItems.builder()
                        .quantity(cartItemBody.getQuantity())
                        .product(exsistingProduct.orElseThrow(() -> new ProductNotFoundException("Product not found")))
                .user(existingUser.orElseThrow(() -> new UserNotFoundException("User not found")))
                .build());

    }
}
