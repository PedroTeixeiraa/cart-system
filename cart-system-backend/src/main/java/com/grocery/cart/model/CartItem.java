package com.grocery.cart.model;

import lombok.Data;

import com.grocery.cart.service.PromotionService;

@Data
public class CartItem {

	private Product product;

	private int quantity;
}
