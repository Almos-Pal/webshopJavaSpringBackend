package hu.wv.webshopbackend.cartItems;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems,Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE cart_items_seq SET next_val = 1", nativeQuery = true)
    void resetSequence();

   Optional<List<CartItems>> findAllByUserId(final Long userId);
   Optional<List<CartItems>> findAllByProductId(final Long productId);




}
