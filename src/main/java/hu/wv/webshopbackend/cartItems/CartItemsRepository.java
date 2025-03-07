package hu.wv.webshopbackend.cartItems;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems,Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE cart_items_seq SET next_val = 1", nativeQuery = true)
    void resetSequence();
}
