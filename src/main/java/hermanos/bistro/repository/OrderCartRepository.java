package hermanos.bistro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hermanos.bistro.model.OrderCart;

public interface OrderCartRepository extends JpaRepository<OrderCart, Long>{

}
