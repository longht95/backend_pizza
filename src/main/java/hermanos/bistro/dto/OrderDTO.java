package hermanos.bistro.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO {
	private String name;
	private String phone;
	private String address;
	private List<ItemDTO> itemPizza;
}
