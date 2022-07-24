package com.example.demo.scraping;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.example.demo.aop.LogAspect;
import com.example.demo.domain.ProxyModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

//スクレイピングがばれない対策
@Component
public class ScrapingBlockCounter {

	private final String PATH="src/main/resources/private/ip_address.csv";

	//リクエスト間隔をランダムな時間あける
	public void randomizeInterval()  {

		/*
		 * ランダムな間隔時間生成(3-5秒の範囲/3000-5000milli)
		 */
		Random random=new Random();
		int i=random.nextInt(2000)+3000;
		long millis=(long)i;

		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//IPアドレスをJSONファイルから読み込む
	private List<ProxyModel> readIpJson() {

		List<ProxyModel>list=new ArrayList<>();

		//jsonの取得
		String json=null;
		try (Stream<String> stream = Files.lines(Paths.get(PATH))) {
			StringBuilder builder=new StringBuilder();
			stream.forEach(builder::append);
			json=builder.toString();
		}catch(IOException e) {
		}

		//パーサー
		if(json.length() > 0) {

			//JsonObject{}
			JsonObject jsonObj=(JsonObject)new Gson().fromJson(json, JsonObject.class);

			//JsonArray[]
			JsonArray jsonAry=jsonObj.get("proxy").getAsJsonArray();
			JsonObject obj;
			ProxyModel proxy=new ProxyModel();

			for(int i = 0; i < jsonAry.size(); i++) {
				obj=jsonAry.get(i).getAsJsonObject();
				proxy.setIp(obj.get("ip").getAsString());
				proxy.setPort(obj.get("port").getAsInt());
				list.add(proxy);
			}
		}else {
			//jsonファイルが見つからないまたは空欄のエラー
		}
		return list;
	}

	//IPアドレスをCSVファイルから読み込む
	private List<ProxyModel> readIpCsv(){

		String[] list=null;

		try (Stream<String>stream=Files.lines(Paths.get(PATH))){

			String line=null;
			Reader reader=new InputStreamReader((InputStream) stream,"UTF-8");
			BufferedReader buf=new BufferedReader(reader);

			while((line=buf.readLine())!=null){
				list=line.split(",", 0);
			}

			List<ProxyModel>proxyList=new ArrayList<>();
			for(String ip:list) {
				ProxyModel proxy=new ProxyModel();
				proxy.setIp(ip);
				proxy.setPort(443);
				proxyList.add(proxy);
				LogAspect.logger.info("ip="+proxy.getIp());
			}

			return proxyList;

		} catch (IOException e) {
			LogAspect.logger.error("Message:"+e.getMessage());
			LogAspect.logger.error("Cause:"+e.getCause());
		}
		return null;
	}

	//プロキシサーバーを使ってIPアドレスをランダムに切り替えるIPアドレスのプールを作る）
	public ProxyModel selectProxyServer(){

		/*
		 * プロキシサーバー情報取得（ユーザー名/パス/プロキシのIPアドレス/ポート番号等
		 */
		List<ProxyModel> list=readIpCsv();

		/*
		 * プロキシをランダムに一つ選択
		 */
		int index=new Random().nextInt(list.size());
		ProxyModel proxy=list.get(index);

		return proxy;

	}

	//異なるスクレイピングパターンを適用する（ランダムなマウスの動き）
	public void randomMouseMove()  {

		//画面の幅取得
		int screenWidth=Toolkit.getDefaultToolkit().getScreenSize().width;
		//画面の高さ取得
		int screenHeight=Toolkit.getDefaultToolkit().getScreenSize().height;

		Random random=new Random();
		int x=random.nextInt(screenWidth);
		int y=random.nextInt(screenHeight);

		Robot robot ;
		try {
			robot = new Robot();
			robot.mouseMove(x, y);
		} catch (AWTException e) {
			LogAspect.logger.error("Message:"+e.getMessage());
			LogAspect.logger.error("Cause:"+e.getCause());
		}
	}

	//HTTPヘッダの書き換え/ユーザーエージェントのみ（jsoupでヘッダーの設定をする）/Jsoup用
	 /*
	  *  ヘッダにはHost,Connection,Cache-Control,Upgraede-Insecure-Requests,User-Agent（切り替えられるようにする）,Referrer,Accept-Encoding,Accept-Language
	  *  ×Host,Connection,Cache-Control,Upgraede-Insecure-Requests
	 */
	public String selectUserAgent() {

		/*
		 * ユーザーエージェントのリストを作成する
		 * リストからランダムに一つ選択
		 */
		List<String>list=new ArrayList<>();
		list.add("Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/12.10240");
		list.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
		list.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:67.0) Gecko/20100101 Firefox/67.0");
		list.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:61.0) Gecko/20100101 Firefox/61.0");
		list.add("Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko");
		list.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.0 Safari/605.1.15");

		Random random=new Random();
		int index=random.nextInt(list.size());

		String userAgent=list.get(index);

		return userAgent;
	}

	//chromeのみのユーザーエージェントリストからランダムに一つ選択(Selenium用）
	public String selectUserAgentForSelenium() {

		List<String>list=new ArrayList<>();
		list.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
		list.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36");
		list.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3864.0 Safari/537.36");
		list.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100");

		Random random=new Random();
		int index=random.nextInt(list.size());

		String userAgent=list.get(index);

		return userAgent;

	}
}
