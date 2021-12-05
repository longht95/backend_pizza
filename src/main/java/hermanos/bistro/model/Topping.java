package hermanos.bistro.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Topping {
	@Id
	private Long id;
	private String name;
	private float price;
	private boolean isCheese;
	@ManyToOne
	@JoinColumn(name="orderDetails_id", nullable=true)
	private OrderDetails orderDetails;
}
