package com.cb.demo.userprofile;

import com.cb.demo.userprofile.model.UserEntity;
import com.cb.demo.userprofile.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserCommandLine  implements CommandLineRunner {
    @Autowired
     public UserEntityRepository userEntityRepository ;
    @Override
    public void run(String... args) throws Exception {

        UserEntity user = new UserEntity();
        user.setId("user::"+1);
        user.setCountryCode("Fr");
        user.setUsername("Pascal");
        user.setPassword("password");
        userEntityRepository.save(user);
    }
}
