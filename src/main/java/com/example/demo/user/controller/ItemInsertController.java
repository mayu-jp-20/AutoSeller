package com.example.demo.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.aop.LogAspect;
import com.example.demo.component.ExhibitUtilService;
import com.example.demo.domain.ItemModel;
import com.example.demo.domain.UserModel;
import com.example.demo.service.ItemService;
import com.example.demo.service.UserService;
import com.example.demo.user.controller.userAop.BaseController;

import lombok.Data;

//ASIN_CSVまたはURLからのスクレイピングによって取得したアイテム一覧を表示
//Qoo10へ商品登録
@Controller
public class ItemInsertController extends BaseController{

	@Autowired
	ExhibitUtilService exbUtilService;

	@Autowired
	UserService userService;

	@Autowired
	ItemService itemService;

	//@GetMapping("/amazon_item_list")
	public String getItemInsert(@ModelAttribute List<ItemModel>itemList,Model model,@AuthenticationPrincipal UserModel user) {

		model.addAttribute("itemList", itemList);
		setStatus(user,model);

		return "user/amazon_item_list";
	}


	//登録ボタン押したら、Qoo10へ出品・同時にデータベース(itemとexhibit)に登録
	@PostMapping("/amazon_item_list")
	public String postItemList(@ModelAttribute ItemListForm itemListForm,@AuthenticationPrincipal UserModel user,Model model) {


		//ステータスを付与されていることを確認
		if(user.getStatus()=="ROLE_WAITING") {
			setStatus(user,model);
			return "user/failed";
		}

		LogAspect.logger.info("args:{}",itemListForm);

		/*
		 * ExhibtiUtilのメソッドを利用して登録処理
		 */
		int limitNum=user.getLimitNum();

		if(limitNum <= 0) {
			model.addAttribute("message", "出品可能上限数に達しています。プランを変更するか、出品商品を取り下げてください");
			setStatus(user,model);
			return getItemInsert(itemListForm.getItemList(),model,user);
		}

		//Qoo10への出品処理
		int newLimitNum=exbUtilService.exhibitItem(itemListForm.getItemList(),user);

		//上限数書き換え
		user.setLimitNum(newLimitNum);
		userService.updateLimitNum(user);

		setStatus(user,model);
		model.addAttribute("link", "home");
		return "user/success_for_user";
	}

	@Data
	public class ItemListForm{
		List<ItemModel>itemList;
	}
}
