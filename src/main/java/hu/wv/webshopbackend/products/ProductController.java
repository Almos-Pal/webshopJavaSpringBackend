package hu.wv.webshopbackend.products;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("product")
public class ProductController {
    private  final ProductService productService;

    public ProductController( final ProductService productService ) {
        this.productService = productService;
    }
}
