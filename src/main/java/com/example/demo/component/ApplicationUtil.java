package com.example.demo.component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ApplicationUtil {

	//CSVからString[]への変換
	public String[]readCSV(MultipartFile file) throws IOException{

		String[] wordList=null;
		String line=null;
		InputStream stream=file.getInputStream();
		Reader reader=new InputStreamReader(stream,"UTF-8");
		BufferedReader buf=new BufferedReader(reader);

		while((line=buf.readLine())!=null) {
			wordList=line.split(",", 0);
		}
		return wordList;
	}

	//DB上のroleからステータス名への変換
	public String getStatus(String role) {

		String status=null;
		switch(role) {
		case "ROLE_PLATINUM_Q":
			status="プラチナQ";
			break;
		case "ROLE_GOLD_Q":
			status="ゴールドQ";
			break;
		case "ROLE_STANDARD_Q":
			status="スタンダードQ";
			break;
		case "ROLE_PLATINUM_Y":
			status="プラチナY";
			break;
		case "ROLE_GOLD_Y":
			status="ゴールドY";
			break;
		case "ROLE_STANDARD_Y":
			status="スタンダードY";
			break;
		case "ROLE_PLATINUM_QY":
			status="プラチナQY";
			break;
		case "ROLE_GOLD_QY":
			status="ゴールドQY";
			break;
		case "ROLE_STANDARD_QY":
			status="スタンダードQY";
			break;
		case"ROLE_WAITING":
			status="ステータス付与待ち";
			break;
		case"ROLE_ADMIN":
			status="管理者";
			break;
		default:
			status=null;
		}
		return status;
	}

}
