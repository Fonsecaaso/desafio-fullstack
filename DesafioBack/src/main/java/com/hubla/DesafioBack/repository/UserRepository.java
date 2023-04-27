package com.hubla.DesafioBack.repository;

import com.hubla.DesafioBack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.nome = :nome")
    User findByNome(@Param("nome") String nome);

    User save(User user); // Criar usuário dado nome e saldo

    List<User> findAll(); // Buscar dados de todos os usuários
}
