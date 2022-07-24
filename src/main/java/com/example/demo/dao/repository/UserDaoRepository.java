package com.example.demo.dao.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.aop.LogAspect;
import com.example.demo.dao.UserDao;
import com.example.demo.domain.UserModel;

@Repository
public class UserDaoRepository implements UserDao{

	/*
	 * エンコードする項目
	 * Webアプリのパスワード、Qoo10APIキー、Qoo10パスワード、販売者認証キー
	 */

	@Autowired
	JdbcTemplate jdbc;

	@Autowired
	PasswordEncoder encoder;

	/*
	 * 登録時はrole=ROLE_WAITING,limit_num=0で登録して、管理者側でroleとlimit_numを付与してもらったときに情報を更新する
	 */
	@Override
	public int insert(UserModel user){

		int rowNumber=jdbc.update("INSERT INTO service_user(user_name"
				+ ",kana_name"
				+ ",sex"
				+ ",birthday"
				+ ",user_id"
				+ ",password"
				+ ",role"
				+ ",limit_num"
				+ ",enabled"
				+ ",connect_qoo_account"
				+ ",item_qty)"
				+ "VALUES(?,?,?,?,?,?,?,?,?,?)"
				,user.getName()
				,user.getKanaName()
				,user.getSex()
				,user.getBirthday()
				,user.getUserId()
				,encoder.encode(user.getPassword())
				,"ROLE_WAITING"
				,0
				,true
				,false
				,0);

		if(rowNumber != 1) {
			LogAspect.logger.warn("Failed to  UserDaoRepository.insert(UserModel)");
		}

		return rowNumber;
	}

	@Override
	public int insertAdmin(UserModel user) {

		System.out.println("登録："+user.toString());

		int rowNumber=jdbc.update("INSERT INTO service_user(user_name"
				+ ",kana_name"
				+ ",sex"
				+ ",birthday"
				+ ",user_id"
				+ ",password"
				+ ",role"
				+ ",limit_num"
				+ ",enabled"
				+ ",connect_qoo_account"
				+ ",item_qty)"
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)"
				,user.getName()
				,user.getKanaName()
				,user.getSex()
				,user.getBirthday()
				,user.getUserId()
				,encoder.encode(user.getPassword())
				,"ROLE_ADMIN"
				,user.getLimitNum()
				,true
				,false
				,0);

		if(rowNumber != 1) {
			LogAspect.logger.warn("Failed to  UserDaoRepository.insertAdmin(UserModel)");
		}

		return rowNumber;
	}

	@Override
	public int insertQooInfo(UserModel account){

		int rowNumber=jdbc.update("UPDATE service_user "
				+ "SET qoo_id=?,"
				+ "qoo_pwd=?,"
				+ "api_key=?,"
				+ "all_shipping_code=?"
				+ "WHERE user_id=?"
				,account.getQooId()
				,encoder.encode(account.getQooPass())
				,encoder.encode(account.getApiKey())
				,account.getShippingCode()
				,account.getUserId());

		if(rowNumber != 1) {
			LogAspect.logger.warn("Failed to  UserDaoRepository.insertQooInfo(UserModel)");
		}

		return rowNumber;
	}


	@Override
	public int updateStatus(UserModel user){

		int rowNumber=jdbc.update("UPDATE service_user "
				+ "SET role=?"
				+ "WHERE user_id=?"
				, user.getStatus()
				,user.getUserId());

		if(rowNumber != 1) {
			LogAspect.logger.warn("Failed to  UserDaoRepository.updateStatus(UserModel) ");
		}

		return rowNumber;
	}

	@Override
	public int updateEnabled(UserModel user) {

		int rowNumber=jdbc.update("UPDATE service_user "
				+ "SET enabled=?"
				+ "WHERE user_id=?"
				,user.isEnabled()
				,user.getUserId());

		if(rowNumber != 1) {
			LogAspect.logger.warn("Failed to  UserDaoRepository.updateEnabled(UserModel)");
		}

		return rowNumber;
	}

	@Override
	public int updatePrice(UserModel price) {

		int rowNumber=jdbc.update("UPDATE service_user "
				+ "SET price_1=?,"
				+ "price_2=?,"
				+ "price_3=?,"
				+ "price_4=?,"
				+ "price_5=?,"
				+ "rate_1=?,"
				+ "rate_2=?,"
				+ "rate_3=?,"
				+ "rate_4=?,"
				+ "rate_5=?,"
				+ "rate_6=?,"
				+ "fixing_1=?,"
				+ "fixing_2=?,"
				+ "fixing_3=?,"
				+ "fixing_4=?,"
				+ "fixing_5=?,"
				+ "fixing_6=?"
				+ "WHERE user_id=?"
				,price.getPrice1()
				,price.getPrice2()
				,price.getPrice3()
				,price.getPrice4()
				,price.getPrice5()
				,price.getRate1()
				,price.getRate2()
				,price.getRate3()
				,price.getRate4()
				,price.getRate5()
				,price.getRate6()
				,price.getFixing1()
				,price.getFixing2()
				,price.getFixing3()
				,price.getFixing4()
				,price.getFixing5()
				,price.getFixing6()
				,price.getUserId());

		if(rowNumber != 1) {
			LogAspect.logger.warn("Failed to  UserDaoRepository.updatePrice(UserModel)");
		}
		return rowNumber;
	}

	@Override
	public int updateReferencePrice(UserModel user){

		int rowNumber=jdbc.update("UPDATE service_user "
				+ "SET reference_price=?"
				+ " WHERE user_id=?"
				,user.getReferencePrice()
				,user.getUserId());

		if(rowNumber != 1) {
			LogAspect.logger.warn("Failed to  UserDaoRepository.updateReferencePrice(UserModel)");
		}

		return rowNumber;
	}

	@Override
	public List<UserModel> selectAll(){

		List<Map<String,Object>>getList=jdbc.queryForList("SELECT *"
				+ " FROM service_user");

		List<UserModel>userList=new ArrayList<>();

		for(Map<String,Object>map:getList) {

			UserModel userModel=new UserModel();

			if((boolean)map.get("enabled")) {
				userModel.setStrEnable("可");
			}else {
				userModel.setStrEnable("不可");
			}

			String status=null;
			String role=(String)map.get("role");

			switch(role) {
			case "ROLE_PLATINUM_Q":
				status="プラチナQ";
				break;
			case "ROLE_GOLD_Q":
				status="ゴールドQ";
				break;
			case "ROLE_STANDARD_Q":
				status="スタンダードQ";
				break;
			case "ROLE_PLATINUM_Y":
				status="プラチナY";
				break;
			case "ROLE_GOLD_Y":
				status="ゴールドY";
				break;
			case "ROLE_STANDARD_Y":
				status="スタンダードY";
				break;
			case "ROLE_PLATINUM_QY":
				status="プラチナQY";
				break;
			case "ROLE_GOLD_QY":
				status="ゴールドQY";
				break;
			case "ROLE_STANDARD_QY":
				status="スタンダードQY";
				break;
			}

		    userModel.setName((String)map.get("user_name"));
		    userModel.setKanaName((String)map.get("kana_name"));
		    userModel.setSex((String)map.get("sex"));
		    userModel.setBirthday((Date)map.get("birthday"));
		    userModel.setUserId((String)map.get("user_id"));
		    userModel.setStatus(status);

		    userList.add(userModel);
		}
		return userList;
	}

	@Override
	public int updateQooConnection(UserModel user) {

		int rowNumber=jdbc.update("UPDATE service_user"
				+ " SET connect_qoo_account=? "
				+ "WHERE user_id=?"
				,user.isConnectQooAccount()
				,user.getUserId());

		if(rowNumber != 1) {
			LogAspect.logger.warn("Failed to  UserDaoRepository.updateQooConnection(UserModel)");
		}
		return rowNumber;
	}

	@Override
	public int updateQooConfig(UserModel qoo){

		int rowNumber=jdbc.update("UPDATE service_user "
				+ "SET fixing_keyward_for_item=? ,"
				+ "shipping_code=?,"
				+ "item_page_header=?,"
				+ "item_page_footer=?"
				+ "WHERE user_id=?"
				,qoo.getFixingKeywardForItem()
				,qoo.getShippingCode()
				,qoo.getItemPageHeader()
				,qoo.getItemPageFooter()
				,qoo.getUserId());

		if(rowNumber != 1) {
			LogAspect.logger.warn("Failed to  UserDaoRepository.updateQooConfig(UserModel) ");
		}

		return rowNumber;
	}

	@Override
	public UserModel selectOneAll(String mailAddress) {

		Map<String,Object>map=jdbc.queryForMap("SELECT *"
				+ "FROM service_user "
				+ "WHERE user_id=?"
				, mailAddress);

		UserModel userModel=new UserModel();

		userModel.setName((String)map.get("user_name"));
		userModel.setKanaName((String)map.get("kana_name"));
		userModel.setSex((String)map.get("sex"));
		userModel.setBirthday((Date)map.get("birthday"));
		userModel.setUserId((String)map.get("user_id"));
		userModel.setPassword((String)map.get("password"));
		userModel.setStatus((String)map.get("role"));
		userModel.setLimitNum((int)map.get("limit_num"));
		userModel.setEnabled((boolean)map.get("enabled"));
		userModel.setConnectQooAccount((boolean)map.get("connect_qoo_account"));
		userModel.setQooId((String)map.get("qoo_id"));
		userModel.setQooPass((String)map.get("qoo_pwd"));
		userModel.setApiKey((String)map.get("api_key"));
		userModel.setPrice1((BigDecimal)map.get("price_1"));
		userModel.setPrice2((BigDecimal)map.get("price_2"));
		userModel.setPrice3((BigDecimal)map.get("price_3"));
		userModel.setPrice4((BigDecimal)map.get("price_4"));
		userModel.setPrice5((BigDecimal)map.get("price_5"));
		userModel.setRate1((BigDecimal)map.get("rate_1"));
		userModel.setRate2((BigDecimal)map.get("rate_2"));
		userModel.setRate3((BigDecimal)map.get("rate_3"));
		userModel.setRate4((BigDecimal)map.get("rate_4"));
		userModel.setRate5((BigDecimal)map.get("rate_5"));
		userModel.setRate6((BigDecimal)map.get("rate_6"));
		userModel.setFixing1((BigDecimal)map.get("fixing_1"));
		userModel.setFixing2((BigDecimal)map.get("fixing_2"));
		userModel.setFixing3((BigDecimal)map.get("fixing_3"));
		userModel.setFixing4((BigDecimal)map.get("fixing_4"));
		userModel.setFixing5((BigDecimal)map.get("fixing_5"));
		userModel.setFixing6((BigDecimal)map.get("fixing_6"));
		userModel.setReferencePrice((BigDecimal)map.get("reference_price"));
		userModel.setFixingKeywardForItem((String)map.get("fixing_keyward_for_item"));
		userModel.setShippingCode((String)map.get("shipping_code"));
		userModel.setAllShippingCode((String)map.get("all_shipping_code"));
		userModel.setItemQty((int)map.get("item_qty"));
		userModel.setItemPageHeader((String)map.get("item_page_header"));
		userModel.setItemPageFooter((String)map.get("item_page_footer"));

		return userModel;
	}

	@Override
	public int updateLimitNum(UserModel user) {

		int rowNumber=jdbc.update("UPDATE service_user "
				+ "SET limit_num=?"
				+ "WHERE user_id=?"
				,user.getLimitNum()
				,user.getUserId());

		if(rowNumber != 1) {
			LogAspect.logger.warn("Failed to  UserDaoRepository.updateLimitNum(UserModel)");
		}

		return rowNumber;
	}


}
