package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.NgDao;

@Service
public class NgService {

	@Autowired
	NgDao dao;

	public int insert(String ngContens,String ngType){
		int rowNumber=dao.insert(ngContens,ngType);
		return rowNumber;
	}

	public List<String> insert(String[]ngContentsList,String ngType) {

		List<String> list=new ArrayList<>();

		for(String ngContents:ngContentsList) {
			int rowNumber=dao.insert(ngContents, ngType);

			if(rowNumber!=1) {
				list.add(ngContents);
			}
		}
		return list;
	}

	public List<String>selectNgContents(String contensType){
		return dao.selectNgContents(contensType);
	}
}
