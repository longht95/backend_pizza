package hermanos.bistro.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne
	private Item item;
	
	@OneToOne
	private Item itemHalf;
	
	@ManyToOne
	@JoinColumn(name="orderCart_id", nullable=true)
	private OrderCart orderCart;
	
	@OneToMany(mappedBy = "orderDetails")
	private List<Topping> toppings;
	
	private float price;
	
}
