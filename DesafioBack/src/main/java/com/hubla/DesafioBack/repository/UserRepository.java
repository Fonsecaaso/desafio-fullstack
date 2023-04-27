package com.hubla.DesafioBack.repository;

import com.hubla.DesafioBack.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u WHERE u.name = :name")
    UserEntity findByName(@Param("name") String name);

    UserEntity save(UserEntity userEntity); // Criar usuário dado name e saldo

    List<UserEntity> findAll(); // Buscar dados de todos os usuários
}
