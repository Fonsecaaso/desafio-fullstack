package com.hubla.DesafioBack.repository;

import com.hubla.DesafioBack.entity.Curso;
import com.hubla.DesafioBack.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CursoRepositoryI {
    @Autowired
    CursoRepository cursoRepository;
//    @Autowired
//    UserRepository userRepository;

    public Curso save(Curso curso){
        Curso c = cursoRepository.findByNome(curso.getNome());
        if(c == null)
            return cursoRepository.save(curso);
        return c;
    }

    public User findByNomeCurso(String nomeCurso) {
        return cursoRepository.findByNomeCurso(nomeCurso);
    }
}
