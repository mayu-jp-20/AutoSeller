package com.example.demo.user.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.api.qoo.QooService;
import com.example.demo.api.qoo.RequestMakerQoo;
import com.example.demo.api.qoo.model.GoodsStatusRequest;
import com.example.demo.api.qoo.model.ItemResponse;
import com.example.demo.domain.ExhibitModel;
import com.example.demo.domain.ItemModel;
import com.example.demo.domain.SearchModel;
import com.example.demo.domain.UserModel;
import com.example.demo.service.ExhibitService;
import com.example.demo.service.ItemService;
import com.example.demo.user.controller.userAop.BaseController;

//出品した商品のリスト
@Controller
public class ExhibitListController extends BaseController{

	@Autowired
	ItemService itemDao;

	@Autowired
	QooService qooService;

	@Autowired
	ExhibitService exhibitService;

	@Autowired
	RequestMakerQoo requestMaker;

	@GetMapping("/exhibit")
	public String getExhibitList(@AuthenticationPrincipal UserModel user,Model model) {

		/*
		 * ユーザーが出品している商品を表示
		 */
		//ユーザーが出品している全商品のASINを取得
		List<ExhibitModel>exhibitList=exhibitService.selectExhibitAll(user.getUserId());
		//商品情報を取得
		List<String>asinList=new ArrayList<>();
		for(ExhibitModel exhibit:exhibitList) {
			asinList.add(exhibit.getAsin());
		}
		List<ItemModel>itemList=itemDao.selectMany(asinList);

		model.addAttribute("itemList", itemList);
		setStatus(user,model);

		return "user/exhibit";
	}

	@GetMapping("/exhibit/monthlyAccess_up")
	public String getAsinUp(@AuthenticationPrincipal UserModel user,Model model)
			throws NullPointerException,ClassCastException,
		    UnsupportedOperationException,IllegalArgumentException{

		/*
		 * ユーザーが出品している商品を表示
		 */
		List<ExhibitModel>exhibitList=exhibitService.selectExhibitAll(user.getUserId());
		List<String>asinList=new ArrayList<>();
		for(ExhibitModel exhibit:exhibitList) {
			asinList.add(exhibit.getAsin());
		}
		List<ItemModel>itemList=itemDao.selectMany(asinList);

		Comparator<ItemModel> compare=Comparator.comparing(ItemModel::getMonthlyAccess);
		itemList.sort(compare);

		model.addAttribute("itemList", itemList);
		setStatus(user,model);

		return "user/exhibit";
	}

	@GetMapping("exhibit/monthlyAccess_down")
	public String getAsinDown(@AuthenticationPrincipal UserModel user,Model model)
			throws NullPointerException,ClassCastException,
		    UnsupportedOperationException,IllegalArgumentException{

		List<ExhibitModel>exhibitList=exhibitService.selectExhibitAll(user.getUserId());
		List<String>asinList=new ArrayList<>();
		for(ExhibitModel exhibit:exhibitList) {
			asinList.add(exhibit.getAsin());
		}
		List<ItemModel>itemList=itemDao.selectMany(asinList);

		Comparator<ItemModel> compare=Comparator.comparing(ItemModel::getMonthlyAccess,Comparator.reverseOrder());
		itemList.sort(compare);

		model.addAttribute("itemList", itemList);
		setStatus(user,model);

		return "user/exhibit";
	}

	@GetMapping("/exhibit/allAccess_up")
	public String getItemUp(@AuthenticationPrincipal UserModel user,Model model)
			throws NullPointerException,ClassCastException,
		    UnsupportedOperationException,IllegalArgumentException{

		List<ExhibitModel>exhibitList=exhibitService.selectExhibitAll(user.getUserId());
		List<String>asinList=new ArrayList<>();
		for(ExhibitModel exhibit:exhibitList) {
			asinList.add(exhibit.getAsin());
		}
		List<ItemModel>itemList=itemDao.selectMany(asinList);

		Comparator<ItemModel> compare=Comparator.comparing(ItemModel::getAllAccess);
		itemList.sort(compare);

		model.addAttribute("itemList", itemList);
		setStatus(user,model);

		return "user/exhibit";
	}

	@GetMapping("exhibit/allAccess_down")
	public String getItemDown(@AuthenticationPrincipal UserModel user,Model model)
			throws NullPointerException,ClassCastException,
		    UnsupportedOperationException,IllegalArgumentException{

		List<ExhibitModel>exhibitList=exhibitService.selectExhibitAll(user.getUserId());
		List<String>asinList=new ArrayList<>();
		for(ExhibitModel exhibit:exhibitList) {
			asinList.add(exhibit.getAsin());
		}
		List<ItemModel>itemList=itemDao.selectMany(asinList);

		Comparator<ItemModel> compare=Comparator.comparing(ItemModel::getAllAccess,Comparator.reverseOrder());
		itemList.sort(compare);

		model.addAttribute("itemList", itemList);
		setStatus(user,model);

		return "user/exhibit";
	}

	@GetMapping("/exhibit/monthlySales_up")
	public String getCategoryUp(@AuthenticationPrincipal UserModel user,Model model)
			throws NullPointerException,ClassCastException,
		    UnsupportedOperationException,IllegalArgumentException{

		List<ExhibitModel>exhibitList=exhibitService.selectExhibitAll(user.getUserId());
		List<String>asinList=new ArrayList<>();
		for(ExhibitModel exhibit:exhibitList) {
			asinList.add(exhibit.getAsin());
		}
		List<ItemModel>itemList=itemDao.selectMany(asinList);

		Comparator<ItemModel> compare=Comparator.comparing(ItemModel::getMonthlySales);
		itemList.sort(compare);

		model.addAttribute("itemList", itemList);
		setStatus(user,model);

		return "user/exhibit";
	}

	@GetMapping("exhibit/monthlySales_down")
	public String getCategoryDown(@AuthenticationPrincipal UserModel user,Model model)
			throws NullPointerException,ClassCastException,
		    UnsupportedOperationException,IllegalArgumentException{

		List<ExhibitModel>exhibitList=exhibitService.selectExhibitAll(user.getUserId());
		List<String>asinList=new ArrayList<>();
		for(ExhibitModel exhibit:exhibitList) {
			asinList.add(exhibit.getAsin());
		}
		List<ItemModel>itemList=itemDao.selectMany(asinList);

		Comparator<ItemModel> compare=Comparator.comparing(ItemModel::getMonthlySales,Comparator.reverseOrder());
		itemList.sort(compare);

		model.addAttribute("itemList", itemList);
		setStatus(user,model);

		return "user/exhibit";
	}

	@GetMapping("/exhibit/allSales_up")
	public String getRankUp(@AuthenticationPrincipal UserModel user,Model model)
			throws NullPointerException,ClassCastException,
		    UnsupportedOperationException,IllegalArgumentException{

		List<ExhibitModel>exhibitList=exhibitService.selectExhibitAll(user.getUserId());
		List<String>asinList=new ArrayList<>();
		for(ExhibitModel exhibit:exhibitList) {
			asinList.add(exhibit.getAsin());
		}
		List<ItemModel>itemList=itemDao.selectMany(asinList);

		Comparator<ItemModel> compare=Comparator.comparing(ItemModel::getAllSales);
		itemList.sort(compare);

		model.addAttribute("itemList", itemList);
		setStatus(user,model);

		return "user/exhibit";
	}

	@GetMapping("exhibit/allSales_down")

	public String getRankDown(@AuthenticationPrincipal UserModel user,Model model)
			throws NullPointerException,ClassCastException,
		    UnsupportedOperationException,IllegalArgumentException{

		List<ExhibitModel>exhibitList=exhibitService.selectExhibitAll(user.getUserId());
		List<String>asinList=new ArrayList<>();
		for(ExhibitModel exhibit:exhibitList) {
			asinList.add(exhibit.getAsin());
		}
		List<ItemModel>itemList=itemDao.selectMany(asinList);

		Comparator<ItemModel> compare=Comparator.comparing(ItemModel::getAllSales,Comparator.reverseOrder());
		itemList.sort(compare);

		model.addAttribute("itemList", itemList);
		setStatus(user,model);

		return "user/exhibit";
	}


	@PostMapping(value="/exhibit",params="search")
	public String postSearch(@ModelAttribute("keyword")String keyword,@AuthenticationPrincipal UserModel user,Model model) throws SQLException {

		SearchModel searchModel=new SearchModel();
		searchModel.setKeyward(keyword);
		//searchModel.setAsin(searchForm.getAsin());
		//searchModel.setCategory(searchForm.getCategory());

		List<ExhibitModel>exhibitList=exhibitService.selectExhibitAll(user.getUserId());
		List<String>asinList=new ArrayList<>();
		for(ExhibitModel exhibit:exhibitList) {
			asinList.add(exhibit.getAsin());
		}
		List<ItemModel>itemList=itemDao.selectExhibitListBySearch(asinList, searchModel);

		model.addAttribute("itemList",itemList);
		setStatus(user,model);

		return "user/exhibit";
	}

	@PostMapping(value="/exhibit/{id}",params="delete")
	public String deleteItem(Model model,@PathVariable("id") String asin,@AuthenticationPrincipal UserModel user) {

		List<String>itemCodeList=exhibitService.getItemCode(user.getUserId(), asin);

		//Qoo10から取り下げ
		for (String itemCode:itemCodeList) {
			GoodsStatusRequest dto=requestMaker.editGoodsStatus(user,itemCode);
			ItemResponse res=qooService.editGoodsStatus(dto);

			if(res.getResultCode()!=0) {
				model.addAttribute("message", "削除に失敗しました。");
				return "user/exhibit";
			}else {
				exhibitService.deleteItem(user.getUserId(), asin);
			}
		}

		setStatus(user,model);
		model.addAttribute("link", "exhibit");

		return "user/success_for_user";
	}
}
