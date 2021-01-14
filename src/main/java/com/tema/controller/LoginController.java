package com.tema.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tema.entities.CompanyRelation;
import com.tema.entities.Job;
import com.tema.entities.User;
import com.tema.mapper.CompanyMapper;
import com.tema.mapper.JobMapper;
import com.tema.mapper.UserMapper;

@Controller
@RequestMapping("/login")
public class LoginController {
	@Autowired
	UserMapper userMapper;
	@Autowired
	CompanyMapper companyMapper;
	@Autowired
	JobMapper jobMapper;
	
	@GetMapping
	public String toLogin() {
		return "login";
	}
	@PostMapping
	public String login(@RequestParam("userEmail") String email,
						@RequestParam("password") String password,
						HttpSession session, Model model) {
		User loginUser = userMapper.getOneByEmail(email);
		//数据加密
		String md5Passward = DigestUtils.md5DigestAsHex(password.getBytes());
		if (loginUser != null && loginUser.getPassword().equals(md5Passward)){
			loginUser = getUser(loginUser.getId());
			System.out.println(loginUser.getIsHR());
			session.removeAttribute("loginUser");
			session.setAttribute("loginUser", loginUser);
			if (email.equals("admin@admin.com")) {
				return "redirect:manager";
			}
			if (loginUser.getCompany() == null) {
				return "redirect:jobs";
			}
			return "redirect:home";
		}
		else {
			return "index";
		}
	}
	
	public User getUser(int userId) {
		User returnUser = userMapper.getOne(userId);
		returnUser.setCompany( companyMapper.getJobCompany(returnUser.getId()) );
		Job job = jobMapper.getUserJob(userId);
		returnUser.setJob(job);
		CompanyRelation companyRelation = userMapper.getCompany(userId);
		if (companyRelation != null && companyRelation.getCompanyId() != -1) {
			returnUser.setCompany(companyMapper.getCompany(companyRelation.getCompanyId()));
			returnUser.setIsAdmin(companyRelation.getIsAdmin());
			returnUser.setIsHR(companyRelation.getIsHR());
		}else {
			returnUser.setCompany(null);
			returnUser.setIsAdmin(false);
			returnUser.setIsHR(false);
		}
		return returnUser;
	}
}
