package com.tema.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tema.entities.Job;
import com.tema.entities.JobRequest;
import com.tema.entities.Resume;
import com.tema.entities.User;
import com.tema.mapper.CompanyMapper;
import com.tema.mapper.JobMapper;
import com.tema.mapper.JobRequestMapper;
import com.tema.mapper.ResumeMapper;
import com.tema.mapper.UserMapper;
import com.tema.utils.getKeyWords;

@Controller
@RequestMapping("jobs")
public class JobsController {
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
	@Autowired
	SolrClient solrClient;
	
	User user = null;
	Job job = null;
	/**
	 * 访问用户个人主页
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping
	public String visitHome(HttpSession session, Model model) {
		//从数据库取出用户
		if (session.getAttribute("loginUser") == null) {
			return "redirect:/login";
		}
		user = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", user);
		List<Job> jobs = jobMapper.findPage(0, 100);
		model.addAttribute("jobs", jobs);
		
		return "redirect:/jobs/findJobs";
	}
	
	//智能推荐
	@RequestMapping(value = "/recommend", method = GET)
	public String visitRecommend(HttpSession session, Model model,
			@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
		//从数据库取出用户
		if (session.getAttribute("loginUser") == null) {
			return "redirect:login";
		}
		user = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", user);
		List<String> histories = userMapper.getSearchHistory(user.getId());
		if (histories.size() == 0) {
			return "redirect:/jobs/findJobs";
		}
		String keyWord = "";
		for (String history : histories) {
			keyWord += (history + " ");
		}
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.set("q", keyWord);
		solrQuery.set("df", "resource_keywords");
		int start = pageNo * pageSize;
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("recommend", true);
		solrQuery.set("start", start);
		solrQuery.set("rows", pageSize);
		try {
			QueryResponse queryResponse = solrClient.query(solrQuery);
			SolrDocumentList results = queryResponse.getResults();
			long allNum = results.getNumFound();
			model.addAttribute("allPages", allNum / pageSize + 1);
			ArrayList<Job> jobList = new ArrayList<>();
			
			for (SolrDocument result : results) {
				Job job = new Job();
				job.setId(Integer.parseInt(result.get("id").toString()));
				job.setName(result.get("name").toString());
				job.setSalary((Integer)result.get("salary"));
				job.setEndTime((Date)result.get("endtime"));
				job.setDescription(result.get("description").toString());
	            jobList.add(job);
	            model.addAttribute("jobs", jobList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "findJobs";
	}
	
	@RequestMapping(value = "/findJobs", method = GET)
	public String deliveringPackages(@RequestParam(value = "keyword", defaultValue = "") String keyword,
			@RequestParam(value = "salary", defaultValue = "0") int salary,
			@RequestParam(value = "education", defaultValue = "0") int education,
			@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "5") int pageSize, Model model, HttpSession session) {
		user = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", user);
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.addHighlightField("name,description");

		solrQuery.setHighlightSimplePre("<font color='red'>");
		solrQuery.setHighlightSimplePost("</font>");
		//若设置了关键词
		if (!keyword.equals("")) {
			//回显关键词
			List<String> histories  = getKeyWords.getAllWordsMatched(keyword);
			for (String history : histories) {
				userMapper.addHistory(user.getId(), history);
			}
			model.addAttribute("keyword", keyword);
			solrQuery.set("q", keyword);
			solrQuery.set("df", "resource_keywords");
		}else {
			//否则查询全部
			solrQuery.set("q", "*:*");
		}
		int aboveS;
		//回显薪资和学历
		model.addAttribute("salary", salary);
		model.addAttribute("education", education);
		switch (salary) {
		case 0:
			aboveS = 0;
			break;
		case 1:
			aboveS = 3000;
			break;
		case 2:
			aboveS = 5000;
			break;
		case 3:
			aboveS = 10000;
			break;
		case 4:
			aboveS = 20000;
			break;
		default:
			aboveS = 0;
			break;
		}
		int start = pageNo * pageSize;
		model.addAttribute("pageNo", pageNo);
		solrQuery.set("start", start);
		solrQuery.set("rows", pageSize);
		if (education != 0) {
			solrQuery.set("fq", "salary:[" + aboveS +" TO *]", "education:[" + education +" TO *]");
		}else {
			solrQuery.set("fq", "salary:[" + aboveS +" TO *]");
		}
		try {
			QueryResponse queryResponse = solrClient.query(solrQuery);
			SolrDocumentList results = queryResponse.getResults();
			long allNum = results.getNumFound();
			model.addAttribute("allPages", allNum / pageSize + 1);
			ArrayList<Job> jobList = new ArrayList<>();
			//高亮
			Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
			for (SolrDocument result : results) {
				Job job = new Job();
				job.setId(Integer.parseInt(result.get("id").toString()));
				job.setName(result.get("name").toString());
				job.setSalary((Integer)result.get("salary"));
				job.setEndTime((Date)result.get("endtime"));
				job.setDescription(result.get("description").toString());
				Map<String, List<String>> map = highlighting.get(result.get("id"));
				List<String> list = map.get("name");
	            if(null != list && list.size() > 0){
	                String name = list.get(0);
	                job.setName(name);
	            }
	            //截取字符串，使高亮部分位于第一位
	            List<String> list1 = map.get("description");
	            if(null != list1 && list1.size() > 0){
	                String description = list1.get(0);
	                int index = description.indexOf("<font color='red'>");
	                if (index != -1 && index != 0) {
	                	description = "..." + description.substring(index);
	                }
	                job.setDescription(description);
	            }
	            jobList.add(job);
	            model.addAttribute("jobs", jobList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "findJobs";
	}
	
	@GetMapping("/detial/{id}")
	public String detial(@PathVariable("id") int id, HttpSession session, Model model) {
		if (session.getAttribute("loginUser") == null) {
			return "redirect:login";
		}
		user = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", user);
		job = jobMapper.findJob(id);
		job.setCompany(companyMapper.getJobCompany(job.getId()));
		session.setAttribute("job", job);
		model.addAttribute("job", job);
		return "jobs/detial";
	}
	
	@RequestMapping("/resumes")
	public String resumes(HttpSession session, Model model) {
		if (session.getAttribute("loginUser") == null) {
			return "redirect:login";
		}
		user = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", user);
		List<Resume> resumes = resumeMapper.findPageByUserId(user.getId(), 0, 100);
		model.addAttribute("resumes", resumes);
		return "jobs/myResumes";
	}
	
	/**投递简历
	 * 
	 * @param id
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/send/{id}")
	public String sendResume(@PathVariable("id") int id, HttpSession session, Model model) {
		if (session.getAttribute("loginUser") == null) {
			return "redirect:login";
		}
		user = (User) session.getAttribute("loginUser");
		if (user.getCompany() != null) {
			model.addAttribute("sendResumeError", "您已经有公司了，不能再投递简历了");
			return "redirect:/jobs";
		}
		model.addAttribute("loginUser", user);
		JobRequest jobRequest = new JobRequest();
		jobRequest.setAcceptance(false);
		jobRequest.setFinalAcceptance(false);
		job = (Job) session.getAttribute("job");
		jobRequest.setJob(job);
		jobRequest.setResume(resumeMapper.findById(id));
		jobRequest.setUser(user);
		jobRequestMapper.insert(jobRequest);
		return "redirect:/jobs";
	}
}
