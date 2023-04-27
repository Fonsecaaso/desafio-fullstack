package com.hubla.DesafioBack.repository;

import com.hubla.DesafioBack.entity.Venda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VendaRepositoryI {
    @Autowired
    VendaRepository vendaRepository;

    @Autowired
    CursoRepository cursoRepository;
    @Autowired
    UserRepository userRepository;

    public void save(Venda venda) {
        vendaRepository.save(venda);
    }

}
