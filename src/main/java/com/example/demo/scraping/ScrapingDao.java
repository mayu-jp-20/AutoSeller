package com.example.demo.scraping;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.example.demo.domain.CaptchaModel;
import com.example.demo.domain.ExhibitModel;
import com.example.demo.domain.UserModel;
import com.example.demo.user.domain.ShopSearchModel;

public interface ScrapingDao {

	/*
	 * Amazonの商品一覧のページからasinリストを抜き出す
	 * SearchItemByUrl
	 */
	public List<String> getAsinList(String url)throws IOException;

	/*
	 * Qoo10の特定のショップから、評価がついている商品を抜き出す
	 * SearchByShop
	 */
	public List<ShopSearchModel>getItemByshop(String mailAddress,String shopId) throws ParserConfigurationException, SAXException, IOException, AWTException;

	/*
	 * Captchaリゾルバー
	 */
	public CaptchaModel selectCaptcha() throws IOException;

	/*
	 * Qoo10Analyticsから集計情報取得
	 * 戻り値：List<List<AnalyticsModel>>→List<List<ExhibitModel>>
	 */
	public List<List<ExhibitModel>>getAnalytics(UserModel user,CaptchaModel captcha) throws Exception;
}
