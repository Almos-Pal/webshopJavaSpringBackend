package hu.wv.webshopbackend.config;

import hu.wv.webshopbackend.user.User;
import hu.wv.webshopbackend.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader  implements CommandLineRunner {

    private final UserRepository userRepository;
    private static PasswordEncoder passwordEncoder;
    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;


    }


    @Override
    public void run(String... args) throws Exception {
         userRepository.deleteAll();
         userRepository.resetSequence();
        User adminUser = buildAdminUser();
        List<User> users = buildUsers();
        userRepository.save(adminUser);
        userRepository.saveAll(users);
    }

    private static User buildAdminUser() {

      return User.builder()
              .username("Admin")
              .admin(false)
              .password(passwordEncoder.encode("Password"))
              .admin(true)
              .email("adm@adm.hu").build();


    }

    private static List<User> buildUsers() {
        List<User> users = new ArrayList<>();
        for(int i =0; i < 10; i++) {
              users.add(  User.builder()
                        .username("User" + i)
                        .password(passwordEncoder.encode("Password" + i))
                        .email("user" + i + "@gmail.com")
                        .build());
        }

        return users;

    }
}
