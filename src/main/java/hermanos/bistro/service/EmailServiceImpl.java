package hermanos.bistro.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import hermanos.bistro.model.OrderCart;
import hermanos.bistro.model.OrderDetails;
import hermanos.bistro.model.Topping;

@Component
public class EmailServiceImpl {
	@Autowired
	private JavaMailSender emailSender;

	public void sendSimpleMessage(String to, String subject, OrderCart orderCart) {
		StringBuilder str = new StringBuilder();
		str.append("New order by ").append(orderCart.getName()).append("\n");
		str.append("Phone:").append(orderCart.getPhone()).append("\n");
		str.append("Address:").append(orderCart.getAddress()).append("\n");
		str.append("Time order:").append(new Date());
		str.append("List Order:").append("\n");
		for (OrderDetails orderDetails : orderCart.getOrderDetails()) {
			str.append(orderDetails.getItem().getName()).append("\n");
			if (orderDetails.getItemHalf() != null) {
				str.append("(Half)").append(orderDetails.getItemHalf().getName()).append("\n");
			}
			if (!CollectionUtils.isEmpty(orderDetails.getToppings())) {
				for (Topping topping : orderDetails.getToppings()) {
					str.append(topping.getName()).append("\n");
				}
			}
			str.append("---------------");
		}
		str.append("Total : ").append(orderCart.getTotalPrice());
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("long.hotrieu@gmail.com");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(str.toString());
		emailSender.send(message);
	}
}
