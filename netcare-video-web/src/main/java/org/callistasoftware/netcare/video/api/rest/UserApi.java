package org.callistasoftware.netcare.video.api.rest;

import org.callistasoftware.netcare.service.api.ServiceResult;
import org.callistasoftware.netcare.video.core.api.User;
import org.callistasoftware.netcare.video.core.spi.UserDetailsService;
import org.callistasoftware.netcare.video.web.controller.ControllerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserApi extends ControllerSupport {
	
	@Autowired
	private UserDetailsService service;
	
	@RequestMapping(value="/search", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public ServiceResult<User[]> findUsers(@RequestParam("searchterm") final String searchTerm) {
		getLog().debug("Searhing for {}", searchTerm);
		return this.service.findUsersBySearch(searchTerm);
	}
	
	@RequestMapping(value="/saveUserData", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResult<Boolean> saveUserData(@RequestParam(value="firstName") final String firstName, @RequestParam(value="surName") final String surName) {
		getLog().debug("Saving user data {} {}", firstName, surName);
		return this.service.saveUser(firstName + " " + surName);
	}
}
