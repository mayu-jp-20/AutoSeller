package com.example.demo.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.api.qoo.QooService;
import com.example.demo.api.qoo.RequestMakerQoo;
import com.example.demo.api.qoo.model.GetSellerDeliveryGroupInfoObject;
import com.example.demo.api.qoo.model.GetSellerDeliveryGroupInfoRequest;
import com.example.demo.api.qoo.model.GetSellerDeliveryGroupInfoResponse;
import com.example.demo.domain.UserModel;
import com.example.demo.service.UserService;
import com.example.demo.user.controller.userAop.BaseController;
import com.example.demo.user.domain.AccountForm;

@Controller
public class QooAccountController extends BaseController{

	@Autowired
	UserService service;

	@Autowired
	QooService qooService;

	@Autowired
	RequestMakerQoo requestMaker;

	@GetMapping("/account")
	public String getQooAccount(@ModelAttribute AccountForm form,@AuthenticationPrincipal UserModel userModel,Model model) {

		//マスキング処理時間かかるから「登録済み」で表示する
		if(userModel.getApiKey()!=null) {
			userModel.setApiKey("登録済み");
		}
		if(userModel.getQooPass()!=null) {
			userModel.setQooPass("登録済み");
		}

		model.addAttribute("form", userModel);
		setStatus(userModel,model);

		return "user/account";
	}

	@PostMapping("/account")
	public String insertConnect(@ModelAttribute AccountForm form,BindingResult br,@AuthenticationPrincipal UserModel user,Model model) {

		//バリデーション無し

		//UserModelに入力内容をバインド
		user.setApiKey(form.getApiKey());
		user.setQooId(form.getQooId());
		user.setQooPass(form.getQooPwd());

		//一度販売者認証キーを生成してみて入力内容が正しいか確認する
		int testKey=qooService.testKey(user);
		if(testKey!=0) {
			model.addAttribute("message", "Qoo10との接続に失敗しました。");
		}
			//送料コード取得
			GetSellerDeliveryGroupInfoRequest dto=requestMaker.getSellerDeliveryGroupInfoRequest(user);
			GetSellerDeliveryGroupInfoResponse response=qooService.getSellerDeliveryGroupInfo(dto);

			if(response.getResultCode()==0) {//success
				List<GetSellerDeliveryGroupInfoObject> objList=response.getList();
				StringBuilder sb=new StringBuilder();

				for(GetSellerDeliveryGroupInfoObject obj:objList) {
					String code=obj.getShippingNo();
					sb.append(code+",");
				}

				//Qoo10のID ,パス、APIキー、販売者キー,送料コードをデータベースに登録
				user.setApiKey(form.getApiKey());//APIキー
				user.setQooId(form.getQooId());//販売者IDまたはEmail
				user.setQooPass(form.getQooPwd());//販売者パスワード
				user.setAllShippingCode(sb.toString());//送料コード

				int insert=service.insertQooInfo(user);

				if(insert!=1) {
					setStatus(user,model);
					model.addAttribute("message", "更新に失敗しました");
				}

			}else {
				setStatus(user,model);
				model.addAttribute("message", "送料コードの取得に失敗しました。");
				return "user/account";
			}

		model.addAttribute("link", "account");
		setStatus(user,model);

		return "user/success_for_user";
	}

}
