package hu.wv.webshopbackend.products;

import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    public ProductService( final ProductRepository productRepository ) {
        this.productRepository = productRepository;
    }
}
