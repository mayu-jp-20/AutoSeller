package com.example.demo.dao.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.aop.LogAspect;
import com.example.demo.domain.Category;

@Repository
public class CategoryDaoRepository {

	@Autowired
	JdbcTemplate jdbc;

	public int insert(Category cate) {

		int rowNumber=jdbc.update("INSERT INTO category(category_code"
				+ ",category"
				+ ",amazon_category"
				+ ",sub_category_code"
				+ ",sub_category"
				+ ",sub_sub_category_code"
				+ ",sub_sub_category) "
				+ "VALUES(?,?,?,?,?,?,?)"
				,cate.getCategroyCode()
				,cate.getCategory()
				,cate.getAmazonCategory()
				,cate.getSubCategoryCode()
				,cate.getSubCategory()
				,cate.getSubsubCategoryCode()
				,cate.getSubsubCategory());

		if(rowNumber != 1) {
			LogAspect.logger.warn("登録失敗 ,小カテゴリ名:{},小カテゴリコード:{}"
					,cate.getSubsubCategory()
					,cate.getSubsubCategoryCode());
		}
		return rowNumber;
	}

	//Amazonの大分類でフィルターにかける
	public List<Category>selectByCategory(String amazonCategory){

		List<Map<String,Object>>getList=jdbc.queryForList("SELECT * "
				+ "FROM category "
				+ "WHERE amazon_category=?"
				, amazonCategory);

		List<Category>list=new ArrayList<>();

		for(Map<String,Object>map:getList) {
			Category newCate=new Category();

			newCate.setCategroyCode((String)map.get("category_code"));
			newCate.setCategory((String)map.get("category"));
			newCate.setAmazonCategory((String)map.get("amazon_category"));
			newCate.setSubCategoryCode((String)map.get("sub_category_code"));
			newCate.setSubCategory((String)map.get("sub_category"));
			newCate.setSubsubCategoryCode((String)map.get("sub_sub_category_code"));
			newCate.setSubsubCategory((String)map.get("sub_sub_category"));
			list.add(newCate);
		}

		return list;
	}

	//Qoo10の大分類でフィルターにかける
	public List<Category>selectByQCategory(String category){
		List<Map<String,Object>>getList=jdbc.queryForList("SELECT * "
				+ "FROM category "
				+ "WHERE category=?"
				, category);

		List<Category>list=new ArrayList<>();

		for(Map<String,Object>map:getList) {
			Category newCate=new Category();

			newCate.setCategroyCode((String)map.get("category_code"));
			newCate.setCategory((String)map.get("category"));
			newCate.setAmazonCategory((String)map.get("amazon_category"));
			newCate.setSubCategoryCode((String)map.get("sub_category_code"));
			newCate.setSubCategory((String)map.get("sub_category"));
			newCate.setSubsubCategoryCode((String)map.get("sub_sub_category_code"));
			newCate.setSubsubCategory((String)map.get("sub_sub_category"));
			list.add(newCate);
		}

		return list;
	}

	//大分類を取得する
	public List<String>selectCategory(){

		List<Map<String,Object>>getList=jdbc.queryForList("SELECT DISTINCT category "
				+ "FROM category");

		List<String>list=new ArrayList<>();

		for(Map<String,Object>map:getList) {
			list.add((String)map.get("category"));
		}
		return list;
	}

}
