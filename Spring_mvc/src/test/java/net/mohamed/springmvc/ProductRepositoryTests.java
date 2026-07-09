package net.mohamed.springmvc;

import net.mohamed.springmvc.entities.Product;
import net.mohamed.springmvc.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testSaveAndFindByName() {
        Product product = Product.builder()
                .name("Clavier mecanique")
                .description("Clavier mecanique RGB")
                .price(450)
                .quantity(20)
                .build();
        productRepository.save(product);

        Product found = productRepository.findByName("Clavier mecanique");

        assertThat(found).isNotNull();
        assertThat(found.getQuantity()).isEqualTo(20);
    }

    @Test
    void testFindByNameContains() {
        productRepository.save(Product.builder()
                .name("Souris optique")
                .description("Souris optique USB")
                .price(90)
                .quantity(100)
                .build());

        assertThat(productRepository.findByNameContains("Souris")).isNotEmpty();
    }
}
