package com.tema.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tema.entities.CompanyRelation;
import com.tema.entities.CompanyRequest;
import com.tema.entities.Job;
import com.tema.entities.Judge;
import com.tema.entities.User;
import com.tema.forms.CompanyForm;
import com.tema.mapper.CompanyMapper;
import com.tema.mapper.CompanyRequestMapper;
import com.tema.mapper.JobMapper;
import com.tema.mapper.JudgeMapper;
import com.tema.mapper.UserMapper;

@Controller
@RequestMapping("/companyManage")
public class CompanyManageController {
	@Autowired
	CompanyRequestMapper companyRequestMapper;
	@Autowired
	UserMapper userMapper;
	@Autowired
	CompanyMapper companyMapper;
	@Autowired
	JobMapper jobMapper;
	@Autowired
	JudgeMapper judgeMapper;
	
	User user;
	@RequestMapping("/signCompany")
	public String toSignCompany(HttpSession session, Model model) {
		model.addAttribute("newCompany", new CompanyForm());
		user = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", user);
		return "companyManager/signCompany";
	}
	
	@GetMapping
	public String myCompany(HttpSession session, Model model) {
		if (session.getAttribute("loginUser") == null) {
			return "redirect:/login";
		}
		user = (User) session.getAttribute("loginUser");
		if (!user.getIsAdmin() || user.getCompany() == null) {
			return "redirect:/home";
		}
		model.addAttribute("loginUser", user);
		System.out.println(user.getCompany().getId());
		List<Integer> integers = userMapper.findPageByCompany(user.getCompany().getId());
		List<User> users = new ArrayList<User>();
		for (Integer integer : integers) {
			users.add(getUser(integer));
		}
		model.addAttribute("employees", users);
		return "companyManager";
	}
	
	@PostMapping("/signCompany")
	public String signCompany(@Valid @ModelAttribute("newCompany") CompanyForm newCompany,
			HttpSession session, Errors errors) {
		if (errors.hasErrors()) {
			return "companyManager/signCompany";
		}
		CompanyRequest companyRequest = new CompanyRequest();
		companyRequest.setName(newCompany.getName());
		companyRequest.setDescription(newCompany.getDescription());
		user = (User) session.getAttribute("loginUser");
		companyRequest.setUser(user);
		companyRequestMapper.insert(companyRequest);
		return "redirect:/home";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") int id, HttpSession session, Model model) {
		if (session.getAttribute("loginUser") == null) {
			return "redirect:login";
		}
		user = (User) session.getAttribute("loginUser");
		if (!user.getIsAdmin() || user.getCompany() == null) {
			return "redirect:/home";
		}
		userMapper.setCompany(id, -1);
		return "redirect:/companyManage";
	}
	
	@GetMapping("/judge/{id}")
	public String judgeUser(@PathVariable("id") int id, HttpSession session, Model model) {
		if (session.getAttribute("loginUser") == null) {
			return "redirect:login";
		}
		user = (User) session.getAttribute("loginUser");
		if (!user.getIsAdmin() || user.getCompany() == null) {
			return "redirect:/home";
		}
		User judgedUser = getUser(id);
		model.addAttribute("loginUser", user);
		Judge judge = judgeMapper.queryCompanyJudge(id, user.getCompany().getId());
		if (judge != null) {
			model.addAttribute("judgement", judge.getJudgement());
		}
		model.addAttribute("judgedUser", judgedUser);
		return "companyManager/judgeUser";
	}
	
	@RequestMapping(value = "/submitUserJudge", method = GET)
	public String deliveringPackages(@RequestParam(value = "judgement") int judgement,
			@RequestParam(value = "userId") int id, Model model, HttpSession session) {
			User judgedUser = getUser(id);
			Judge newJudege = new Judge();
			user = (User) session.getAttribute("loginUser");
			Judge judge = judgeMapper.queryCompanyJudge(id, user.getCompany().getId());
			
			if (judge == null) {
				newJudege.setJudgement(judgement);
				newJudege.setJudgeDate(new Date());
				newJudege.setUser(judgedUser);
				newJudege.setCompany(user.getCompany());
				judgeMapper.insertCompanyJudge(newJudege);
			}else {
				judge.setJudgement(judgement);
				judge.setJudgeDate(new Date());
				judgeMapper.updateCompanyJudge(judge);
			}
			return "redirect:/companyManage";
	}
	
	public User getUser(int userId) {
		User returnUser = userMapper.getOne(userId);
		returnUser.setCompany( companyMapper.getJobCompany(returnUser.getId()) );
		Job job = jobMapper.getUserJob(userId);
		returnUser.setJob(job);
		CompanyRelation companyRelation = userMapper.getCompany(userId);
		if (companyRelation != null && companyRelation.getCompanyId() != -1) {
			returnUser.setCompany(companyMapper.getJobCompany(companyRelation.getCompanyId()));
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
