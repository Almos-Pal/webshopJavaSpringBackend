package user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    public UserController( final UserService userService ) {
        this.userService = userService;
    }

    @GetMapping()
    public user.UserService.User getUser() {
        return null;
    }
}
