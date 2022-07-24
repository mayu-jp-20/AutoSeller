package com.example.demo.user.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.component.ExhibitUtilService;
import com.example.demo.domain.ItemModel;
import com.example.demo.domain.UserModel;
import com.example.demo.user.controller.userAop.BaseController;

import io.swagger.client.ApiException;

@Controller
public class SearchForTest extends BaseController{

	@Autowired
	ExhibitUtilService exhibitService;

	@Autowired
	ItemInsertController itemInsertController;

	@GetMapping("/search_for_test")
	public String getSearchForTest(@AuthenticationPrincipal UserModel user,Model model) {
		setStatus(user,model);
		return "user/search_for_test";
	}

	@PostMapping("/search_for_test")
	public String postSearchForTest(@ModelAttribute("asin")String asin,
			@AuthenticationPrincipal UserModel user,Model model)
					throws IllegalArgumentException, IllegalAccessException, IOException, ApiException {

		List<String>list=new ArrayList<>();
		list.add(asin);

		//ASINからItemModelに変換
		List<ItemModel> itemList=exhibitService.getItemModelByAsin(list);

		setStatus(user,model);

		return itemInsertController.getItemInsert(itemList,model,user);
	}

}
