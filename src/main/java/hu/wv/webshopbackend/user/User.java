package hu.wv.webshopbackend.user;


import hu.wv.webshopbackend.cartItems.CartItems;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "USERS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class User {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Long id;

    @Column()
    private String username;

    @Column
    private String password;

    @Column(unique = true)
    private String email;

    @Column
    private  boolean isAdmin;


    @OneToMany
    private Set<CartItems> cartItems;


}
