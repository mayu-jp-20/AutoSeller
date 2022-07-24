package com.example.demo.security;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.UserModel;

@Repository
public class LoginUserRepository {

	@Autowired
	private JdbcTemplate jdbc;

	/*
	 * ユーザー情報を取得してUserDetailsを生成するメソッド
	 */
	public UserDetails selectOne(String userId) {

		//select実行（ユーザーの取得）
		Map<String,Object>userMap=jdbc.queryForMap("SELECT * "
				+ "FROM service_user "
				+ "WHERE user_id=?", userId);
		//権限リストの取得
		List<GrantedAuthority>grantedAuthorityList=getRoleList(userId);
		//結果返却用のUserDetailsを生成
		UserModel user=buildUserDetails(userMap,grantedAuthorityList);

		return user;
	}

	/*
	 * 権限リストを取得するメソッド
	 */
	private List<GrantedAuthority> getRoleList(String userId){
		//select実行
		List<Map<String,Object>>authorityList=jdbc.queryForList("SELECT role "
				+ "FROM service_user "
				+ "WHERE user_id=?"
				,userId);

		//結果返却用のList生成
		List<GrantedAuthority>grantedAuthorityList=new ArrayList<>();
		for(Map<String,Object>map:authorityList) {
			//ロール名を取得
			String roleName=(String)map.get("role");
			//SimpleGrantedAuthorityインスタンスの生成
			GrantedAuthority authority=new SimpleGrantedAuthority(roleName);
			//結果返却用のListにインスタンスを追加
			grantedAuthorityList.add(authority);
		}
		return grantedAuthorityList;
	}
	/*
	 * ユーザークラスの作成
	 */
	private UserModel buildUserDetails(Map<String,Object> map,
			List<GrantedAuthority>grantedAuthorityList) {

		UserModel userModel=new UserModel();
		userModel.setUserId((String)map.get("user_id"));
		userModel.setPassword((String)map.get("password"));
		userModel.setName((String)map.get("user_name"));
		userModel.setKanaName((String)map.get("kana_name"));
		userModel.setSex((String)map.get("sex"));
		userModel.setBirthday((Date)map.get("birthday"));
		userModel.setStatus((String)map.get("role"));
		userModel.setLimitNum((int)map.get("limit_num"));
		userModel.setEnabled((boolean)map.get("enabled"));
		userModel.setConnectQooAccount((boolean)map.get("connect_qoo_account"));
		userModel.setQooId((String)map.get("qoo_id"));
		userModel.setQooPass((String)map.get("qoo_pwd"));
		userModel.setApiKey((String)map.get("api_key"));
		userModel.setSellerCertificationKey((String)map.get("seller_certification_key"));
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
		userModel.setAuthority(grantedAuthorityList);

		return userModel;
	}
}
