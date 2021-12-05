package hermanos.bistro.model;

import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class SubCategory {
	@Id
	private Long id;
	private String name;
	
}
