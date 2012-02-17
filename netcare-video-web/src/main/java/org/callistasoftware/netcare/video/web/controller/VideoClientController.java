package org.callistasoftware.netcare.video.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("")
public class VideoClientController {
	
	private static final Logger log = LoggerFactory.getLogger(VideoClientController.class);
	
	@Value("${rtmp.url}")
	private String rtmpServerUrl;

	@RequestMapping(value="/", method=RequestMethod.GET)
	public String display() {
		return "redirect:/dashboard";
	}
	
	@RequestMapping(value="/home", method=RequestMethod.GET)
	public String displayHome() {
		return "redirect:/dashboard";
	}
	
	@RequestMapping(value="/dashboard", method=RequestMethod.GET)
	public String displayDashboard() {
		return "dashboard";
	}
	
	@RequestMapping(value="/video", method=RequestMethod.GET)
	public String displayConference(@RequestParam(value="booking") final Long bookingId, final Model m) {
		log.info("Display video conference with id {}", bookingId);
		
		log.debug("RTMP server url is: {}", this.rtmpServerUrl);
		m.addAttribute("serverUrl", this.rtmpServerUrl); 
		
		m.addAttribute("consumeStream", "nej");
		m.addAttribute("produceStream", "nej");
		
		return "video";
	}
}
