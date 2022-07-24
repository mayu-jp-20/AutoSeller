package com.example.demo.dao.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.aop.LogAspect;
import com.example.demo.dao.ExhibitDao;
import com.example.demo.domain.ExhibitModel;

/*
 * Exhibitテーブルの読み書き
 * ・DataAccessExceptionが発生したらログ出力と-9999を返す
 */
@Repository
public class ExhibitDaoRepository implements ExhibitDao{

	@Autowired
	JdbcTemplate jdbc;

	@Override
	public int countSeller(String asin){

		int count = jdbc.queryForObject("SELECT COUNT(*)"
							+ "FROM exhibit"
							+ " WHERE asin=?"
							, Integer.class
							, asin);
		return count;
	}

	@Override
	public List<ExhibitModel> selectExhibitAll(String mailAddress){

		List<ExhibitModel> asinList=new ArrayList<>();

		List<Map<String,Object>> getList=jdbc.queryForList("SELECT * "
						+ "FROM exhibit"
						+ " WHERE mail_address=?"
						,mailAddress);

		for(Map<String,Object>map:getList) {

				ExhibitModel model=new ExhibitModel();

				model.setExhibitId((String)map.get("exhibit_id"));
				model.setMailAddress((String)map.get("mail_address"));
				model.setAsin((String)map.get("asin"));
				model.setItemCode((String)map.get("item_code"));
				model.setMonthlyAccess((int)map.get("monthly_access"));
				model.setMonthlySales((int)map.get("monthly_sales"));
				model.setAllAccess((int)map.get("all_access"));
				model.setAllSales((int)map.get("all_sales"));

				asinList.add(model);
		}
		return asinList;
	}

	@Override
	public int insert(ExhibitModel exhibit) {

		int rowNumber=jdbc.update("INSERT INTO exhibit(exhibit_id,"
					+ "mail_address,"
					+ "asin,"
					+ "item_code,"
					+ "monthly_access,"
					+ "monthly_sales,"
					+ "all_access,"
					+ "all_sales)"
					+ " VALUES(?,?,?,?,?,?,?,?)"
					,UUID.randomUUID().toString()
					,exhibit.getMailAddress()
					,exhibit.getAsin()
					,exhibit.getItemCode()
					,0
					,0
					,0
					,0);

		if(rowNumber!=1) {
			LogAspect.logger.warn("Failed to  ExhibitDaoRepository.insert(ExhibitModel) ,args:{}",exhibit );
		}

		return rowNumber;
	}

	//rowNumberのカウントはServiceクラスに任せる
	@Override
	public int insertAnalytics(String mailAddress,ExhibitModel analytics) throws DataAccessException {

		int rowNumber=0;

		if (analytics.isType()) {//閲覧数の場合

				rowNumber = jdbc.update("UPDATE exhibit"
						+ " SET monthly_access=monthly_access + ?,"
						+ "all_access=all_access + ?"
						+ "WHERE mail_address=?"
						+ " AND item_code=? "
						,analytics.getNum()
						,analytics.getNum()
						,mailAddress
						,analytics.getItemCode());

		}else {//販売数の場合

				rowNumber=jdbc.update("UPDATE exhibit "
						+ "SET monthly_sales=monthly_sales + ?,"
						+ "all_sales=all_sales + ? "
						+ "WHERE mail_address=? "
						+ "AND item_code=?"
						,analytics.getNum()
						,analytics.getNum()
						,mailAddress
						,analytics.getItemCode());
		}

		if(rowNumber!=1) {
			LogAspect.logger.warn("Failed to  ExhibitDaoRepository.insertAnalytics(String,AnalyticsModel) ,args:{}",mailAddress+","+analytics );
		}
		return rowNumber;
	}

	@Override
	public int deleteItem(String mailAddress,String asin){

		int rowNumber=jdbc.update("DELETE FROM exhibit "
				+ "WHERE mail_address=?"
				+ " AND asin=?"
				,mailAddress
				,asin);

		if(rowNumber!=1) {
			LogAspect.logger.warn("Failed to  ExhibitDaoRepository.deleteItem(String,String) ,args:{}",mailAddress+","+asin );
		}

		return rowNumber;
	}

	@Override
	public List<ExhibitModel> selectByAsin(String asin)  {

		List<Map<String,Object>> getList=jdbc.queryForList("SELECT *"
				+ "FROM exhibit"
				+ " WHERE asin=?"
				,asin);

		List<ExhibitModel>exhibitList=new ArrayList<>();

		for(Map<String,Object>map:getList) {

			ExhibitModel model=new ExhibitModel();
			model.setExhibitId((String)map.get("exhibit_id"));
			model.setMailAddress((String)map.get("mail_address"));
			model.setAsin((String)map.get("asin"));
			model.setItemCode((String)map.get("item_code"));
			model.setMonthlyAccess((int)map.get("monthly_access"));
			model.setMonthlySales((int)map.get("monthly_sales"));
			model.setAllAccess((int)map.get("all_access"));
			model.setAllSales((int)map.get("all_sales"));
			exhibitList.add(model);
		}
		return exhibitList;
	}

	@Override
	public int countExhibiNum(String mailAddress)  {

		int count=jdbc.queryForObject("SELECT COUNT(*)FROM exhibit "
				+ "WHERE mail_address=?"
				, Integer.class
				, mailAddress);

		return count;
	}

	@Override
	public List<String> getItemCode(String mailAddress, String asin) {

		List<Map<String,Object>>getList=jdbc.queryForList("SELECT item_code "
				+ "FROM exhibit "
				+ "WHERE mail_address=? AND asin=?"
				,mailAddress
				,asin);

		List<String>list=new ArrayList<>();
		for(Map<String,Object> map:getList) {
			list.add((String)map.get("item_code"));
		}

		return list;
	}

	@Override
	public ExhibitModel selectOne(String mailAddress, String asin) {

		Map<String,Object> getMap=jdbc.queryForMap("SELECT * "
				+ "FROM exhibit "
				+ "WHERE mail_address=? AND asin=?"
				, mailAddress
				,asin);

		ExhibitModel exh=new ExhibitModel();
		exh.setAllAccess((int)getMap.get("all_access"));
		exh.setMonthlyAccess((int)getMap.get("monthly_access"));
		exh.setAllAccess((int)getMap.get("all_access"));
		exh.setMonthlySales((int)getMap.get("monthly_sales"));

		return exh;
	}
}
