package com.hubla.DesafioBack.service;

import com.hubla.DesafioBack.entity.UserEntity;
import com.hubla.DesafioBack.entity.dto.UserDTO;
import com.hubla.DesafioBack.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public List<UserDTO> getAll() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserDTO> ans = new ArrayList<>();
        for(UserEntity userEntity : userEntities){
            UserDTO dto = new UserDTO();
            BeanUtils.copyProperties(userEntity,dto);
            ans.add(dto);
        }
        return ans;
    }

    public void updateBalance(UserEntity user, double amount){
        if(userRepository.existsByName(user.getName())) {
            Optional<UserEntity> userUpdated = userRepository.findByName(user.getName());
            userUpdated.get().setBalance(userUpdated.get().getBalance() + amount);
            userRepository.save(userUpdated.get());
        }else{
            userRepository.save(user);
        }
    }
}
