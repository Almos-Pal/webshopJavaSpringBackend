package hu.wv.webshopbackend.user;

import hu.wv.webshopbackend.exception.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private static PasswordEncoder passwordEncoder;

    public UserService(final UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(final Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> deleteById(final Long id) {

        userRepository.deleteById(id);
        return userRepository.findAll();
    }

    public User updateUser(Long id, Map<String, Object> fields) {
        Optional<User> existingProduct = userRepository.findById(id);

        if (existingProduct.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(User.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, existingProduct.get(), value);
            });
            return userRepository.save(existingProduct.get());
        }
        return null;
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
