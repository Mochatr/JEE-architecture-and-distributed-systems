package net.mohamed.demobdccspringdata;

import net.mohamed.demobdccspringdata.entities.Product;
import net.mohamed.demobdccspringdata.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoBdccSpringDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoBdccSpringDataApplication.class, args);
    }

    @Bean
    CommandLineRunner start(ProductRepository productRepository){
        return args -> {
            Product p1 = new Product();
            p1.setName("Phone");
            p1.setPrice(6700);
            p1.setQuantity(3);

            Product p2 = new Product(null, "Laptop", 8700, 7);
            Product p3 = Product.builder()
                    .name("Headphone").price(1500).quantity(15)
                    .build();
            productRepository.save(p1);
            productRepository.save(p2);
            productRepository.save(p3);

        };
    }
}