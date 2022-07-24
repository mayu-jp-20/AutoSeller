package com.example.demo.admin.controller;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.ItemModel;
import com.example.demo.service.ExhibitService;
import com.example.demo.service.ItemService;

//登録されている商品を一覧で表示する(出品者数の表示は詳細画面で）
@Controller
public class ItemListController {

	@Autowired
	ItemService dao;

	@Autowired
	ExhibitService exhibitService;

	@GetMapping("/item_list_admin")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getItemList(@ModelAttribute("keyword")String keyword,Model model){

		List<ItemModel>itemList=dao.selectAll();
		model.addAttribute("itemList", itemList);

		return "admin/item_list_admin";
	}

	@GetMapping("item_list_admin/monthlyAccess_up")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getItemListMonthlyAccessUp(@ModelAttribute("keyword")String keyword,Model model)
			throws NullPointerException,ClassCastException,
		    UnsupportedOperationException,IllegalArgumentException{

		List<ItemModel>itemList=dao.selectAll();
		Comparator<ItemModel> compare=Comparator.comparing(ItemModel::getMonthlyAccess);
		itemList.sort(compare);
		model.addAttribute("itemList", itemList);

		return "admin/item_list_admin";
	}

	@GetMapping("item_list_admin/monthlyAccess_down")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getItemListMonthlyAccessDown(@ModelAttribute("keyword")String keyword,Model model)
			throws NullPointerException,ClassCastException,
		    UnsupportedOperationException,IllegalArgumentException{

		List<ItemModel>itemList=dao.selectAll();
		Comparator<ItemModel> compare=Comparator.comparing(ItemModel::getMonthlyAccess,Comparator.reverseOrder());
		itemList.sort(compare);
		model.addAttribute("itemList", itemList);

		return "admin/item_list_admin";
	}

	@GetMapping("item_list_admin/allAccess_up")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getItemListAllAccessUp(@ModelAttribute ("keyword")String keyword,Model model)
			throws NullPointerException,ClassCastException,
		    UnsupportedOperationException,IllegalArgumentException{

		List<ItemModel>itemList=dao.selectAll();
		Comparator<ItemModel> compare=Comparator.comparing(ItemModel::getAllAccess);
		itemList.sort(compare);
		model.addAttribute("itemList", itemList);

		return "admin/item_list_admin";
	}

	@GetMapping("item_list_admin/allAccess_down")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getItemListAllAccessDown(@ModelAttribute ("keyword")String keyword,Model model)
			throws NullPointerException,ClassCastException,
		    UnsupportedOperationException,IllegalArgumentException{

		List<ItemModel>itemList=dao.selectAll();
		Comparator<ItemModel> compare=Comparator.comparing(ItemModel::getAllAccess,Comparator.reverseOrder());
		itemList.sort(compare);
		model.addAttribute("itemList", itemList);

		return "admin/item_list_admin";
	}

	@GetMapping("item_list_admin/monthlySales_up")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getItemListMonthlySalesUp(@ModelAttribute ("keyword")String keyword,Model model)
			throws NullPointerException,ClassCastException,
		    UnsupportedOperationException,IllegalArgumentException{

		List<ItemModel>itemList=dao.selectAll();
		Comparator<ItemModel> compare=Comparator.comparing(ItemModel::getMonthlySales);
		itemList.sort(compare);
		model.addAttribute("itemList", itemList);

		return "admin/item_list_admin";
	}

	@GetMapping("item_list_admin/monthlySales_down")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getItemListMonthlySalesDown(@ModelAttribute ("keyword")String keyword,Model model)
			throws NullPointerException,ClassCastException,
		    UnsupportedOperationException,IllegalArgumentException{

		List<ItemModel>itemList=dao.selectAll();
		Comparator<ItemModel> compare=Comparator.comparing(ItemModel::getMonthlySales,Comparator.reverseOrder());
		itemList.sort(compare);
		model.addAttribute("itemList", itemList);

		return "admin/item_list_admin";
	}

	@GetMapping("item_list_admin/allSales_up")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getItemListAllSalesUp(@ModelAttribute ("keyword")String keyword,Model model)
			throws NullPointerException,ClassCastException,
		    UnsupportedOperationException,IllegalArgumentException{

		List<ItemModel>itemList=dao.selectAll();
		Comparator<ItemModel> compare=Comparator.comparing(ItemModel::getAllSales);
		itemList.sort(compare);
		model.addAttribute("itemList", itemList);

		return "admin/item_list_admin";
	}

	@GetMapping("item_list_admin/allSales_down")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getItemListAllSalesDown(@ModelAttribute("keyword")String keyword,Model model)
			throws NullPointerException,ClassCastException,
		    UnsupportedOperationException,IllegalArgumentException{

		List<ItemModel>itemList=dao.selectAll();
		Comparator<ItemModel> compare=Comparator.comparing(ItemModel::getAllSales,Comparator.reverseOrder());
		itemList.sort(compare);
		model.addAttribute("itemList", itemList);

		return "admin/item_list_admin";
	}

	//キーワード検索
	@PostMapping("/item_list_admin")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String postItemList(@ModelAttribute ("keyword")String keyword,Model model,BindingResult br) throws SQLException {

		if(br.hasErrors()) {
			return "admin/item_list_admin";
		}

		List<ItemModel>itemList=dao.selectAllItemBySearch(keyword);
		model.addAttribute("itemList",itemList);

		return "admin/item_list_admin";
	}

}

