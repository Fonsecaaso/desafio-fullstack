package com.hubla.DesafioBack.repository;

import com.hubla.DesafioBack.entity.Curso;
import com.hubla.DesafioBack.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    Curso findByName(String name);

    @Query("SELECT c.responsavel FROM Curso c WHERE c.name = :name")
    UserEntity findByNameCurso(@Param("name") String name);
}
