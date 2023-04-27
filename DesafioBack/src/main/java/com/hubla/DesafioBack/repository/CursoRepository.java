package com.hubla.DesafioBack.repository;

import com.hubla.DesafioBack.entity.Curso;
import com.hubla.DesafioBack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    Curso findByNome(String nome);

    @Query("SELECT c.responsavel FROM Curso c WHERE c.nome = :nome")
//    @Query("SELECT c.nome FROM Curso c")
    User findByNomeCurso(@Param("nome") String nome);
}
