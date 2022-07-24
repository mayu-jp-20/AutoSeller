package com.example.demo.admin.domain;

import javax.validation.constraints.PositiveOrZero;

import lombok.Data;

@Data
public class ExhibitLimitForm {

	/*
	 * 数字のみ
	 */

	@PositiveOrZero//0または整数
	private int platinumQ;

	@PositiveOrZero
	private int goldQ;

	@PositiveOrZero
	private int standardQ;

	@PositiveOrZero
	private int platinumY;

	@PositiveOrZero
	private int goldY;

	@PositiveOrZero
	private int standardY;

	@PositiveOrZero
	private int platinumQY;

	@PositiveOrZero
	private int goldQY;

	@PositiveOrZero
	private int standardQY;

}
