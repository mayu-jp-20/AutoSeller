package com.example.demo.dao;

import com.example.demo.admin.domain.ExhibitLimitModel;

public interface MaximumDao {

	public int update(ExhibitLimitModel model) ;

	public ExhibitLimitModel select(String id);

}
