package com.example.demo.scraping;

import java.awt.AWTException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.example.demo.aop.LogAspect;
import com.example.demo.api.CaptchaService;
import com.example.demo.domain.CaptchaModel;
import com.example.demo.domain.ExhibitModel;
import com.example.demo.domain.ProxyModel;
import com.example.demo.domain.UserModel;
import com.example.demo.user.domain.ShopSearchModel;

@Component
public class ScrapingService implements ScrapingDao{

	@Autowired
	ScrapingBlockCounter counter;

	@Autowired
	ScrapingDetail sd;

	@Autowired
	CaptchaService captchaService;

	@Override
	public List<String> getAsinList(String url) throws IOException {

		List<String>asinList=new ArrayList<>();
		/*
		 * プロキシ/ユーザーエージェント設定
		 */
		String userAgent=counter.selectUserAgent();
		ProxyModel proxy=counter.selectProxyServer();
		Connection connection=Jsoup.connect(url);
		connection.proxy(proxy.getIp(), proxy.getPort());
		connection.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		connection.header("User-Agent", userAgent);
		/*
		 * ASINを取得
		 */
		Document document=connection.get();
		Elements grid=document.getElementsByClass("s-main-slot s-result-list s-search-results sg-row");
		Elements eleAsinList=grid.get(0).getElementsByAttribute("data-asin");
		/*
		 * String型に変換
		 */
		for(Element asin:eleAsinList) {
			asinList.add(asin.toString());
		}
		return asinList;
	}

	@Override
	public List<ShopSearchModel> getItemByshop(String mailAddress, String shopId)
			throws ParserConfigurationException, SAXException, IOException, AWTException {

		List<ShopSearchModel>resultList=new ArrayList<>();

		/*
		 * URLの生成
		 */
		String url="https://www.qoo10.jp/shop/"+shopId+"?&sortType=MOST_REVIEWED";
		/*
		 * プロキシ設定
		 */
		ProxyModel myProxy=counter.selectProxyServer();
        Proxy proxy=new Proxy();
        proxy.setHttpProxy("<"+myProxy.getIp()+":"+myProxy.getPort()+">");
        ChromeOptions option=new ChromeOptions();
        option.setCapability("proxy", proxy);
        /*
         * ユーザーエージェント設定
         */
        option.addArguments("--user-agent="+counter.selectUserAgentForSelenium());
		/*
		 * WebDriverでURLに接続
		 */
		WebDriver driver=new ChromeDriver(option);
		driver.get(url);
		JavascriptExecutor jexec=(JavascriptExecutor)driver;
		/*
		 * 全体の高さをjavascript実行して取得
		 */
		int sh=(int)jexec.executeScript("return document.body.scrollHeight");
		int height=0;
		/*
		 * 全体の高さと現在の高さが同じになるまでスクロールする
		 */
		do {
			height=height + 1000;
			jexec.executeScript("scrollTo(0," + height  + ")");

			/*
			 * 3-5秒間待機
			 */
			counter.randomizeInterval();
			/*
			 * ランダムなマウスの動き
			 */
			counter.randomMouseMove();

		} while (height < sh);
		/*
		 * DriverからDocumentへ変換
		 */
		InputSource inputSource=new InputSource(new StringReader(driver.toString()));
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder=factory.newDocumentBuilder();
		Document document=(Document) builder.parse(inputSource);
		/*
		 * driver(selenium)の終了
		 */
		driver.quit();

		/*
		 * 結果エリアの<ul>取得
		 */
		Element ul=document.getElementById("search_result_item_list");

		/*
		 * liから商品情報を取得する
		 */
		List<Node>liList=ul.childNodes();
		ShopSearchModel result=new ShopSearchModel();

		for(Node li:liList) {
			Elements review=((Element)li).getElementsByClass("review_total_count");//評価数
			/*
			 * 評価数がなければループ終了
			 */
			if(review==null) {
				break;
			}
			String goodsCode=((Element) li).getElementsByAttribute("goodscode").get(0).toString();//商品コード
			String itemUrl=((Element)li).getElementsByClass("tt").attr("abs:href");//URL
			String strReview=review.text();
		    strReview.replace("(", "");
		    strReview.replace(")", "");
		    int intReview=Integer.parseInt(strReview);
		    String itemName=((Element)li).getElementsByClass("tt").get(0).text();//商品名
		    Date date=new Date();//日付

		    result.setSearchId(UUID.randomUUID().toString());
		    result.setMailAddress(mailAddress);
		    result.setSearchDate(date);
		    result.setUrl(itemUrl);
		    result.setItemCode(goodsCode);
		    result.setReview(intReview);
		    result.setItemName(itemName);
		    resultList.add(result);
		}
		return resultList;
	}

	@Override
	public CaptchaModel selectCaptcha() {

		CaptchaModel captcha=new CaptchaModel();

		/*
		 * プロキシ/ユーザーエージェント設定
		 */
		String userAgent=counter.selectUserAgent();
		ProxyModel proxy=counter.selectProxyServer();

		Connection connection=Jsoup.connect(captcha.getLoginURL());
		connection.proxy(proxy.getIp(), proxy.getPort());
		connection.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		connection.header("User-Agent", userAgent);

		/*
		 * captchaの部分を取得
		 */
		Document document = null;
		try {
			document = connection.get();
		} catch (IOException e) {
			LogAspect.logger.error("Message:"+e.getMessage());
			LogAspect.logger.error("Cause:"+e.getCause());
			return null;
		}
		LogAspect.logger.info("*********** captcha document");
		LogAspect.logger.info(document.toString());
		Element id=document.getElementById("recaptcha_widget");
		String imgLink=id.attr("src");
		Element idInputArea=document.getElementById("txtLoginID");
		Element pwdInputArea=document.getElementById("txtLoginPwd");
		Element codeInputArea=id.getElementById("recaptcha_response_field");

		captcha.setImgLink(imgLink);
		captcha.setLoginPage(document);
		captcha.setId(id);
		captcha.setIdInputArea(idInputArea);
		captcha.setPwdInputArea(pwdInputArea);
		captcha.setCodeInputArea(codeInputArea);

		return captcha;
	}

	@Override
	public List<List<ExhibitModel>> getAnalytics(UserModel user, CaptchaModel captcha) {

		/*
		 * reCaptchaの突破
		 */
		String solveCode=captchaService.captchaSolver(captcha.getImgLink());

		//エラー発生時（返り値がnullだった場合）
		if(solveCode==null) {
			return null;
		}

        //ID入力
        captcha.getIdInputArea().attr("value",  user.getQooId());
        //パスワード入力
        captcha.getPwdInputArea().attr("value", user.getQooPass());
        //解除コードを所定のエリアに入力
        captcha.getCodeInputArea().attr("value", solveCode);

        /*
         * プロキシ設定
         */
		ProxyModel myProxy=counter.selectProxyServer();
        Proxy proxy=new Proxy();
        proxy.setHttpProxy("<"+myProxy.getIp()+":"+myProxy.getPort()+">");
        ChromeOptions option=new ChromeOptions();
        option.setCapability("proxy", proxy);
        /*
         * ユーザーエージェント設定
         */
        option.addArguments("--user-agent="+counter.selectUserAgentForSelenium());

        //ボタンクリック
        WebDriver driver=new ChromeDriver(option);
        driver.get(captcha.getLoginURL());
        driver.findElement(By.id("btn_login")).click();
        /*
         * ランダムなマウスの動き
         */
        counter.randomMouseMove();

        /*
         * 待機
         */
        counter.randomizeInterval();

        //トップページに恐らくアクセスできている
        WebElement elemAnal=driver.findElement(By.cssSelector("[title='Q-Analytics']"));
        String link=elemAnal.getAttribute("abs:href");
        LogAspect.logger.info("link:"+link);

        /*
         * 待機
         */
        counter.randomizeInterval();

		//販売数取得（高い順から）
	    List<ExhibitModel> salesNumList = null;
		try {
			salesNumList = sd.getSalesNum(driver,link);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			LogAspect.logger.error("Message:"+e.getMessage());
			LogAspect.logger.error("Cause:"+e.getCause());
			return null;
		}

	    //PV数（高い順から）
	    List<ExhibitModel> accessNumList = null;
		try {
			accessNumList = sd.getAccessNum(driver,link);
		} catch (IOException | ParserConfigurationException | SAXException e) {
			LogAspect.logger.error("Message:"+e.getMessage());
			LogAspect.logger.error("Cause:"+e.getCause());
			return null;
		}

	    List<List<ExhibitModel>>result=new ArrayList<>();

	    result.add(salesNumList);
	    result.add(accessNumList);

		return result;
	}

}
