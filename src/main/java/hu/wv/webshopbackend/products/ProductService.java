package hu.wv.webshopbackend.products;

import hu.wv.webshopbackend.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    public ProductService( final ProductRepository productRepository ) {
        this.productRepository = productRepository;
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
}
