package com.example.demo.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.api.qoo.RequestMakerQoo;
import com.example.demo.domain.ExhibitModel;
import com.example.demo.domain.ItemModel;
import com.example.demo.service.ExhibitService;
import com.example.demo.service.ItemService;
import com.example.demo.service.UserService;

//登録商品の詳細情報表示ページ
/*
 * AttributeSetsの出力をつくる
 * ファイル出力で<th></th>を上書きするようにする
 */
@Controller
public class ItemDetailAdminController {

	@Autowired
	ItemService itemDao;

	@Autowired
	ExhibitService exhibitService;

	@Autowired
	UserService userService;

	@Autowired
	RequestMakerQoo rmq;

	//1商品の集計情報全てがみられる
	@GetMapping("/item_detail_admin/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getItemDetailAdmin(Model model,@PathVariable("id")String asin){

		if(asin!=null&&asin.length()>0) {

			//商品情報の取得
			ItemModel item=itemDao.selectOne(asin);

			String attrTable=rmq.getAttributeTable(item);
			String strAttrList=attrTable.replaceAll("<b>●商品情報</b>", "")
			.replaceAll("<b>", "")
			.replaceAll("</b>", "")
			.replaceAll("<p>", "")
			.replaceAll("</p>", "");

			item.setStrAttrList(strAttrList);

			model.addAttribute("item", item);

			//出品者数の取得
			int numberOfSeller=exhibitService.countSeller(asin);

			//出品しているユーザーとアクセス数販売数を検索
			List<ExhibitModel> exhibitUser=exhibitService.selectByAsin(asin);

			model.addAttribute("seller", numberOfSeller);
			model.addAttribute("exhibitUser", exhibitUser);
		}

		return "admin/item_detail_admin";
	}

}
