package hu.wv.webshopbackend.products;


import hu.wv.webshopbackend.cartItems.CartItems;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Entity
@Table(name = "PRODUCTS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String productName;

    @Column
    private String description;

    @Column
    private int price;

    @Column
    @Lob
    private byte[] picture;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_items_id", referencedColumnName = "id")
    private CartItems cartItems;

}
