package org.syaku.apps.helloworld.web.apps.user.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.syaku.spring.security.session.SessionInformationSupport;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2016. 9. 21.
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private SessionRegistry sessionRegistry;

	@Value("#{config.usernameParameter}")
	String usernameParameter;
	@Value("#{config.passwordParameter}")
	String passwordParameter;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String dispMemberLogin(Model model) {
		model.addAttribute("usernameParameter", usernameParameter);
		model.addAttribute("passwordParameter", passwordParameter);
		return "login";
	}


	@RequestMapping(value = "/mypage", method = RequestMethod.GET)
	public String dispMemberMypage(Model model, HttpSession session) {
		model.addAttribute("sessionId", session.getId());
		return "mypage";
	}

	@RequestMapping(value = "/visitor", method = RequestMethod.GET)
	public String dispMemberVisitor(Model model) {
		SessionInformationSupport sessionInformationSupport = new SessionInformationSupport(sessionRegistry);
		model.addAttribute("visitors", sessionInformationSupport.getSessionInformations());

		return "visitor";
	}

	@RequestMapping(value = "/visitor", method = RequestMethod.DELETE)
	public @ResponseBody
	Map<String, Object> procMemberVisitorDelete(@RequestBody Map< String, Object> data) {

		SessionInformation session = sessionRegistry.getSessionInformation((String) data.get("sessionId"));
		if (session != null) {
			session.expireNow();
		}

		return new HashMap<>();
	}

	@RequestMapping(value = "/error/{id}", method = RequestMethod.GET)
	public String dispMemberMypage(@PathVariable("id") String error) {
		return "error/" + error;
	}
}