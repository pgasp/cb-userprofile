package com.cb.demo.userprofile.controlers;

import com.cb.demo.userprofile.model.UserEntity;
import com.cb.demo.userprofile.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class UserControler {
    @Autowired
    private UserEntityRepository userEntityRepository;


    @PostConstruct
    private void postConstruct() {
        {

            String[] names = {"Pascal","Olivia", "Alex", "Allex", "Alec", "Charlotte", "Benjamin",
                    "James", "Elijah", "Michael", "Liam", "Emma", "Isabella", "Mia", "Robert", "Maria", "David", "Mary",
                    "George", "Henry", "Thomas", "Joseph", "Samuel", "Elizabeth", "Margaret", "Martha", "Ann", "Catherine"};

            String[] surnames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller",
                    "Davis", "Rodriguez", "Martinez", "Hernandez", "Lopez",  "Gonzalez" };
            String[] country = {"US", "FR", "GB", "ES", "JP",  };

            for(int i=0; i < 300;i++) {

                if(i%100 == 0) {
                    System.out.println("----------- i = "+i);
                }
                UserEntity userEntity = new UserEntity();
                userEntity.setId("user::"+i);
                userEntity.setFirstName(names[ThreadLocalRandom.current().nextInt(0, names.length )]);
                userEntity.setLastName(surnames[ThreadLocalRandom.current().nextInt(0, surnames.length )]);
                userEntity.setTenantId(1);
                userEntity.setCountryCode( country[ThreadLocalRandom.current().nextInt(0, country.length )]);
                userEntity.setUsername("user"+i);
                userEntity.setPassword("secret");
                userEntity.setEnabled(true);
                userEntityRepository.save(userEntity);

            }
        }
    }

    @GetMapping("/findByUsername")
    public UserEntity findByUsername(@RequestParam("username") String userName) {
        return userEntityRepository.findByUsername(userName);
    }

    @PostMapping("/save")
    public UserEntity save(@Valid @RequestBody UserEntity user) {
        return userEntityRepository.save(user);
    }

    @GetMapping(value = "/findActiveUsersByFirstName")
    public List<UserEntity> findActiveUsersByFirstName(@RequestParam("firstName") String firstName,
                                                       @RequestParam("countryCode") String countryCode,
                                                       @RequestParam("limit") int limit,
                                                       @RequestParam("offset") int offset) {
        return userEntityRepository.findActiveUsersByFirstName(firstName, true, countryCode, limit, offset);
    }


}