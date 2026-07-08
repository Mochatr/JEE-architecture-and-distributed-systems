package net.mohamed.demobdccspringdata.web;

import net.mohamed.demobdccspringdata.entities.Product;
import net.mohamed.demobdccspringdata.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductRestController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    public List<Product> allProducts(){
        return productRepository.findAll();
    }

    @GetMapping("/products/{id}")
    public Product findById(@PathVariable Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product not found"));
    }

    @GetMapping("/find/products")
    public List<Product> findProducts(String kw){
        return productRepository.findByNameContains(kw);
    }

    @PostMapping("/products")
    public Product save(@RequestBody Product product){
        return productRepository.save(product);
    }
}