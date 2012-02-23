package org.callistasoftware.netcare.video.api.rest;

import org.callistasoftware.netcare.service.api.ServiceResult;
import org.callistasoftware.netcare.video.core.api.VideoBooking;
import org.callistasoftware.netcare.video.core.api.VideoMeetingFormBean;
import org.callistasoftware.netcare.video.core.exception.ServiceException;
import org.callistasoftware.netcare.video.core.spi.VideoBookingService;
import org.callistasoftware.netcare.video.web.controller.ControllerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/meeting")
public class VideoMeetingApi extends ControllerSupport {

	@Autowired
	private VideoBookingService service;
	
	@RequestMapping(value="/new", method=RequestMethod.POST, produces="application/json", consumes="application/json")
	@ResponseBody
	public ServiceResult<VideoBooking> createNewMeeting(@RequestBody final VideoMeetingFormBean data) {
		getLog().info("Creating new video meeting");
		return this.service.createNewVideoMeeting(data);
	}
	
	@RequestMapping(value="/{meeting}/delete", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResult<Boolean> deleteVideoMeeting(@PathVariable("meeting") final Long meeting) {
		getLog().info("Delete video meeting {}", meeting);
		return this.service.deleteVideoMeeting(meeting);
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public ServiceResult<VideoBooking[]> loadAllMeetingsForUser() {
		return this.service.getBookingsForUser(getCurrentUser().getId(), false);
	}
	
	@RequestMapping(value="/{careUnit}/list", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public ServiceResult<VideoBooking[]> loadAllMeetingsOnCareUnit(@PathVariable("careUnit") final Long careUnit) {
		return this.service.getBookingsForCareUnit(careUnit, false);
	}
	
	@RequestMapping(value="/{booking}/status", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public ServiceResult<VideoBooking> checkParticipantStatus(@PathVariable("booking") final Long booking) throws ServiceException {
		return service.loadVideoBooking(booking);
	}
	
	@RequestMapping(value="/{booking}/leave", method=RequestMethod.POST) 
	@ResponseBody
	public ServiceResult<Boolean> leaveVideoMeeting(@PathVariable("booking") final Long booking) throws ServiceException {
		return this.service.leaveVideoMeeting(booking);
	}
}
