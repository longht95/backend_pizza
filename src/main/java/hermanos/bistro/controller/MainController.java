package hermanos.bistro.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hermanos.bistro.dto.ItemDTO;
import hermanos.bistro.dto.OrderDTO;
import hermanos.bistro.model.Category;
import hermanos.bistro.model.Item;
import hermanos.bistro.model.OrderCart;
import hermanos.bistro.model.OrderDetails;
import hermanos.bistro.model.Topping;
import hermanos.bistro.repository.CategoryRepository;
import hermanos.bistro.repository.ItemRepository;
import hermanos.bistro.repository.OrderCartRepository;
import hermanos.bistro.repository.ToppingRepository;
import hermanos.bistro.service.EmailServiceImpl;

@CrossOrigin(maxAge = 3600)
@RestController
public class MainController {
	@Autowired
	public CategoryRepository categoryRepository;
	@Autowired
	public ItemRepository itemRepository;

	@Autowired
	public ToppingRepository toppingRepository;
	
	@Autowired
	public OrderCartRepository orderCartRepository;
	
	@Autowired
	public EmailServiceImpl emailServiceImpl;

	@GetMapping("/init")
	public Item init() {
		List<Topping> listTopping = new ArrayList<>();
		listTopping.add(new Topping(1L, "Phô mai Burrata nhà làm (75g)", 50000, true, null));
		listTopping.add(new Topping(2L, "Phô mai Burrata nhà làm (150g)", 150000, true, null));

		listTopping.add(new Topping(3L, "Nấm (40g)", 50000, false, null));

		toppingRepository.saveAll(listTopping);

		List<Category> listCategory = new ArrayList<>();
		listCategory.add(new Category(1L, "Wood-fired Pizza"));
		listCategory.add(new Category(2L, "Pasta"));
		listCategory.add(new Category(3L, "Tacos"));
		listCategory.add(new Category(4L, "Nachos"));
		listCategory.add(new Category(5L, "Grilled & Fired"));
		listCategory.add(new Category(6L, "Salad"));
		listCategory.add(new Category(7L, "Burger"));
		listCategory.add(new Category(8L, "Drinks"));
		listCategory.add(new Category(9L, "Craft Beer"));
		categoryRepository.saveAll(listCategory);

		List<Item> listItem = new ArrayList<>();
		listItem.add(new Item(1L, "Bạch Tuộc kiểu Gracia từ Tây Ban Nha Pizza",
				"Bạch tuộc mềm, xốt ớt bột, các loại hạt, nụ bạch hoa và xốt ngò, nụ bạch hoa và xốt ngò",
				"https://storage.googleapis.com/delivery-system-v2/02-06-2021%20Image/10010012.png", 260000,
				listCategory.get(0)));
		listItem.add(new Item(2L, "Xúc xích cay tự làm Pizza",
				"Xúc xích thảo mộc nhà làm, tiêu xanh, bông cải xanh, xốt cà chua, phô mai Mozzarella, Grana Padano",
				"https://storage.googleapis.com/delivery-system-v2/02-06-2021%20Image/10020006.png", 250000,
				listCategory.get(0)));
		listItem.add(new Item(3L, "Hawaiian 4P’s kiểu Đà Nẵng Pizza",
				"Xốt ớt mayonnaise ngọt, giăm bông, dứa, hành tây, cà chua bi, phô mai Mascarpone, cỏ xạ hương",
				"https://storage.googleapis.com/delivery-system-v2/02-06-2021%20Image/10020007.png", 250000,
				listCategory.get(0)));
		listItem.add(new Item(4L, "Trứng và cải bó xôi với Phô mai Ricotta Pizza",
				"Trứng, cải bó xôi và bơ tỏi, phô mai Ricotta, tiêu đen",
				"https://storage.googleapis.com/delivery-system-v2/Pizza/10000009%20-%20Extra%20Parma%20Ham%20Margherita.png",
				250000, listCategory.get(0)));
		listItem.add(new Item(5L, "Thịt bò cay kiểu Kebab Pizza", "Thịt bò ướp cay, xốt cà chua, ớt xanh, hạt thì là",
				"https://storage.googleapis.com/delivery-system-v2/02-06-2021%20Image/10010010.png", 250000,
				listCategory.get(0)));
		listItem.add(new Item(6L, "Pizza Phô mai Burrata Margherita thịt nguội",
				"Xốt cà chua cùng phô mai Burrata và thịt nguội Ý Parma",
				"https://storage.googleapis.com/delivery-system-v2/Pizza/10000003.png", 270000, listCategory.get(0)));
		itemRepository.saveAll(listItem);
		System.out.println(itemRepository.findAll().toString());
		return null;
	}

	@GetMapping("/getListItem/{id}")
	public List<Item> getAllItemByCategory(@PathVariable Long id) {
		return itemRepository.findByCategory_Id(id);
	}

	@GetMapping("/getItem/{id}")
	public Item getItemById(@PathVariable Long id) {
		return itemRepository.findById(id).orElse(null);
	}

	@GetMapping("/getTopping")
	public List<Topping> getTopping() {
		return toppingRepository.findAll();
	}

	@PostMapping("/order")
	public String order(@RequestBody OrderDTO orderDTO) {
		System.out.println("ORDERRRRR");
		float totalOrder = 0;
		OrderCart order = new OrderCart();
		order.setName(orderDTO.getName());
		order.setAddress(orderDTO.getAddress());
		order.setPhone(orderDTO.getPhone());
		order.setOrderDetails(new ArrayList<>());
		for (ItemDTO item : orderDTO.getItemPizza()) {
			OrderDetails orderDetails = new OrderDetails();
			float priceTotal = 0;
			Optional<Item> pizza = itemRepository.findById(item.getId());
			if (pizza.isPresent()) {
				orderDetails.setItem(pizza.get());
				priceTotal += pizza.get().getPrice();
			} else {
				continue;
			}

			if (item.getItemHalf() != null) {
				Optional<Item> halfPizza = itemRepository.findById(item.getItemHalf().getId());
				if (halfPizza.isPresent()) {
					orderDetails.setItemHalf(halfPizza.get());
					priceTotal = (priceTotal / 2) + (halfPizza.get().getPrice() / 2);
				} else {
					continue;
				}
			}

			if (!CollectionUtils.isEmpty(item.getToppings())) {
				List<Topping> selectedTopping = toppingRepository
						.findAllById(item.getToppings().stream().map(t -> t.getId()).collect(Collectors.toList()));
				orderDetails.setToppings(selectedTopping);
				priceTotal += selectedTopping.stream().mapToDouble(t -> t.getPrice()).sum();
			}

			orderDetails.setPrice(priceTotal);
			totalOrder += priceTotal;
			order.getOrderDetails().add(orderDetails);
		}
		order.setTotalPrice(totalOrder);
		orderCartRepository.save(order);
		emailServiceImpl.sendSimpleMessage("long.hotrieu@gmail.com", "New Order", order);
		System.out.println(orderCartRepository.findAll().toString());
		return null;
	}
}
