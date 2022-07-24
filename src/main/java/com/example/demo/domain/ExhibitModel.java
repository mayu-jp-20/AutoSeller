package com.example.demo.domain;

import lombok.Data;

@Data
public class ExhibitModel {

	private String exhibitId;
	private String mailAddress;
	private String asin;
	private String itemCode;
	private boolean type;//閲覧数（true）か販売数（false）か
	private int num;
	private int monthlyAccess;
	private int monthlySales;
	private int allAccess;
	private int allSales;

}
