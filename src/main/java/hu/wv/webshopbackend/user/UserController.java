package hu.wv.webshopbackend.user;

import hu.wv.webshopbackend.auth.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    public UserController(final UserService userService, UserRepository userRepository, AuthenticationManager authenticationManager,JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
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

    @PostMapping("/register")
    public ResponseEntity<ErrorRes> createUser(@RequestBody final User user) {
        if(userService.existsByEmail(user.getEmail())) {


            ErrorRes errorResponse = new ErrorRes(HttpStatus.CONFLICT,"User with this email already exists.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
         userService.createUser(user);

        return   ResponseEntity.status(HttpStatus.OK).body(new ErrorRes(HttpStatus.OK,"User created"));
    }

    @DeleteMapping("/{id}")
    public List<User> deleteUser(@PathVariable final Long id) {
        return userService.deleteById(id);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserLoginDTO loginReq)  {

        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));
            String email = authentication.getName();
            User user = User.builder().email(email).password(loginReq.getPassword()).build();
            String token = jwtUtil.createToken(user);
            LoginRes loginRes = new LoginRes(email,token);

            return ResponseEntity.ok(loginRes);

        }catch (BadCredentialsException e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST,"Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }catch (Exception e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
    @PatchMapping("/{id}")
    public User updateUser(@PathVariable final Long id, @RequestBody final Map<String,Object> fields) {
        return userService.updateUser(id,fields);
    }
}
