package hermanos.bistro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hermanos.bistro.model.Topping;

public interface ToppingRepository extends JpaRepository<Topping, Long>{
	
}
