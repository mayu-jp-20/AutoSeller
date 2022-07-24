package com.example.demo.admin.domain;

import com.example.demo.component.PropertyUtil;

import lombok.Data;

@Data
public class ExhibitLimitModel {

	private int platinumQ;
	private int goldQ;
	private int standardQ;
	private int platinumY;
	private int goldY;
	private int standardY;
	private int platinumQY;
	private int goldQY;
	private int standardQY;

	public String getMaximumId() {
		return PropertyUtil.getMaximumId();
	}
}
