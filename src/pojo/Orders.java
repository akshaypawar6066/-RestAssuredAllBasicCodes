package pojo;

import java.util.List;

public class Orders {
	private List<OrderDetails> orders;

	public List<OrderDetails> getOrderDetails() {
		return orders;
	}

	public void setOrderDetails(List<OrderDetails> orderDetails) {
		this.orders = orderDetails;
	}
}
