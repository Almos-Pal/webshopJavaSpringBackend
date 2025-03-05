package hu.wv.webshopbackend.user;

import hu.wv.webshopbackend.exception.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private  final  UserRepository userRepository;
    private static PasswordEncoder passwordEncoder;

    public UserService(final UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(final Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User findUserByEmail(final String email) {
        try {
            if(userRepository.findByEmail(email).isPresent()){
                return userRepository.findByEmail(email).get();
            }
            else
                throw new UserNotFoundException("User not found");
        } catch (final UserNotFoundException e) {
            return null;
        }
    }
}
