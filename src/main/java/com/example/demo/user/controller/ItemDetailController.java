package com.example.demo.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.api.qoo.RequestMakerQoo;
import com.example.demo.domain.ExhibitModel;
import com.example.demo.domain.ItemModel;
import com.example.demo.domain.UserModel;
import com.example.demo.service.ExhibitService;
import com.example.demo.service.ItemService;
import com.example.demo.user.controller.userAop.BaseController;

@Controller
public class ItemDetailController extends BaseController{

	@Autowired
	ItemService itemService;

	@Autowired
	ExhibitService exhibitService;

	@Autowired
	RequestMakerQoo rmq;

	@GetMapping("/item_detail/{id}")
	public String getItemDetail(Model model,@PathVariable("id")String asin,@AuthenticationPrincipal UserModel user){

		if(asin!=null&&asin.length()>0) {

			//商品情報の取得
			ItemModel item=itemService.selectOne(asin);

			String attrTable=rmq.getAttributeTable(item);
			String strAttrList=attrTable.replaceAll("<b>●商品情報</b>", "")
			.replaceAll("<b>", "")
			.replaceAll("</b>", "")
			.replaceAll("<p>", "")
			.replaceAll("</p>", "");

			item.setStrAttrList(strAttrList);


			//アクセス数の取得
			ExhibitModel exh=exhibitService.selectOne(user.getUserId(), asin);

			//アクセス数をセットする
			item.setAllAccess(exh.getAllAccess());
			item.setMonthlyAccess(exh.getMonthlyAccess());
			item.setAllSales(exh.getAllSales());
			item.setMonthlySales(exh.getMonthlySales());

			model.addAttribute("form", item);
		}
		setStatus(user,model);

		return "user/item_detail";
	}
}



