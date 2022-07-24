package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.admin.domain.ExhibitLimitModel;
import com.example.demo.dao.MaximumDao;

@Service
public class MaximumService {

	@Autowired
	MaximumDao dao;

	//Listで登録したりする可能性がないため、他のリポジトリと違いrowNumberが1なら成功、
	//0なら失敗という風にしている
	public boolean update(ExhibitLimitModel model) {

		int rowNumber=dao.update(model);

		boolean result=false;

		if(rowNumber == 1) {
			result=true;
		}
		return result;
	}

	public ExhibitLimitModel select(String id) {
		return dao.select(id);
	}

}
