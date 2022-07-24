package com.example.demo.admin.controller;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.domain.UserModel;
import com.example.demo.service.UserService;

@Controller
public class UserListController {

	@Autowired
	UserService dao;

	@GetMapping("/user_list_admin")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getUserList(Model model){

		List<UserModel>userList=dao.selectAll();
		model.addAttribute("userList", userList);

		return "admin/user_list_admin";
	}

	@GetMapping("user_list_admin/name_up")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getUserListNamesUp(Model model)
			throws NullPointerException,ClassCastException,
		    UnsupportedOperationException,IllegalArgumentException{

		List<UserModel>userList=dao.selectAll();
		Comparator<UserModel> compare=Comparator.comparing(UserModel::getKanaName);
		userList.sort(compare);
		model.addAttribute("userList", userList);

		return "admin/user_list_admin";
	}

	@GetMapping("user_list_admin/name_down")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getUserListNamesDown(Model model)
			throws NullPointerException,ClassCastException,
		    UnsupportedOperationException,IllegalArgumentException{

		List<UserModel>userList=dao.selectAll();
		Comparator<UserModel> compare=Comparator.comparing(UserModel::getKanaName,Comparator.reverseOrder());
		userList.sort(compare);
		model.addAttribute("userList", userList);

		return "admin/user_list_admin";
	}

	@GetMapping("user_list_admin/sex_up")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getUserListSexUp(Model model)
			throws NullPointerException,ClassCastException,
		    UnsupportedOperationException,IllegalArgumentException{

		List<UserModel>userList=dao.selectAll();
		Comparator<UserModel> compare=Comparator.comparing(UserModel::getSex);
		userList.sort(compare);
		model.addAttribute("userList", userList);

		return "admin/user_list_admin";
	}

	@GetMapping("user_list_admin/sex_down")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getUserListSexDown(Model model)
			throws NullPointerException,ClassCastException,
		    UnsupportedOperationException,IllegalArgumentException{

		List<UserModel>userList=dao.selectAll();
		Comparator<UserModel> compare=Comparator.comparing(UserModel::getSex,Comparator.reverseOrder());
		userList.sort(compare);
		model.addAttribute("userList", userList);

		return "admin/user_list_admin";
	}

	@GetMapping("user_list_admin/birthday_up")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getUserListBirthdayUp(Model model)
			throws NullPointerException,ClassCastException,
		    UnsupportedOperationException,IllegalArgumentException{

		List<UserModel>userList=dao.selectAll();
		Comparator<UserModel> compare=Comparator.comparing(UserModel::getBirthday);
		userList.sort(compare);
		model.addAttribute("userList", userList);

		return "admin/user_list_admin";
	}

	@GetMapping("user_list_admin/birthday_down")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getUserListBirthdayDown(Model model)
			throws NullPointerException,ClassCastException,
		    UnsupportedOperationException,IllegalArgumentException{

		List<UserModel>userList=dao.selectAll();
		Comparator<UserModel> compare=Comparator.comparing(UserModel::getBirthday,Comparator.reverseOrder());
		userList.sort(compare);
		model.addAttribute("userList", userList);

		return "admin/user_list_admin";
	}

	@GetMapping("user_list_admin/status_up")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getUserListStatusUp(Model model)
			throws NullPointerException,ClassCastException,
		    UnsupportedOperationException,IllegalArgumentException{

		List<UserModel>userList=dao.selectAll();
		Comparator<UserModel> compare=Comparator.comparing(UserModel::getStatus);
		userList.sort(compare);
		model.addAttribute("userList", userList);

		return "admin/user_list_admin";
	}

	@GetMapping("user_list_admin/status_down")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getUserListStatusDown(Model model)
			throws NullPointerException,ClassCastException,
		    UnsupportedOperationException,IllegalArgumentException{

		List<UserModel>userList=dao.selectAll();
		Comparator<UserModel> compare=Comparator.comparing(UserModel::getStatus,Comparator.reverseOrder());
		userList.sort(compare);
		model.addAttribute("userList", userList);

		return "admin/user_list_admin";
	}

	@GetMapping("user_list_admin/enable_up")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getUserListEnableUp(Model model)
			throws NullPointerException,ClassCastException,
		    UnsupportedOperationException,IllegalArgumentException{

		List<UserModel>userList=dao.selectAll();
		Comparator<UserModel> compare=Comparator.comparing(UserModel::getStrEnable);
		userList.sort(compare);
		model.addAttribute("userList", userList);

		return "admin/user_list_admin";
	}

	@GetMapping("user_list_admin/enable_down")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getUserListEnableDown(Model model)
			throws NullPointerException,ClassCastException,
		    UnsupportedOperationException,IllegalArgumentException{

		List<UserModel>userList=dao.selectAll();
		Comparator<UserModel> compare=Comparator.comparing(UserModel::getStrEnable,Comparator.reverseOrder());
		userList.sort(compare);
		model.addAttribute("userList", userList);

		return "admin/user_list_admin";
	}


}
