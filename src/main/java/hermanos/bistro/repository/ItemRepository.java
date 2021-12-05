package hermanos.bistro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hermanos.bistro.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{
	List<Item> findByCategory_Id(Long id);
}
