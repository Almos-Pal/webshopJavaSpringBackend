package hu.wv.webshopbackend.config;

import com.github.javafaker.Faker;
import hu.wv.webshopbackend.products.Product;
import hu.wv.webshopbackend.products.ProductRepository;
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
    private final ProductRepository productRepository;
    private static PasswordEncoder passwordEncoder;
    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
                this.productRepository = productRepository;


    }


    @Override
    public void run(String... args) throws Exception {
         userRepository.deleteAll();
         productRepository.deleteAll();

         userRepository.resetSequence();
         productRepository.resetSequence();
         User adminUser = buildAdminUser();
        List<User> users = buildUsers();
        List<Product> products = buildProducts();
        userRepository.save(adminUser);
        userRepository.saveAll(users);
        productRepository.saveAll(products);
    }

    private static User buildAdminUser() {

      return User.builder()
              .username("Admin")
              .admin(false)
              .password(passwordEncoder.encode("Password"))
              .admin(true)
              .email("adm@adm.hu").build();


    }
    private static List<Product> buildProducts() {
        final List<Product> products = new ArrayList<>();
        Faker faker = new Faker();
        for (int i = 0; i < 10; i++) {
            String productName = faker.book().title();
            String productDescription = faker.lorem().paragraph(10);
            int productPrice = faker.number().numberBetween(1, 100);
            products.add(Product.builder()
                    .productName(productName)
                    .price(productPrice)
                    .description(productDescription)
        .build());
        }
        return products;
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
