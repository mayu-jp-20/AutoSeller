package com.example.demo.admin.controller;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.admin.domain.ExhibitLimitForm;
import com.example.demo.admin.domain.ExhibitLimitModel;
import com.example.demo.aop.LogAspect;
import com.example.demo.api.qoo.QooService;
import com.example.demo.api.qoo.RequestMakerQoo;
import com.example.demo.api.qoo.model.GoodsStatusRequest;
import com.example.demo.api.qoo.model.ItemResponse;
import com.example.demo.component.PropertyUtil;
import com.example.demo.domain.ExhibitModel;
import com.example.demo.domain.UserModel;
import com.example.demo.service.ExhibitService;
import com.example.demo.service.MaximumService;
import com.example.demo.service.UserService;

@Controller
public class ConfigMaximum {

	@Autowired
	MaximumService maximumDao;

	@Autowired
	UserService userDao;

	@Autowired
	QooService qooService;

	@Autowired
	ExhibitService exhibitService;

	@Autowired
	RequestMakerQoo requestMaker;

	@GetMapping("/setup_status")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getConfigMaximum(@ModelAttribute ExhibitLimitForm form,Model model) {

		ExhibitLimitModel limit=maximumDao.select(PropertyUtil.getMaximumId());
		model.addAttribute("exhibitLimitForm", limit);

		return "admin/setup_status";
	}

	@PostMapping("/setup_status")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String postConfigMaximum(@ModelAttribute @Validated ExhibitLimitForm form,BindingResult br,Model model)
			throws ConfigurationException, IOException  {

		if(br.hasErrors()) {
			return "admin/setup_status";
		}

		ExhibitLimitModel limit=new ExhibitLimitModel();

		limit.setPlatinumQ(form.getPlatinumQ());
		limit.setGoldQ(form.getGoldQ());
		limit.setStandardQ(form.getStandardQ());
		limit.setPlatinumY(form.getPlatinumY());
		limit.setGoldY(form.getGoldY());
		limit.setStandardY(form.getStandardY());
		limit.setPlatinumQY(form.getPlatinumQY());
		limit.setGoldQY(form.getGoldQY());
		limit.setStandardQY(form.getStandardQY());

		boolean result=maximumDao.update(limit);

		if(result==false) {
			model.addAttribute("message", "登録に失敗しました。");
			return "admin/config_maximum";
		}

		/*
		 * ユーザーのデータベースの上限数を変更
		 */

		//全ユーザー取得
		List<UserModel> allUser=userDao.selectAll();

		for (UserModel user:allUser) {

			String mailAddress=user.getUserId();
			String status=user.getStatus();
			int exhibit=exhibitService.countExhibitNum(mailAddress);//ユーザーの出品数
			List<ExhibitModel>exhibitList=exhibitService.selectExhibitAll(mailAddress);


			//適用するプランを選択
			int newLimit = 0;

			switch(status) {

			case "ROLE_PLATINUM_Q":
				newLimit=limit.getPlatinumQ();
				break;
			case "ROLE_GOLD_Q":
				newLimit=limit.getGoldQ();
				break;
			case "ROLE_STANDARD_Q":
				newLimit=limit.getStandardQ();
				break;
			case "ROLE_PLATINUM_Y":
				newLimit=limit.getPlatinumY();
				break;
			case "ROLE_GOLD_Y":
				newLimit=limit.getGoldY();
				break;
			case "ROLE_STANDARD_Y":
				newLimit=limit.getStandardY();
				break;
			case "ROLE_PLATINUM_QY":
				newLimit=limit.getPlatinumQY();
				break;
			case "ROLE_GOLD_QY":
				newLimit=limit.getGoldQY();
				break;
			case "ROLE_STANDARD_QY":
				newLimit=limit.getStandardQY();
				break;
			}

			int newLimitNum=0;//新しい残りの出品可能額を設定

			if(newLimit > exhibit) {

				newLimitNum = newLimit - exhibit;
			}else if(newLimit == exhibit){

				newLimitNum=0;
			}else {
				newLimitNum=0;

				//出品取下げする商品を選択
				Comparator<ExhibitModel>compare=Comparator.comparing(ExhibitModel::getAllAccess);
				exhibitList.sort(compare);

				//●番目以降を削除(上限がらはみ出た分）
				List<ExhibitModel>newList=exhibitList.subList(newLimit,exhibitList.size());

				//出品取下げ処理
				for (ExhibitModel exhModel:newList) {
					GoodsStatusRequest dto =requestMaker.editGoodsStatus(
							user, exhModel.getItemCode());
					ItemResponse response = qooService.editGoodsStatus(dto);

					if(response.getResultCode()==0) {//成功
						//exhibitTableからも削除
						exhibitService.deleteItem(mailAddress, exhModel.getAsin());
					}else {
						LogAspect.logger.warn("Failed to Qoo10.editGoodsStatus, errorCode:{} {}"
								,response.getResultCode(),response.getResultMsg());
					}
				}
			}

			user.setLimitNum(newLimitNum);
			int updateLimitNum=userDao.updateLimitNum(user);

			if(updateLimitNum!=1) {
				model.addAttribute("message", "更新に失敗しました。");
			}
		}

		model.addAttribute("link", "setup_status");

		return "admin/success_for_admin";
	}

}
