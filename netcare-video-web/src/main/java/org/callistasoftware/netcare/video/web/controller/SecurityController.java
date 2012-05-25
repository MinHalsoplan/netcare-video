package org.callistasoftware.netcare.video.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.callistasoftware.netcare.video.web.util.WebUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value="/security")
public class SecurityController extends ControllerSupport {
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String displayLogin(@RequestParam(value="guid", required=false) final String guid, final HttpSession sc, final Model m) {
		if ((WebUtil.isProfileActive(sc.getServletContext(), "prod") || WebUtil.isProfileActive(sc.getServletContext(), "qa")) && guid != null) {
			m.addAttribute("guid", guid);
			return "redirect:/web/setup";
		}
		
		if (WebUtil.isProfileActive(sc.getServletContext(), "prod") && guid == null) {
			return "redirect:/web/security/denied";
		}
		
		if (WebUtil.isProfileActive(sc.getServletContext(), "qa")) {
			return "redirect:/web/mvk/token/forPatient";
		}
		
		if (WebUtil.isProfileActive(sc.getServletContext(), "test")) {
			return "login";
		}
		
		throw new RuntimeException("Could not determine application mode.");
	}
	
	@RequestMapping(value="/logout")
	public String logout(final HttpSession sc, final HttpServletRequest request) {
		getLog().info("Logout");
		SecurityContextHolder.clearContext();
		request.getSession(false).invalidate();
		
		if (WebUtil.isProfileActive(sc.getServletContext(), "qa") || WebUtil.isProfileActive(sc.getServletContext(), "prod")) {
			return "redirect:/web/security/loggedout";
		} else {
			return "redirect:/web/security/login";
		}
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
