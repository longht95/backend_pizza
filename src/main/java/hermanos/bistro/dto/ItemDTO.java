package hermanos.bistro.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemDTO {
	private Long id;
	private String name;
	private ItemDTO itemHalf;
	private List<ToppingDTO> toppings;
}
