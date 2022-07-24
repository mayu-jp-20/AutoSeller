package com.example.demo.dao.repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.aop.LogAspect;
import com.example.demo.dao.ShopSearchDao;
import com.example.demo.user.domain.ShopSearchModel;

@Repository
public class ShopSearchDaoRepository implements ShopSearchDao{

	@Autowired
	JdbcTemplate jdbc;

	@Override
	public List<ShopSearchModel>selectAll(String mailAddress){

		List<ShopSearchModel>itemList=new ArrayList<>();

		List<Map<String,Object>>getList=jdbc.queryForList("SELECT * "
				+ "FROM shop_search "
				+ "WHERE mail_address=?"
				,mailAddress);

		for(Map<String,Object>map:getList)	{
			ShopSearchModel model=new ShopSearchModel();

			model.setSearchId((String)map.get("search_id"));
			model.setSearchDate((Date)map.get("search_date"));
			model.setMailAddress((String)map.get("mail_address"));
			model.setItemCode((String)map.get("item_code"));
			model.setReview((int)map.get("review"));
			model.setUrl((String)map.get("url"));
			model.setItemName((String)map.get("item_name"));

			itemList.add(model);
		}
		return itemList;
	}

	//search_idはその場で生成すると同じレコードということがわからなくなるから、mail_address+item_codeにする
	//レコード存在すればupdate、しなければinsertをする
	@Override
	public int insert(ShopSearchModel model) {

		Date date=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy/MM/dd");

		int rowNumber=jdbc.update("INSERT INTO shop_search(search_id,"
				+ "search_date,"
				+ "mail_address,"
				+ "item_code,"
				+ "review,"
				+ "url,"
				+ "item_name)"
				+ " VALUES("
				+model.getMailAddress()+model.getItemCode()+","
				+df.format(date)+","
				+model.getMailAddress()+","
				+model.getItemCode()+","
				+model.getReview()+","
				+model.getUrl()+","
				+model.getItemName()+") "
				+ "ON DUPLICATE KEY UPDATE "
				+ "search_date="+df.format(date)+","
				+ "review="+model.getReview()+","
				+ "item_name="+model.getItemName()+")");

		if(rowNumber != 1) {
			LogAspect.logger.warn("Failed to  ShopSearchDaoRepository.insert(ShopSearchModel)");
		}

		return rowNumber;
	}

	//search_idを取得できないためmail_addressとitem_codeから行う
	@Override
	public int update(ShopSearchModel model) {

		int rowNumber=jdbc.update("UPDATE shop_search "
				+ "SET item_name=? "
				+ "WHERE mail_address=? "
				+ "AND item_code=?"
				, model.getItemName()
				,model.getMailAddress()
				,model.getItemCode());

		if(rowNumber != 1) {
			LogAspect.logger.warn("Failed to  ShopSearchDaoRepository.update(ShopSearchModel)");
		}

		return rowNumber;
	}

}
