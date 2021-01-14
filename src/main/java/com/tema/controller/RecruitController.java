package com.tema.controller;

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
import com.tema.entities.Job;
import com.tema.entities.JobRequest;
import com.tema.entities.User;
import com.tema.forms.JobForm;
import com.tema.mapper.CompanyMapper;
import com.tema.mapper.JobMapper;
import com.tema.mapper.JobRequestMapper;
import com.tema.mapper.ResumeMapper;
import com.tema.mapper.UserMapper;

@Controller
@RequestMapping("/recruit")
public class RecruitController {
	@Autowired
	UserMapper userMapper;
	@Autowired
	ResumeMapper resumeMapper;
	@Autowired
	CompanyMapper companyMapper;
	@Autowired
	JobMapper jobMapper;
	@Autowired
	JobRequestMapper jobRequestMapper;
	User user = null;
	
	List<JobRequest> requests ;

	@RequestMapping
	public String visitHome(HttpSession session, Model model) {
		// 从数据库取出用户
		if (session.getAttribute("loginUser") == null) {
			return "redirect:/login";
		}
		user = (User) session.getAttribute("loginUser");
		if ((user.getJob() == null && !user.getIsAdmin()) || (!user.getIsHR() && !user.getIsAdmin())) {
			return "redirect:/home";
		}
		model.addAttribute("loginUser", user);

		return "recruit";
	}

	@RequestMapping("/addJob")
	public String toAddJob(HttpSession session, Model model) {
		model.addAttribute("newJob", new JobForm());
		user = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", user);
		return ("recruit/addJob");

	}

	@PostMapping("/addJob")
	public String addJob(@Valid @ModelAttribute("newJob") JobForm newJob, Errors errors, HttpSession session, Model model) {
		if (errors.hasErrors()) {
			user = (User) session.getAttribute("loginUser");
			model.addAttribute("loginUser", user);
			return "/recruit/addJob";
		}
		if (user != null  && (user.getIsHR() || user.getIsAdmin())) {
			System.out.println(newJob.getBigAge());
			Job job = new Job();
			job.setCompany(user.getCompany());
			job.setCompanyId(user.getCompany().getId());
			job.setDescription(newJob.getDescription());
			job.setName(newJob.getName());
			job.setNeedsNum(newJob.getNeedsNum());
			
			job.setSalary(newJob.getSalary());
			job.setEndTime(newJob.getEndTime());
			job.setSmallAge(newJob.getSmallAge());
			job.setBigAge(newJob.getBigAge());
			job.setEducation(newJob.getEducation());
			job.setStartDate(new Date());
			job.setNowNum(0);
			jobMapper.insert(job);
		}
		return ("redirect:/home");

	}

	@GetMapping("/checkUsers")
	public String toCheckUsers(@RequestParam(value = "choice", defaultValue = "0") int choice, HttpSession session, Model model) {
		// 从数据库取出用户
		if (session.getAttribute("loginUser") == null) {
			return "redirect:/login";
		}
		user = (User) session.getAttribute("loginUser");
		if ((user.getJob() == null && !user.getIsAdmin()) || (!user.getIsHR() && !user.getIsAdmin())) {
			return "redirect:/home";
		}
		model.addAttribute("loginUser", user);
		if (choice == 0) {
			requests = jobRequestMapper.findReceviePage(user.getCompany().getId(), 0, 100);
		}else if (choice == 1){
			requests = jobRequestMapper.findReceviePageByAcctance(user.getCompany().getId(), 0, 100);
		}else {
			requests = jobRequestMapper.findReceviePageByFinalAcctance(user.getCompany().getId(), 0, 100);
		}
		model.addAttribute("requests", requests);
		return "recruit/checkUsers";
	}
	
	@GetMapping("/accept/{id}")
	public String accpetRequest(@PathVariable("id") int id, HttpSession session, Model model) {
		if (session.getAttribute("loginUser") == null) {
			return "redirect:/login";
		}
		user = (User) session.getAttribute("loginUser");
		if (!user.getIsHR() && !user.getIsAdmin()) {
			return "redirect:/home";
		}
		jobRequestMapper.setRequestAcceptcance(id, true);
		return "redirect:/recruit/checkUsers";
	}
	
	@GetMapping("/finalAccept/{id}")
	public String finalAcceptRequest(@PathVariable("id") int id, HttpSession session, Model model) {
		if (session.getAttribute("loginUser") == null) {
			return "redirect:/login";
		}
		user = (User) session.getAttribute("loginUser");
		if (!user.getIsHR() && !user.getIsAdmin()) {
			return "redirect:/home";
		}
		jobRequestMapper.setFinalAcceptcance(id, true);
		int userId = jobRequestMapper.getUserId(id);
		CompanyRelation companyRelation = userMapper.getCompany(userId);
		//更新用户的公司
		if (companyRelation == null) {
			userMapper.addCompany(userId, user.getCompany().getId());
			
		}else {
			userMapper.setCompany(userId, user.getCompany().getId());
		}
		//更新用户职位
		userMapper.setJob(userId, jobRequestMapper.getJobId(id));
		//同时将用户其他所有申请全部设为无效
		jobRequestMapper.setAllDelete(id);
		return "redirect:/recruit/checkUsers";
	}
	
	@GetMapping("/deny/{id}")
	public String denyRequest(@PathVariable("id") int id, HttpSession session, Model model) {
		if (session.getAttribute("loginUser") == null) {
			return "redirect:/login";
		}
		user = (User) session.getAttribute("loginUser");
		if (!user.getIsHR() && !user.getIsAdmin()) {
			return "redirect:/home";
		}
		jobRequestMapper.setDelete(id, true);
		return "redirect:/recruit/checkUsers";
	}
	
}
