package hu.wv.webshopbackend.user;

import hu.wv.webshopbackend.cartItems.CartItems;
import hu.wv.webshopbackend.cartItems.CartItemsRepository;
import hu.wv.webshopbackend.exception.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CartItemsRepository cartItemsRepository;
    private static PasswordEncoder passwordEncoder;

    public UserService(final UserRepository userRepository, CartItemsRepository cartItemsRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.cartItemsRepository = cartItemsRepository;
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


        Optional<List<CartItems>> existingCartItems =  cartItemsRepository.findAllByUserId(id);
        existingCartItems.ifPresent(cartItemsRepository::deleteAll);

        userRepository.deleteById(id);
        return userRepository.findAll();
    }

    public User updateUser(Long id, Map<String, Object> fields) {
        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(User.class, key);
                assert field != null;

                field.setAccessible(true);
                ReflectionUtils.setField(field, existingUser.get(), value);
            });
            return userRepository.save(existingUser.get());
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        UserDetails userDetails =
                org.springframework.security.core.userdetails.User.builder()
                        .username(user.get().getEmail())
                        .password(user.get().getPassword())
                        .roles(roles.toArray(new String[0]))
                        .build();
        return userDetails;
    }

    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
