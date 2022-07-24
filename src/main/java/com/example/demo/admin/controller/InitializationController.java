package com.example.demo.admin.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.aop.LogAspect;
import com.example.demo.domain.Category;
import com.example.demo.service.CategoryService;

@Controller
public class InitializationController {

	/*
	 * アプリケーション全体の初期設定をするクラス
	 */

	@Autowired
	CategoryService categoryService;

	@GetMapping("/initialize")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getInitialize(Model model) {
		return "admin/initialize";
	}

	@PostMapping("/initialize")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String postInitialization(@RequestParam("upload_file")MultipartFile categoryFile,Model model) throws IOException {

		//ファイルの種類チェック
		String fileName = categoryFile.getOriginalFilename();;

		if(fileName.equals("category_mapping.csv")) {
		}else {
			model.addAttribute("message", "CSVファイルをアップロードしてください");
			return "admin/insert_NG_csv";
		}

		List<Category>categoryList=new ArrayList<>();
		InputStream stream = null;
		BufferedReader buf=null;

		//csvファイルをオブジェクトに変換
		try {
			String[]record=null;
			String line=null;
			stream = categoryFile.getInputStream();
			Reader reader=new InputStreamReader(stream,"UTF-8");
			buf=new BufferedReader(reader);

			while((line=buf.readLine())!=null) {
				record=line.split(",",0);

				Category category=new Category();
				category.setCategroyCode(record[0]);
				category.setCategory(record[1]);
				category.setAmazonCategory(record[2]);
				category.setSubCategoryCode(record[3]);
				category.setSubCategory(record[4]);
				category.setSubsubCategoryCode(record[5]);
				category.setSubsubCategory(record[6]);
				categoryList.add(category);
			}
		} catch (IOException e) {

			LogAspect.logger.error("Message:"+e.getMessage());
			LogAspect.logger.error("Cause:"+e.getCause());
			return "error_admin";

		}finally {
			stream.close();
			buf.close();
		}

		//DBに登録
		categoryService.insert(categoryList);

		model.addAttribute("message", "アップロード完了");

		return "admin/initialize";
	}

}
