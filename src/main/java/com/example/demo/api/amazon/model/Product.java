package com.example.demo.api.amazon.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Product {

	private String asin;
	private String max;
	private BigDecimal shipping;//配送料


}
