package net.mohamed.springmvc.repository;

import net.mohamed.springmvc.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByName(String name);

    java.util.List<Product> findByNameContains(String keyword);

    @org.springframework.data.jpa.repository.Query("select p from Product p where p.name like :kw")
    org.springframework.data.domain.Page<Product> search(@Param("kw") String keyword, org.springframework.data.domain.Pageable pageable);
}
