package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.repository.CategoryDaoRepository;
import com.example.demo.domain.Category;

@Service
public class CategoryService {

	@Autowired
	CategoryDaoRepository dao;

	public void insert(List<Category> list) {
		for(Category cate:list) {
			dao.insert(cate);
		}
	}

	public List<Category> selectByCategory(String category){
		List<Category>list=dao.selectByCategory(category);
		return list;
	}

	public List<Category>selectByQCategory(String category){
		return dao.selectByQCategory(category);
	}

	public List<String> selectCategory(){
		return dao.selectCategory();
	}

}
