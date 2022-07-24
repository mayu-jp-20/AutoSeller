
package com.example.demo.user.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.xml.sax.SAXException;

import com.example.demo.component.ExhibitUtilService;
import com.example.demo.domain.ItemModel;
import com.example.demo.domain.UserModel;
import com.example.demo.service.ShopSearchService;
import com.example.demo.user.controller.userAop.BaseController;
import com.example.demo.user.domain.ShopSearchModel;

import io.swagger.client.ApiException;
import lombok.Data;

@Controller
public class EditWordController extends BaseController{

	@Autowired
	ExhibitUtilService exbUtilService;

	@Autowired
	ItemInsertController itemInsertController;

	@Autowired
	ShopSearchService sss;

	public List<String> getAsinByKeyword(UserModel user,String keyward,Model model) throws IOException{

		String url="https://www.amazon.co.jp/s?k="+keyward;

		Document document=Jsoup.connect(url).get();
		/*
		 * ASINを取得
		 */
		Elements asinList=document.getElementsByAttribute("data-asin");

		/*
		 * String型に変換
		 */
		List<String>strAsinList=new ArrayList<>();
		for(Element asin:asinList) {
			strAsinList.add(asin.toString());
		}
		setStatus(user,model);
		return strAsinList;
	}

	//@Getmapping
	public String getEditWord(@ModelAttribute List<ShopSearchModel> itemList,UserModel user,Model model) {

		model.addAttribute("itemList", itemList);
		setStatus(user,model);

		return "user/edit_word";
	}

	@PostMapping("/edit_word")
	public String postEditWord(@ModelAttribute EditWordForm editWordForm,@AuthenticationPrincipal UserModel user,Model model)
			throws IllegalArgumentException, IllegalAccessException, ParserConfigurationException, SAXException, IOException, ApiException {

		System.out.println("結果1："+editWordForm.getKeywordList().get(0).toString());
		System.out.println("結果2："+editWordForm.getKeywordList().get(1).toString());

		//書き換えたキーワードを上書き保存
		sss.update(editWordForm.getKeywordList());

		/*
		 * 		キーワードからASIN一覧を取得する
		 */
		List<String>asinLists=new ArrayList<>();
		List<ShopSearchModel>shopSearchList=editWordForm.getKeywordList();
		for (ShopSearchModel shopSearch : shopSearchList) {

			List<String>asinList = getAsinByKeyword(user,shopSearch.getItemName(),model);
			asinLists.addAll(asinList);
		}

		/*
		 * ASINから商品情報を取得する
		 */
		List<ItemModel>itemList=exbUtilService.getItemModelByAsin(asinLists);
		setStatus(user,model);

		return itemInsertController.getItemInsert(itemList, model,user);
	}

	@Data
	public class EditWordForm{
		private List<ShopSearchModel>keywordList;
	}

}
