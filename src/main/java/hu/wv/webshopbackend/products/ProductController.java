package hu.wv.webshopbackend.products;

import org.springframework.web.bind.annotation.*;

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
    @PostMapping()
    public Product addProduct( @RequestBody final Product product ) {
        return  productService.createProduct(product);
    }
}
