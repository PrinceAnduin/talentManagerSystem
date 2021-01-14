package com.tema.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tema.entities.Company;
import com.tema.entities.CompanyRelation;
import com.tema.entities.CompanyRequest;
import com.tema.entities.User;
import com.tema.mapper.CompanyMapper;
import com.tema.mapper.CompanyRequestMapper;
import com.tema.mapper.UserMapper;

@Controller
@RequestMapping("/manager")
public class ManagerController {
	@Autowired
	CompanyRequestMapper companyRequestMapper;
	@Autowired
	UserMapper userMapper;
	@Autowired
	CompanyMapper companyMapper;
	
	User user;
	@GetMapping
	public String managerHome(HttpSession session, Model model) {
		if (session.getAttribute("loginUser") == null) {
			return "redirect:login";
		}
		user = (User) session.getAttribute("loginUser");
		if (!user.getEmail().equals("admin@admin.com")) {
			return "redirect:login";
		}
		model.addAttribute("loginUser", user);
		return "manager";
	}
	
	@GetMapping("/checkCompanies")
	public String checkCompanies(HttpSession session, Model model) {
		if (session.getAttribute("loginUser") == null) {
			return "redirect:/login";
		}
		user = (User) session.getAttribute("loginUser");
		if (!user.getEmail().equals("admin@admin.com")) {
			return "redirect:login";
		}
		model.addAttribute("loginUser", user);
		List<CompanyRequest> companyRequests = companyRequestMapper.findPage(0, 100);
		model.addAttribute("requests", companyRequests);
		return "manager/checkCompanies";
	}
	
	@GetMapping("/accept/{id}")
	public String accpetRequest(@PathVariable("id") int id, HttpSession session, Model model) {
		if (session.getAttribute("loginUser") == null) {
			return "redirect:/login";
		}
		user = (User) session.getAttribute("loginUser");
		if (!user.getEmail().equals("admin@admin.com")) {
			return "redirect:/index";
		}
		CompanyRequest companyRequest = companyRequestMapper.findOne(id);
		Company company = new Company();
		company.setAdminEmail(companyRequest.getUser().getEmail());
		company.setName(companyRequest.getName());
		company.setDescription(companyRequest.getDescription());
		companyMapper.insert(company);
		CompanyRelation companyRelation = userMapper.getCompany(companyRequest.getUser().getId());
		if (companyRelation == null) {
			userMapper.addCompany(companyRequest.getUser().getId(), company.getId());
		}else {
			userMapper.setCompany(companyRequest.getUser().getId(), company.getId());
		}
		userMapper.setAdmin(companyRequest.getUser().getId());
		companyRequestMapper.gotRequest(id);
		return "redirect:/manager";
	}
	
	@GetMapping("/deny/{id}")
	public String denyRequest(@PathVariable("id") int id, HttpSession session, Model model) {
		if (session.getAttribute("loginUser") == null) {
			return "redirect:login";
		}
		user = (User) session.getAttribute("loginUser");
		if (!user.getEmail().equals("admin@admin.com")) {
			return "redirect:/index";
		}
		companyRequestMapper.gotRequest(id);
		return "redirect:/manager";
	}
}
