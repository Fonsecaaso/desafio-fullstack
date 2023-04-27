package com.hubla.DesafioBack.repository;

import com.hubla.DesafioBack.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryI {
    @Autowired
    UserRepository userRepository;


    public void save(User user, Double mudanca){
        User usuarioExistente = userRepository.findByNome(user.getNome()); // Busca o usuário pelo ID
        if(usuarioExistente != null) {
            usuarioExistente.setSaldo(usuarioExistente.getSaldo() + mudanca); // Atualiza o saldo
            userRepository.save(usuarioExistente);
        }else{
            userRepository.save(user);
        }
    }

    public User findByName(String name){
        return userRepository.findByNome(name);

    }

    public List<User> findAll(){
        return userRepository.findAll();
    }
}
