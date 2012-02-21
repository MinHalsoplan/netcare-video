package org.callistasoftware.netcare.video.api.rest;

import org.callistasoftware.netcare.video.core.api.VideoBooking;
import org.callistasoftware.netcare.video.core.exception.ServiceException;
import org.callistasoftware.netcare.video.core.spi.VideoBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/meeting")
public class VideoApi {

	@Autowired
	private VideoBookingService service;
	
	@RequestMapping(value="/{booking}/status", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public VideoBooking checkParticipantStatus(@PathVariable("booking") final Long booking) throws ServiceException {
		return service.loadVideoBooking(booking);
	}
	
	@RequestMapping(value="/{booking}/leave", method=RequestMethod.POST) 
	@ResponseBody
	public Boolean leaveVideoMeeting(@PathVariable("booking") final Long booking) throws ServiceException {
		this.service.leaveVideoMeeting(booking);
		return Boolean.TRUE;
	}
}
