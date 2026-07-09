package net.mohamed.springmvc;

import net.mohamed.springmvc.entities.Product;
import net.mohamed.springmvc.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMvcApplication.class, args);
    }

    @Bean
    CommandLineRunner start(ProductRepository productRepository) {
        return args -> {
            productRepository.save(Product.builder()
                    .name("Ordinateur portable")
                    .description("Ordinateur portable 15 pouces, 16Go RAM")
                    .price(8500)
                    .quantity(12)
                    .build());
            productRepository.save(Product.builder()
                    .name("Imprimante laser")
                    .description("Imprimante laser monochrome")
                    .price(1200)
                    .quantity(30)
                    .build());
            productRepository.save(Product.builder()
                    .name("Smartphone")
                    .description("Smartphone Android 128Go")
                    .price(3200)
                    .quantity(50)
                    .build());

            productRepository.findAll().forEach(System.out::println);
        };
    }
}
