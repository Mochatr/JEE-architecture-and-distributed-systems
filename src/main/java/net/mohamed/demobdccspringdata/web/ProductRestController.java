package net.mohamed.demobdccspringdata.web;

import net.mohamed.demobdccspringdata.entities.Product;
import net.mohamed.demobdccspringdata.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}