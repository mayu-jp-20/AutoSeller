package com.example.demo.user.domain;

import java.math.BigDecimal;

import javax.validation.constraints.PositiveOrZero;

import lombok.Data;

@Data
public class PriceForm {

	//価格調整の設定入力で使う
	@PositiveOrZero
	private BigDecimal price1;

	@PositiveOrZero
	private BigDecimal price2;

	@PositiveOrZero
	private BigDecimal price3;

	@PositiveOrZero
	private BigDecimal price4;

	@PositiveOrZero
	private BigDecimal price5;

	@PositiveOrZero
	private BigDecimal rate1;

	@PositiveOrZero
	private BigDecimal rate2;

	@PositiveOrZero
	private BigDecimal rate3;

	@PositiveOrZero
	private BigDecimal rate4;

	@PositiveOrZero
	private BigDecimal rate5;

	@PositiveOrZero
	private BigDecimal rate6;

	@PositiveOrZero
	private BigDecimal fixing1;

	@PositiveOrZero
	private BigDecimal fixing2;

	@PositiveOrZero
	private BigDecimal fixing3;

	@PositiveOrZero
	private BigDecimal fixing4;

	@PositiveOrZero
	private BigDecimal fixing5;

	@PositiveOrZero
	private BigDecimal fixing6;

	@PositiveOrZero
	private BigDecimal referencePrice;

}
