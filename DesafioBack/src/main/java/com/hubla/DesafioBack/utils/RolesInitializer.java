package com.hubla.DesafioBack.utils;

import com.hubla.DesafioBack.entity.Role;
import com.hubla.DesafioBack.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RolesInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        Role roleAdmin = new Role();
        roleAdmin.setName("PRODUTOR");
        roleRepository.save(roleAdmin);

        Role roleUser = new Role();
        roleUser.setName("AFILIADO");
        roleRepository.save(roleUser);
    }

}
