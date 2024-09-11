package com.grocery.cart.model;

import java.util.List;

import lombok.Data;

@Data
public class Cart {

	private List<CartItem> items;

	private int totalPrice;

	private int totalSavings;

}
