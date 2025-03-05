package hu.wv.webshopbackend;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private  final  UserRepository userRepository;

    public UserService( final UserRepository userRepository ) {
        this.userRepository = userRepository;

    }
}
