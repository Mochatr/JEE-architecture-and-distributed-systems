package net.mohamed.demobdccspringdata.repository;

import net.mohamed.demobdccspringdata.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository <Product, Long>  {
    List<Product> findByNameContains(String kw);
}
