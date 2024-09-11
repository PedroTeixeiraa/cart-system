package com.grocery.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grocery.cart.model.Cart;
import com.grocery.cart.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@PostMapping("add")
	public Cart addToCart(@RequestBody ProductRequest productRequest) {
		return cartService.addToCart(productRequest.productId, productRequest.quantity);
	}

	@PostMapping("apply-promotion")
	public Cart applyPromotion(@RequestParam String promotionCode) {
		return cartService.applyPromotion(promotionCode);
	}

	@GetMapping
	public Cart getCart() {
		return cartService.getCart();
	}

	public record ProductRequest(String productId, int quantity) {
	}
}