package com.example.demo.user.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.example.demo.api.amazon.AmazonService;
import com.example.demo.component.ApplicationUtil;
import com.example.demo.component.ExhibitUtilService;
import com.example.demo.domain.ItemModel;
import com.example.demo.domain.UserModel;
import com.example.demo.service.NgService;
import com.example.demo.user.controller.userAop.BaseController;

import io.swagger.client.ApiException;

//ASINのみのCSVファイルを受け取る
//決定ボタン押したら、Amazonから情報を取ってきて、商品情報を一覧で表示するページ（itemInsert）へ遷移
@Controller
public class SearchByAsinController extends BaseController{

	@Autowired
	NgService ngDao;

	@Autowired
	AmazonService amazonService;

	@Autowired
	ExhibitUtilService exhibitService;

	@Autowired
	ItemInsertController itemInsertController;

	@Autowired
	ApplicationUtil app;

	@GetMapping("/search_by_asin")
	public String getAsinCsv(@AuthenticationPrincipal UserModel user,Model model) {
		setStatus(user,model);
		return "user/search_by_asin";
	}

	@PostMapping("/search_by_asin")
	public String postInput(@RequestParam("upload_file")MultipartFile file,@AuthenticationPrincipal UserModel user,Model model)
			throws IOException, ParserConfigurationException, SAXException, IllegalArgumentException, IllegalAccessException, ApiException {

		String[] asinList=null;

		/*
		 * ファイルの種類チェック
		 * ASINリスト作成
		 */
		String fileName = file.getOriginalFilename();
		String extension = fileName.substring(fileName.lastIndexOf("."));

		if(extension.equals(".csv")) {
			asinList=app.readCSV(file);
		}else {
			model.addAttribute("message", "CSVファイルをアップロードしてください");
			return "user/search_by_asin";
		}

		List<String>list=new ArrayList<>();
		for(String asin:asinList) {
			list.add(asin);
		}

		//ASINからItemModelに変換
		List<ItemModel> itemList=exhibitService.getItemModelByAsin(list);

		setStatus(user,model);

		return itemInsertController.getItemInsert(itemList,model,user);
	}
}
