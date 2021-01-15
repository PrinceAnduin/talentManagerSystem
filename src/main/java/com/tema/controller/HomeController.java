package com.tema.controller;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sun.mail.util.MailSSLSocketFactory;
import com.tema.config.SolrImportDataFromPostgresqlSchedule;
import com.tema.entities.JobRequest;
import com.tema.entities.Resume;
import com.tema.entities.User;
import com.tema.forms.ChangePasswordForm;
import com.tema.forms.SignUpForm;
import com.tema.mapper.CompanyMapper;
import com.tema.mapper.JobMapper;
import com.tema.mapper.JobRequestMapper;
import com.tema.mapper.ResumeMapper;
import com.tema.mapper.UserMapper;

@Controller
@RequestMapping("/home")
public class HomeController {
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
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SolrImportDataFromPostgresqlSchedule.class);
	
	List<JobRequest> requests;
	
	private String captcha;
	private String changedEmail;
	SignUpForm signupUser;
	User user = null;
	@RequestMapping
	public String visitHome(HttpSession session, Model model) {
		//从数据库取出用户
		if (session.getAttribute("loginUser") == null) {
				return "redirect:login";
		}
		user = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", user);
		System.out.println(user.getIsHR());

		List<Resume> resumes = resumeMapper.findPageByUserId(user.getId(), 0, 100);
		model.addAttribute("resumes", resumes);
		requests = jobRequestMapper.findSendPage(user.getId(), 0, 100);
		model.addAttribute("requests", requests);
		return "home";
	}
	
	@RequestMapping("/editInformation")
	public String toEditInformation(HttpSession session, Model model) {
		if (session.getAttribute("loginUser") == null) {
			return "redirect:login";
		}
		user = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", user);
		return "home/editInformation";
	}
	
	@PostMapping("/editInformation")
	public String editInformation(User loginUser) {
		user.setBirth(loginUser.getBirth());
		user.setGender(loginUser.getGender());
		user.setName(loginUser.getName());
		user.setPhone(loginUser.getPhone());
		userMapper.update(user);
		return "redirect:/home";
	}
	
	@RequestMapping("/editPassword")
	public String toEditPassword(HttpSession session, Model model) {
		model.addAttribute("user", new ChangePasswordForm());
		return "home/editPassword";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session, Model model) {
		session.removeAttribute("user");
		user = null;
		return "redirect:/";
	}
	
	@PostMapping("/editPassword")
	public String editPassword(@Valid @ModelAttribute("user")ChangePasswordForm loginUser
			, Errors errors, Map<String, Object> map) {
		//确认密码
		if (!loginUser.getPassword().equals(loginUser.getComfirmedPassword())) {
			map.put("comfirmPassWordError", "前后密码需要保持一致");
			return "home/editPassword";
		}
		String md5Passward =  DigestUtils.md5DigestAsHex(loginUser.getOldPassword().getBytes());
		//确认旧密码是否正确
		if (!md5Passward.equals(user.getPassword())) {
			map.put("passwordError", "请确定输入了正确的密码");
			return "home/editPassword";
		}
		md5Passward =  DigestUtils.md5DigestAsHex(loginUser.getPassword().getBytes());
		user.setPassword(md5Passward);
		userMapper.update(user);
		return "redirect:/home";
	}
	
	@RequestMapping("/editEmail")
	public String toEditEmail(HttpSession session, Model model) {
		if (session.getAttribute("loginUser") == null) {
			return "redirect:login";
		}
		user = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", user);
		return "home/editEmail";
	}
	
	@PostMapping("/editEmail")
	public String editEmail(User loginUser, Map<String, Object> map, Model model) throws GeneralSecurityException {
		User queryUser = userMapper.getOneByEmail(loginUser.getEmail());
		if (queryUser != null) {
			map.put("repeatError", "该邮箱已被绑定");
			model.addAttribute("loginUser", user);
			return "home/editEmail";
		}
		changedEmail = loginUser.getEmail();
		StringBuilder sb = new StringBuilder();
		for (int count = 0; count < 5; count ++) {
			int x = (int)((10 + 26) * Math.random());
			if (x < 10) {
				sb.append((char)('0'+x));
			}else {
				x = x - 10;
				sb.append((char)('A'+x));
			}
		}
		//构造一个验证码
		captcha = sb.toString();
		System.out.println(captcha);
		//sendMail(changedEmail);
		model.addAttribute("signupUser", new SignUpForm());
		return "/home/submitCaptcha";
	}
	//userMapper.update(user);
	
	@PostMapping("/submitCaptcha")
	public String verify(@ModelAttribute("signupUser")SignUpForm s
						,  Map<String, Object> map) {
		if (captcha.equals(s.getCaptcha())) {
			user.setEmail(changedEmail);
			userMapper.update(user);
			return "redirect:/home";
		}
		//if captcha is not true, put the message in map
		map.put("captchaError", "error in captcha");
		return "/home/submitCaptcha";
	}
	
	@PostMapping("/uploadResume/{x}")
	public String uploadResume(@PathVariable("x") int x, @RequestParam("resume") MultipartFile file, HttpSession session) {
		String fileName = file.getOriginalFilename();
		if (fileName.indexOf(".") != -1) {
			String type=fileName.substring(fileName.indexOf("."));
			String newName = "/FILE"+new Date().getTime()+type;
			System.out.println(fileName);
			user = (User) session.getAttribute("loginUser");
			int nowId = user.getId();
			String path = "/asserts/file/" + nowId + newName;
		    File tempFile=new File("C:/Users/asus/Desktop/practice/javaweb/telentManageSystem/src/main/resources/static" + path);
		    
		    try{
		        if (!tempFile.getParentFile().exists()){
		            tempFile.getParentFile().mkdirs();//创建父级文件路径
		            if (!tempFile.createNewFile()) {
		            	LOGGER.info("file create failed");
		            }
		        }
		        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
		        file.transferTo(tempFile);
		        System.out.println("1");
		    }catch (IOException e){
		        LOGGER.info(e.getMessage());
		    }
		    Resume resume = new Resume(0, user.getId(), path, "暂无");
		    resumeMapper.insert(resume);
		}
		
		if (x == 0) {
			return "redirect:/home/myResumes";
		}else {
			return "redirect:/jobs/resumes";
		}
		
	}
	
	@RequestMapping("/myResumes")
	public String myResume(HttpSession session, Model model) {
		if (session.getAttribute("loginUser") == null) {
			return "redirect:login";
		}
		user = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", user);
		List<Resume> resumes = resumeMapper.findPageByUserId(user.getId(), 0, 100);
		model.addAttribute("resumes", resumes);
		return "home/myResumes";
	}
	
	@RequestMapping("/myRequests")
	public String myRequests(HttpSession session, Model model) {
		if (session.getAttribute("loginUser") == null) {
			return "redirect:login";
		}
		user = (User) session.getAttribute("loginUser");
		model.addAttribute("loginUser", user);
		List<Resume> resumes = resumeMapper.findPage(0, 100);
		model.addAttribute("resumes", resumes);
		requests = jobRequestMapper.findSendPage(user.getId(), 0, 100);
		model.addAttribute("requests", requests);
		return "home/myRequests";
	}
	 
	/**
	 * 发送邮件
	 * @param email
	 * @throws GeneralSecurityException
	 */
	public void sendMail(String email) throws GeneralSecurityException {
		//reviser and sender
		String to = email;
		String from = "702638473@qq.com";
		//here is host of mail, I use QQ mail
		String host = "smtp.qq.com";
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		properties.put("mail.smtp.auth","true");
		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.ssl.socketFactory", sf);
		
		Session session = Session.getInstance(properties,new Authenticator(){
	        @Override
			public PasswordAuthentication getPasswordAuthentication()
	        {
	        //user and password, remember: DO NOT PUSH PASSWORD TO GITHUB!!!! by zzp
	         return new PasswordAuthentication("702638473@qq.com", "btmadbkioftdbbgh"); //发件人邮件用户名、授权码
	        }
	    });
		
		try {
			//create a message to send HTML doc
			MimeMessage message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress(from));
			
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("欢迎来到打工人之家!");
			//insert captcha here
			String htmlText = "    <table class=\"body\" width=\"100%\" style=\"background-color:#757070d5; min-width:600px\" bgcolor=\"#757070d5\">\r\n" + 
					"        <tr>\r\n" + 
					"        <td class=\"body\" align=\"center\" valign=\"top\" width=\"100%\" style=\"min-width:600px\">\r\n" + 
					"        <center>\r\n" + 
					"        <table width=\"100%\" style=\"min-width:600px;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\">\r\n" + 
					"        <tr>\r\n" + 
					"        <td align=\"center\">\r\n" + 
					"        <table width=\"560\" class=\"panel-padded\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"min-width:560px\">\r\n" + 
					"        <tr height=\"50\">\r\n" + 
					"        <td width=\"100%\" height=\"50\" style=\"line-height:1px; font-size:1px\"> </td>\r\n" + 
					"        </tr>\r\n" + 
					"\r\n" + 
					"        <tr>\r\n" + 
					"        <td width=\"560\" align=\"center\" style=\"font-family:arial,helvetica,sans-serif; font-weight:bold; mso-line-height-rule: exactly; font-size:40px; color:#313131; text-align:left; line-height:75px\">\r\n" + 
					"        <div style=\"text-align:center;line-height:70px\">这是您的验证码<br/>若您未进行任何注册，请忽略</div>\r\n" + 
					"        </td>\r\n" + 
					"        </tr>\r\n" + 
					"\r\n" + 
					"        <tr height=\"30\">\r\n" + 
					"            <td width=\"100%\" height=\"30\" style=\"line-height:1px; font-size:1px\"> </td>\r\n" + 
					"            </tr>\r\n" + 
					"            </table>\r\n" + 
					"            </td>\r\n" + 
					"        </tr>\r\n" + 
					"\r\n" + 
					"        <tr>\r\n" + 
					"            <td width=\"560\" align=\"center\" bgcolor=\"#000000\"; style=\"font-family:arial,helvetica,sans-serif; font-weight:bold; font-size:50px; color:#313131; text-align:left; line-height:75px\">\r\n" + 
					"                <div  style=\"text-align:center; line-height:70px;color:  #c9aa71;\"><i>"+ captcha +"</i></div>\r\n" + 
					"            </td>\r\n" + 
					"        </tr>\r\n" + 
					"        <tr>\r\n" + 
					"            <td align=\"center\">\r\n" + 
					"            <table width=\"560\" class=\"panel-padded\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"min-width:560px\">\r\n" + 
					"            <tr height=\"50\">\r\n" + 
					"            <td width=\"100%\" height=\"50\" style=\"line-height:1px; font-size:1px\"> </td>\r\n" + 
					"        </tr>\r\n" + 
					"        <tr>\r\n" + 
					"            <td width=\"560\" align=\"center\" style=\"font-family:arial,helvetica,sans-serif; font-weight:bold; mso-line-height-rule: exactly; font-size: 35px; color:#313131; text-align:left; line-height:75px\">\r\n" + 
					"            <div style=\"text-align:center;line-height:70px\">感谢使用打工人之家</div>\r\n" + 
					"            </td>\r\n" + 
					"        </tr>\r\n" + 
					"        <tr>\r\n" + 
					"            <td width=\"560\" align=\"center\" style=\"font-family:arial,helvetica,sans-serif; font-weight:bold; mso-line-height-rule: exactly; font-size: 20px; color:#313131; text-align:left; line-height:75px\">\r\n" + 
					"            <div style=\"text-align:center;line-height:70px\"></div>\r\n" + 
					"            </td>\r\n" + 
					"        </tr>\r\n" + 
					"        </table>\r\n" + 
					"        </center>\r\n" + 
					"    </td>\r\n" + 
					"    </tr>\r\n" + 
					"    </table>";
			message.setContent(htmlText, "text/html; charset=UTF-8");
			Transport.send(message);
			System.out.println("Sent message successfully");
		} catch (MessagingException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
