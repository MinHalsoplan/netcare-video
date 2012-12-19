package org.callistasoftware.netcare.video.core.api;

import java.io.Serializable;

public class MeetingNoteFormBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long meetingId;
	private String note;
	
	MeetingNoteFormBean() {}
	
	public Long getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
}
