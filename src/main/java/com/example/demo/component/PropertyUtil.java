package com.example.demo.component;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import com.example.demo.aop.LogAspect;

//appication.propertiesへのアクセスメソッド
public class PropertyUtil {

	private static final Properties rb()   {

		Path path=Paths.get("src/main/resources/private/application.properties");

		Reader reader;
		try {
			reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
			Properties properties=new Properties();
			properties.load(reader);

			return properties;
		} catch (IOException e) {
			LogAspect.logger.error("Message:"+e.getMessage());
			LogAspect.logger.error("Cause:"+e.getCause());
			return null;
		}
	}

	//static final ResourceBundle rb=ResourceBundle.getBundle("application");

	public static final String getMWSAccessKey() {
		return rb().getProperty("mws.accessKey");
	}

	public static final String getMWSSecretKey() {
		return rb().getProperty("mws.secretKey");
	}

	public static final String getMWSAppName() {
		return rb().getProperty("mws.appName");
	}

	public static final String getMWSAppVersion() {
		return rb().getProperty("mws.appVersion");
	}

	public static final String getMWSServiceURL() {
		return rb().getProperty("mws.serviceURL");
	}

	public static final String getMWSSellerId() {
		return rb().getProperty("mws.sellerId");
	}

	public static final String getMWSMarketPlaceId() {
		return rb().getProperty("mws.marketPlaceId");
	}

	public static final String getCreateCertificationKeyURL() {
		return rb().getProperty("qoo.createCertificationKeyURL");
	}

	public static final String getSetNewGoodsURL() {
		return rb().getProperty("qoo.setNewGoodsURL");
	}

	public static final String getSellerDeliveryGroupInfoURL() {
		return rb().getProperty("qoo.getSellerDeliveryGroupInfoURL");
	}

	public static final String getGoodsPriceURL() {
		return rb().getProperty("qoo.setGoodsPriceURL");
	}

	public static final String getEditGoodsStatusURL() {
		return rb().getProperty("qoo.editGoodsStatusURL");
	}

	public static final String getEditGoodsMultiImageURL() {
		return rb().getProperty("qoo.editGoodsMultiImage");
	}

	public static final String getShippingInfo() {
		return rb().getProperty("qoo.getShippingInfo");
	}

	public static final String getCaptchaKey() {
		return rb().getProperty("captcha.apiKey");
	}

	public static final String getMaximumId() {
		return rb().getProperty("maximum.id");
	}

	public static final String getLoginQooUrl() {
		return rb().getProperty("login.qoo10.url");
	}

	public static final String getAdminSecretKey() {
		return rb().getProperty("admin.secretKey");
	}
}
