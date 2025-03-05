package hu.wv.webshopbackend.products;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository< Product, Long> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE products_seq SET next_val = 1", nativeQuery = true)
    void resetSequence();
}
