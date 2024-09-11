package com.grocery.cart.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

import com.grocery.cart.model.Cart;
import com.grocery.cart.model.CartItem;
import com.grocery.cart.model.Product;

@Service
@RequiredArgsConstructor
public class CartService {

	private final String PRODUCT_API_URL = "http://localhost:8081/products/";

	private final PromotionService promotionService;

	private final List<CartItem> cartItems = new ArrayList<>();
	private int totalSavings = 0;

	public Cart addToCart(String productId, int quantity) {
		RestTemplate restTemplate = new RestTemplate();
		Product product = restTemplate.getForObject(PRODUCT_API_URL.concat(productId), Product.class);

		CartItem cartItem = new CartItem();
		cartItem.setProduct(product);
		cartItem.setQuantity(quantity);

		CartItem existingItem = findItemInCart(productId);

		if (existingItem != null) {
			existingItem.setQuantity(existingItem.getQuantity() + quantity);
		} else {
			cartItems.add(cartItem);
		}

		return getCart();
	}

	private CartItem findItemInCart(String productId) {
		for (CartItem item : cartItems) {
			if (productId.equals(item.getProduct().getId())) {
				return item;
			}
		}
		return null;
	}

	public Cart getCart() {
		Cart cart = new Cart();
		cart.setItems(cartItems);

		int totalPrice = cartItems.stream().mapToInt(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
		cart.setTotalPrice(totalPrice - totalSavings);
		cart.setTotalSavings(totalSavings);

		return cart;
	}

	public Cart applyPromotion(String promotionCode) {
		double discount = promotionService.getDiscountPercentage(promotionCode);

		int total = cartItems.stream().mapToInt(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
		totalSavings = (int) (total * discount / 100);

		return getCart();
	}
}