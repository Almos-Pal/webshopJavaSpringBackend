package hu.wv.webshopbackend.config;

import hu.wv.webshopbackend.user.User;
import hu.wv.webshopbackend.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
        User user = buildAdminUser();
        userRepository.save(user);
    }

    private static User buildAdminUser() {

      return User.builder()
              .username("admin")
              .isAdmin(false)
              .password(passwordEncoder.encode("Password"))
              .email("asd").build();


    }
}
