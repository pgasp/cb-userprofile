package com.cb.demo.userprofile.controlers;

import com.cb.demo.userprofile.model.UserEntity;
import com.cb.demo.userprofile.services.UserService;
import com.cb.demo.userprofile.services.vo.SimpleUserVO;
import com.cb.demo.userprofile.services.vo.UserFoundImVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class UserControler {
    @Autowired
    private UserService userService;


    @PostConstruct
    private void postConstruct() {
        {

            String[] names = {"Pascal","Olivia", "Alex", "Allex", "Alec", "Charlotte", "Benjamin",
                    "James", "Elijah", "Michael", "Liam", "Emma", "Isabella", "Mia", "Robert", "Maria", "David", "Mary",
                    "George", "Henry", "Thomas", "Joseph", "Samuel", "Elizabeth", "Margaret", "Martha", "Ann", "Catherine"};

            String[] surnames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller",
                    "Davis", "Rodriguez", "Martinez", "Hernandez", "Lopez",  "Gonzalez" };
            String[] country = {"US", "FR", "GB", "ES", "JP",  };

            for(int i=0; i < 10000;i++) {

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
                userService.addUser(userEntity);

            }
        }
    }


    @GetMapping("/findByUsername")
    public UserEntity findByUsername(@RequestParam("username") String userName) {
        return userService.findByUsername(userName);
    }

    @GetMapping("/findById")
    public UserEntity findById(@RequestParam("Id") String id) {
        return userService.getUser(id);
    }

    @PostMapping("/save")
    public UserEntity save(@Valid @RequestBody UserEntity user) {
        return userService.addUser(user);
    }


    @GetMapping(value = "/listActiveUsers")
    public List<SimpleUserVO> listActiveUsers(@RequestParam("firstName") String firstName,
                                                         @RequestParam("countryCode") String countryCode,
                                                         @RequestParam("limit") int limit,
                                                         @RequestParam("offset") int offset) {
        return userService.listActiveUsers(firstName, true, countryCode, limit, offset);
    }

    @GetMapping(value = "/findActiveUsersByFirstName")
    public List<UserEntity> findActiveUsersByFirstName(@RequestParam("firstName") String firstName,
                                                       @RequestParam("countryCode") String countryCode,
                                                       @RequestParam("limit") int limit,
                                                       @RequestParam("offset") int offset) {
        return userService.findActiveUsersByFirstName(firstName, countryCode, limit, offset);
    }

    @GetMapping(value = "/findActiveUsersByFirstNameFTS")
    public List<SimpleUserVO> findActiveUsersByFirstNameFTS(@RequestParam("firstName") String firstName,
                                                       @RequestParam("countryCode") String countryCode,
                                                       @RequestParam("limit") int limit,
                                                       @RequestParam("offset") int offset) {
        return userService.ftsListActiveUsers(firstName, true, countryCode, limit, offset);
    }
    @GetMapping(value = "/scoreActiveUsersByFirstName")
    public List<UserFoundImVO> scoreListActiveUsers(@RequestParam("firstName") String firstName,
                                                    @RequestParam("countryCode") String countryCode,
                                                    @RequestParam("limit") int limit,
                                                    @RequestParam("offset") int offset) {
        return userService.scoreListActiveUsers(firstName, true, countryCode, limit, offset);
    }
}