package hu.wv.webshopbackend.products;

import hu.wv.webshopbackend.cartItems.CartItems;
import hu.wv.webshopbackend.cartItems.CartItemsRepository;
import hu.wv.webshopbackend.exception.ProductNotFoundException;
import hu.wv.webshopbackend.user.User;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartItemsRepository cartItemsRepository;
    public ProductService(final ProductRepository productRepository, CartItemsRepository cartItemsRepository) {
        this.productRepository = productRepository;
        this.cartItemsRepository = cartItemsRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProduct( final Long id )  {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }
    public Product createProduct( final Product product ) {
        return productRepository.save( product );
    }
    public Product updateProduct( final Long id, Map<String, Object> fields ) {
        Optional<Product> existingProduct = productRepository.findById(id);

        if (existingProduct.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Product.class, key);
                assert field != null;

                field.setAccessible(true);

                ReflectionUtils.setField(field, existingProduct.get(), value);
            });
            return productRepository.save(existingProduct.get());
        }
        return null;
    }
    public List<Product> deleteProduct(final Long id ) {


       Optional<List<CartItems>> existingCartItems =  cartItemsRepository.findAllByProductId(id);
        existingCartItems.ifPresent(cartItemsRepository::deleteAll);

        productRepository.deleteById(id);
        return productRepository.findAll();
    }
}
