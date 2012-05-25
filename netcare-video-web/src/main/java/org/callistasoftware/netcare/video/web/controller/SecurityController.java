package org.callistasoftware.netcare.video.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/security")
public class SecurityController extends ControllerSupport {
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String displayLogin() {
		return "login";
	}
	
	@RequestMapping(value="/logout")
	public String logout(final HttpSession sc, final HttpServletRequest request) {
		getLog().info("Logout");
		SecurityContextHolder.clearContext();
		request.getSession(false).invalidate();
		
		return "redirect:/web/security/loggedout";
		
//		if (WebUtil.isProfileActive(sc.getServletContext(), "qa") || WebUtil.isProfileActive(sc.getServletContext(), "prod")) {
//			return "redirect:/netcare/security/loggedout";
//		} else {
//			return "redirect:/netcare/security/login";
//		}
	}
	
	@RequestMapping(value="/loggedout")
	public String loggedout() {
		return "loggedout";
	}
	
	@RequestMapping(value="/denied")
	public String denied() {
		return "denied";
	}
}
