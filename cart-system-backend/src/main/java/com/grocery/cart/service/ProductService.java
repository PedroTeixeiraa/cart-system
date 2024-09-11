package com.grocery.cart.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.grocery.cart.model.Product;

@Service
public class ProductService {
	private final String PRODUCT_API_URL = "http://localhost:8081/products";

	public List<Product> fetchAllProducts() {
		RestTemplate restTemplate = new RestTemplate();
		Product[] products = restTemplate.getForObject(PRODUCT_API_URL, Product[].class);
		return Arrays.asList(products);
	}
}
