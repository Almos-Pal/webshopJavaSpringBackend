package hu.wv.webshopbackend.products;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    @DeleteMapping("/{id}")
    public List<Product> deleteProduct( @PathVariable final Long id ) {
        return productService.deleteProduct(id);
    }
    @PatchMapping("/{id}")
    public Product updateProduct( @PathVariable final Long id, @RequestBody Map<String, Object> fields ) {
        return  productService.updateProduct(id, fields);
    }
}
