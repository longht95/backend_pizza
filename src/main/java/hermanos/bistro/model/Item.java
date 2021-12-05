package hermanos.bistro.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {
	@Id
	private Long id;
	private String name;
	private String description;
	private String srcImg;
	private float price;
	@ManyToOne
	private Category category;
}
