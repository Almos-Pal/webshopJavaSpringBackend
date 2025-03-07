package hu.wv.webshopbackend.cartItems;

import hu.wv.webshopbackend.exception.CartItemsNotFoundException;
import hu.wv.webshopbackend.exception.ProductNotFoundException;
import hu.wv.webshopbackend.exception.UserNotFoundException;
import hu.wv.webshopbackend.products.Product;
import hu.wv.webshopbackend.products.ProductRepository;
import hu.wv.webshopbackend.products.ProductService;
import hu.wv.webshopbackend.user.ErrorRes;
import hu.wv.webshopbackend.user.User;
import hu.wv.webshopbackend.user.UserRepository;
import hu.wv.webshopbackend.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
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

    public CartItems getById(final Long id) {
        return cartItemsRepository.findById(id).orElseThrow(() -> (new CartItemsNotFoundException("Cart Item not found")));
    }

    public List<CartItems> findAll() {
        return cartItemsRepository.findAll();
    }

    public List<CartItems> deleteById(final Long id) {
        cartItemsRepository.deleteById(id);
        return  cartItemsRepository.findAll();
    }

    public CartItems updateCartItemsQuantity(final Long id, final UpdateCartDTO quantity) {
        Optional<CartItems> existingCart = cartItemsRepository.findById(id);
        existingCart.ifPresent(cartItems -> cartItems.setQuantity(quantity.getQuantity()));
        return cartItemsRepository.save(existingCart.get());
    }

    public ResponseEntity deleteAllCartItems(final Long userId) {
        List<CartItems>usersAllCartItem = (cartItemsRepository.findAllByUserId(userId).orElseThrow(() -> new UserNotFoundException("User not found")));
        if(usersAllCartItem.stream().count() == 0) {
            ErrorRes errorResponse = new ErrorRes(HttpStatus.NOT_FOUND,"Users cart is empty");
            return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
        }
        cartItemsRepository.deleteAll(usersAllCartItem);
        ErrorRes errorResponse = new ErrorRes(HttpStatus.OK,"Cart items deleted successfully");

        return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
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
