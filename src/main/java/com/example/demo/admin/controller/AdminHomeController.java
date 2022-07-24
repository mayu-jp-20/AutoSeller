package com.example.demo.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.admin.domain.CheckBrowseDTO;
import com.example.demo.service.ItemService;
import com.example.demo.service.NgService;

//未確認ブラウズノードの表示/成功画面は無し
@Controller
public class AdminHomeController{

	@Autowired
	ItemService itemDao;

	@Autowired
	NgService ngDao;

	/*
	 * 未確認ブラウズノードをラジオボタンで振り分ける（表は使わない）
	 * 出力する際に同じ項目は複数表示されないようにする
	 */

	/*
	 * new印がついた同一のカテゴリ名が複数表示される可能性がある。
	 */
	@GetMapping("/home_admin")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getHomeAdmin(Model model) {

		List<CheckBrowseDTO>browseList=itemDao.selectNewBrowse();
		model.addAttribute("list", browseList);

		return "admin/home_admin";
	}

	/*
	 * CheckBrowseデータバインド用のクラスを作る
	 * -カテゴリ名
	 * -NG/OK
	 * List<CheckBrowse>
	 */
	@PostMapping("/home_admin")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String postHomeAdmin(@ModelAttribute List<CheckBrowseDTO>list,Model model) {

		for(CheckBrowseDTO browse:list) {
			String check=browse.getOkOrNg();

			if(check=="NG") {
				ngDao.insert(browse.getBrowse(), "BROWSE");
			}

			itemDao.updateNotNew(browse.getBrowse());
		}

		return "admin/home_admin";
	}

}
