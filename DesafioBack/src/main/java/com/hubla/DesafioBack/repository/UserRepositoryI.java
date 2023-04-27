package com.hubla.DesafioBack.repository;

import com.hubla.DesafioBack.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryI {
    @Autowired
    UserRepository userRepository;


    public void save(UserEntity userEntity, Double mudanca){
        UserEntity usuarioExistente = userRepository.findByName(userEntity.getName()); // Busca o usuário pelo ID
        if(usuarioExistente != null) {
            usuarioExistente.setSaldo(usuarioExistente.getSaldo() + mudanca); // Atualiza o saldo
            userRepository.save(usuarioExistente);
        }else{
            userRepository.save(userEntity);
        }
    }

    public UserEntity findByName(String name){
        return userRepository.findByName(name);

    }

    public List<UserEntity> findAll(){
        return userRepository.findAll();
    }
}
