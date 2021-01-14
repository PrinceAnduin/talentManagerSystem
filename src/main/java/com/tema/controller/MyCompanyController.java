package com.tema.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tema.entities.Judge;
import com.tema.entities.User;
import com.tema.mapper.CompanyMapper;
import com.tema.mapper.CompanyRequestMapper;
import com.tema.mapper.JudgeMapper;
import com.tema.mapper.UserMapper;

@Controller
@RequestMapping("/myCompany")
public class MyCompanyController {
	@Autowired
	CompanyRequestMapper companyRequestMapper;
	@Autowired
	UserMapper userMapper;
	@Autowired
	CompanyMapper companyMapper;
	@Autowired
	JudgeMapper judgeMapper;
	
	User user;
	
	@GetMapping
	public String myCompany(HttpSession session, Model model) {
		if (session.getAttribute("loginUser") == null) {
			return "redirect:/login";
		}
		user = (User) session.getAttribute("loginUser");
		if (user.getIsAdmin() || user.getCompany() == null) {
			return "redirect:/home";
		}
		model.addAttribute("loginUser", user);
		return "myCompany";
	}
	
	@GetMapping("/judge")
	public String judgeUser(HttpSession session, Model model) {
		if (session.getAttribute("loginUser") == null) {
			return "redirect:login";
		}
		user = (User) session.getAttribute("loginUser");
		if (user.getIsAdmin() || user.getCompany() == null) {
			return "redirect:/home";
		}
		model.addAttribute("loginUser", user);
		Judge judge = judgeMapper.queryUserJudge(user.getId(), user.getCompany().getId());
		model.addAttribute("judgedUser", user.getCompany());
		if (judge != null) {
			model.addAttribute("judgement", judge.getJudgement());
		}
		return "myCompany/judgeCompany";
	}
	

	@RequestMapping(value = "/submitCompanyJudge", method = GET)
	public String deliveringPackages(@RequestParam(value = "judgement") int judgement, Model model, HttpSession session) {
			user = (User) session.getAttribute("loginUser");
			Judge newJudege = new Judge();
			user = (User) session.getAttribute("loginUser");
			Judge judge = judgeMapper.queryUserJudge(user.getId(), user.getCompany().getId());
			
			if (judge == null) {
				newJudege.setJudgement(judgement);
				newJudege.setJudgeDate(new Date());
				newJudege.setUser(user);
				newJudege.setCompany(user.getCompany());
				judgeMapper.insertUserJudge(newJudege);
			}else {
				judge.setJudgement(judgement);
				judge.setJudgeDate(new Date());
				judgeMapper.updateUserJudge(judge);
			}
			return "redirect:/myCompany";
	}
}
