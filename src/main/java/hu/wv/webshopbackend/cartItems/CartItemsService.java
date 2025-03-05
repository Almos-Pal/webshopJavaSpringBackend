package hu.wv.webshopbackend.cartItems;

import org.springframework.stereotype.Service;

@Service
public class CartItemsService {
    private final CartItemsRepository cartItemsRepository;
    public CartItemsService( final CartItemsRepository cartItemsRepository ) {
        this.cartItemsRepository = cartItemsRepository;
    }
}
