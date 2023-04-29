package com.hubla.DesafioBack.repository;

import com.hubla.DesafioBack.entity.Product;
import com.hubla.DesafioBack.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Boolean existsByName(String name);
    Product findByName(String name);
    @Query("SELECT p.owner FROM Product p WHERE p.name = :name")
    Optional<UserEntity> findUserByProduct(@Param("name") String name);
}
