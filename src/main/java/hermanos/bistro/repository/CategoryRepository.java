package hermanos.bistro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hermanos.bistro.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
	
}
