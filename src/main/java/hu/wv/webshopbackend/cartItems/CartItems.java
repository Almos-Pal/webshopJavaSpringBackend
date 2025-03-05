package hu.wv.webshopbackend.cartItems;


import hu.wv.webshopbackend.products.Product;
import hu.wv.webshopbackend.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CART_ITEMS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItems {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;

    @Column
    private int quantity;

    @ManyToOne
    private User user;

    @JoinColumn(name = "cart_items_id", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.ALL )

    private  Product product;




}
