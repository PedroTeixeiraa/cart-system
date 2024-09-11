package com.grocery.cart.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Service;

@Service
public class PromotionService {

	private Map<String, Double> promotions = new HashMap<>();

	public PromotionService() {
		promotions.put("SAVE10", 10.0);
		promotions.put("SAVE20", 20.0);
		promotions.put("SAVE30", 30.0);
	}

	public double getDiscountPercentage(String code) {
		Double promo = promotions.get(code);
		return Objects.requireNonNullElse(promo, 0.0);
	}
}
