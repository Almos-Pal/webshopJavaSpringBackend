package hu.wv.webshopbackend.products;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {
    private  final ProductService productService;

    public ProductController( final ProductService productService ) {
        this.productService = productService;
    }


    @GetMapping()
    public List<Product> getAllProducts() {
        return  productService.getProducts();
    }
    @GetMapping("/{id}")
    public Product getProduct( @PathVariable final Long id ) {
        return  productService.getProduct(id);
    }
}
