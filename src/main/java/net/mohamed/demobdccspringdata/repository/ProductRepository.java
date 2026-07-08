package net.mohamed.demobdccspringdata.repository;

import net.mohamed.demobdccspringdata.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository <Product, Long>  {
    
}
