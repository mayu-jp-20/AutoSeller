package com.example.demo.scraping;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.example.demo.aop.LogAspect;
import com.example.demo.domain.ExhibitModel;
import com.example.demo.domain.ProxyModel;

@Component
public class ScrapingDetail {

	@Autowired
	ScrapingBlockCounter counter;

	//商品詳細ページのリンク先取得
	public Elements itemDetailLink(Document doc) {

		Elements h2List = new Elements();

		Elements grid=doc.getElementsByClass("s-main-slot s-result-list s-search-results sg-row");
		Elements hasUUIDList=grid.last().getElementsByAttribute("data-uuid");
		//Elements hasUUIDList=doc.getElementsByAttribute("data-uuid");
		Elements h2;
		int i=0;
		for (Element hasUUID:hasUUIDList) {
			h2 = hasUUID.getElementsByTag("h2");

			if(i==0) {
				h2List=h2;
			}else {
			h2List.addAll(h2);
			}
			i++;
		}

		return h2List;
	}

	//Qoo10-Analyticsの並び替え処理
	public Select srchSortBy(WebDriver driver) {

		WebElement sortBy=driver.findElement(By.id("srch_sort_by"));
		Select selectObject = new Select(sortBy);
		return selectObject;
	}

	//Document型への変換
	public Document driverToDocument(WebDriver driver,Document document) throws ParserConfigurationException, SAXException, IOException {
		InputSource inputSource=new InputSource(new StringReader(driver.toString()));
	    DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder builder=factory.newDocumentBuilder();
	    document=(Document) builder.parse(inputSource);
	    return document;
	}

	//PV数を含む要素の取得
	public Element getPV(Element dd) {
		Elements tr = dd.getElementsByTag("tr");
		Element td = tr.last().child(8);//8番目の要素を取得

		return td;
	}

	//Qoo10-Analyticsの販売数
	public List<ExhibitModel> getSalesNum(WebDriver driver,String link)
			throws ParserConfigurationException, SAXException, IOException{

		List<ExhibitModel>salesAnalyticsList=new ArrayList<>();
		boolean nextPage=false;
		int i2=0;
		org.jsoup.nodes.Document document=null;

		/*
		 * プロキシ/ユーザーエージェント設定for jsoup
		 */
		String userAgent=counter.selectUserAgent();
		ProxyModel proxy=counter.selectProxyServer();

		do {

			/*
			 * 待機
			 */
			counter.randomizeInterval();

			if (i2==0) {//最初のループのみ並び替え処理と型変換
				driver.get(link);

				/*
				 * 販売数昇順に並べ替える（1週間ごと）
				 */
				Select selectObject=srchSortBy(driver);
				selectObject.selectByValue("SALES_1WAGO_DESC");

				//driverをDocument型へ変換
				document=driverToDocument(driver,document);

			}else {

				Connection connection=Jsoup.connect(link);
				connection.proxy(proxy.getIp(),proxy.getPort());
				connection.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
				connection.header("User-Agent", userAgent);
				document=connection.get();
			}

			LogAspect.logger.info("************* sales number document");
			LogAspect.logger.info(document.toString());

			Elements yesterdayList = document.getElementsByClass("yesterday");//昨日の販売数と閲覧数両方取得しちゃっている
			int i = 1;

			for (Element yesterday : yesterdayList) {

				if (i % 2 == 0) {
				} else {

					String sales = yesterday.toString();
					int intSales=Integer.parseInt(sales);

					if (intSales == 0) {
						break;//0がでてきたらループ終了/nextPageがtrueに切り替わることはないのでdo-while文も同時終了

					} else {//販売数が1以上の場合、商品番号を取得する

						ExhibitModel analytics = new ExhibitModel();
						analytics.setNum(intSales);

						Elements sibling = yesterday.siblingElements();//yesterdayの兄弟要素を取得する
						Elements order = sibling.last().getElementsByClass("order");
						Element itemNum = order.get(3);//3つ目の要素を取得

						analytics.setItemCode(itemNum.toString());
						analytics.setType(false);

						salesAnalyticsList.add(analytics);

						if (yesterday == yesterdayList.get(yesterdayList.size() - 1)) {//yesterdayListの最後の要素の時)
							nextPage=true;
							//次のページに行く
							Elements paginate=document.getElementsByClass("paginate");
							Elements next=paginate.last().getElementsByClass("next");

							link=next.attr("abs:href");
						}
					}
				}
				i++;
			}
		i2++;

		} while (nextPage);

		return salesAnalyticsList;
	}

	public List<ExhibitModel>getAccessNum(WebDriver driver,String analyticsLink)
			throws IOException, ParserConfigurationException, SAXException{

		List<ExhibitModel>list=new ArrayList<>();
		boolean nextPage=false;
		int i2=0;
		org.jsoup.nodes.Document document=null;
		/*
		 * プロキシ/ユーザーエージェント設定for jsoup
		 */
		String userAgent=counter.selectUserAgent();
		ProxyModel proxy=counter.selectProxyServer();

		do {

			/*
			 * 待機
			 */
			counter.randomizeInterval();

			if (i2==0) {//最初のループのみ並び替え処理と型変換
				driver.get(analyticsLink);

				Select selectObject =srchSortBy(driver);
				//アクセス昇順に並べ替える(1週間毎）
				selectObject.selectByValue("PV_1WAGO_DESC");

				//Document型へ変換
				document =driverToDocument(driver,document);
			}else {

				Connection connection=Jsoup.connect(analyticsLink);
				connection.proxy(proxy.getIp(),proxy.getPort());
				connection.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
				connection.header("User-Agent", userAgent);
				document=connection.get();
				document=connection.get();
			}

			Elements lastWeekPV = document.getElementsByClass("bd_list01");
			Elements dds = lastWeekPV.last().getElementsByTag("dd");

			for (Element dd : dds) {

				//PV数の取得
				Element pvElem = getPV(dd);
				String pv = pvElem.child(3).text();
				int intPv=Integer.parseInt(pv);

				if (pv == "0") {//0がでたら終了/nextPageがtrueに切り替わることはないのでdo-while文も同時終了
					break;

				} else {//PV数が1以上の場合、商品番号を取得する

					//tdの兄弟要素である商品番号を取得
					Elements sibling = pvElem.siblingElements();
					Elements order = sibling.last().getElementsByClass("order");
					Element span = order.get(3);

					ExhibitModel analytics=new ExhibitModel();
					analytics.setNum(intPv);
					analytics.setItemCode(span.toString());
					analytics.setType(true);

					list.add(analytics);

					if(dd==dds.get(dds.size()-1)) {
						nextPage=true;

						//次のページに行く
						Elements paginate=document.getElementsByClass("paginate");
						Elements next=paginate.last().getElementsByClass("next");

						analyticsLink=next.attr("abs:href");
					}
				}
			}
			i2++;

		} while (nextPage);

		return list;
	}

}
