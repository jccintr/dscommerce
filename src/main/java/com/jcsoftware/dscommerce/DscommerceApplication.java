package com.jcsoftware.dscommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//public class DscommerceApplication implements CommandLineRunner  {
	public class DscommerceApplication {
	
	/*
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	OrderItemRepository orderItemRepository;
	*/
	public static void main(String[] args) {
		SpringApplication.run(DscommerceApplication.class, args);
	}
/*
	@Override
	public void run(String... args) throws Exception {
		
		Order order = new Order();
		order.setMoment(Instant.now());
		order.setOrderStatus(OrderStatus.WAITING_PAYMENT);
		User client = userRepository.getReferenceById(1L);
		order.setClient(client);
		Product product = new Product(null,"X-Files Temporada 8","Box da série X-Files (8a temporada)",49.99, "http://www.x-files.com/dvd/temp8.png"); 
		productRepository.save(product);
		OrderItem item = new OrderItem(order,product,1,product.getPrice());
		order.getItems().add(item);
		orderRepository.save(order);
		orderItemRepository.saveAll(order.getItems());
		
	}
*/
	
}
