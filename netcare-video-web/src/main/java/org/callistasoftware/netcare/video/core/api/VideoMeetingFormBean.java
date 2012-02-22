package org.callistasoftware.netcare.video.core.api;

import java.io.Serializable;

public class VideoMeetingFormBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String description;
	
	private String date;
	private String start;
	private String end;
	
	private int[] participants;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public int[] getParticipants() {
		return participants;
	}

	public void setParticipants(int[] participants) {
		this.participants = participants;
	}
}
