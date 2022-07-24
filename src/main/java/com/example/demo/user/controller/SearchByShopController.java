package com.example.demo.user.controller;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.xml.sax.SAXException;

import com.example.demo.domain.UserModel;
import com.example.demo.scraping.ScrapingDao;
import com.example.demo.service.ShopSearchService;
import com.example.demo.user.controller.userAop.BaseController;
import com.example.demo.user.domain.ShopSearchModel;

@Controller
public class SearchByShopController extends BaseController{

	@Autowired
	EditWordController ewController;

	@Autowired
	ShopSearchService shopSearchRep;

	@Autowired
	ScrapingDao scraping;

	/*
	 * 30日以内に登録されたItemCodeがあれば除外する
	 */
	public List<ShopSearchModel>editList(List<ShopSearchModel> list,String mailAddress){

		List<ShopSearchModel>removeList=new ArrayList<>();
		List<ShopSearchModel>oldList=shopSearchRep.selectAll(mailAddress);

		for(ShopSearchModel item:list) {
			String itemCode=item.getItemCode();
			for(ShopSearchModel old:oldList) {
				if(itemCode.equals(old.getItemCode())) {
					/*
					 * oldの日付に30を足す
					 */
					Calendar oldDate=Calendar.getInstance();
					oldDate.setTime(old.getSearchDate());
					oldDate.add(Calendar.DAY_OF_MONTH, 30);
					Calendar newDate=Calendar.getInstance();
					newDate.setTime(item.getSearchDate());
					/*
					 * その数字と今日の日付を比較
					 */
					int compareResult=oldDate.compareTo(newDate);
					/*
					 * if 今日の日付よりもすぎている場合はリストから削除
					 */
					if(compareResult == 1) {
						removeList.add(item);
					}
				}
			}
		}
		list.removeAll(removeList);
		return list;
	}

	@GetMapping("/search_by_shop")
	public String getSearchByShop(@ModelAttribute("shopId")String shopId,@AuthenticationPrincipal UserModel user,Model model) {
		setStatus(user,model);
		return "user/search_by_shop";
	}

	@PostMapping("/search_by_shop")
	public String postSearchByShop(@ModelAttribute("shopId")String shopIdForm,@AuthenticationPrincipal UserModel user,Model model)
			throws IOException, ParserConfigurationException, SAXException, AWTException {

		//カンマで区切ってリストにする
		String[] shopIdList=shopIdForm.split(",");

		/*
		 * 加工無しの情報全て取得
		 */
		List<ShopSearchModel>result=new ArrayList<>();
		for (String shopId:shopIdList) {
			List<ShopSearchModel>list = scraping.getItemByshop(user.getUserId(), shopId);
			result.addAll(list);
		}

		/*
		 * 直近30日に取得したアイテムは除外
		 */
		result=editList(result,user.getUserId());

		// shopSearchテーブルに登録
		shopSearchRep.insert(result);

		setStatus(user,model);
		return ewController.getEditWord(result,user, model);
	}

}
