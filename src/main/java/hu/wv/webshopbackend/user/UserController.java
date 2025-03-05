package hu.wv.webshopbackend.user;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(final UserService userService, UserRepository userRepository) {
        this.userService = userService;
    }

    @GetMapping()
    public List<User> findAll() {

        return  userService.findAll();
    }

    @GetMapping("/user/{email}")
    public User findUserByEmail(@PathVariable String email) {
        return userService.findUserByEmail(email);
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping()
    public User createUser(@RequestBody final User user) {
        return  userService.createUser(user);
    }

    @DeleteMapping("/{id}")
    public List<User> deleteUser(@PathVariable final Long id) {
        return userService.deleteById(id);
    }


    @PatchMapping("/{id}")
    public User updateUser(@PathVariable final Long id, @RequestBody final Map<String,Object> fields) {
        return userService.updateUser(id,fields);
    }
}
