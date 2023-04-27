package com.hubla.DesafioBack.service;

import com.hubla.DesafioBack.entity.User;
import com.hubla.DesafioBack.entity.dto.UserDTO;
import com.hubla.DesafioBack.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();
        List<UserDTO> ans = new ArrayList<>();
        for(User user:users){
            UserDTO dto = new UserDTO();
            BeanUtils.copyProperties(user,dto);
            ans.add(dto);
        }
        return ans;
    }
}
