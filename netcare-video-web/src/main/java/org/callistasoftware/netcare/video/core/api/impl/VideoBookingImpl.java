package org.callistasoftware.netcare.video.core.api.impl;

import java.util.Date;

import org.callistasoftware.netcare.video.core.api.VideoBooking;

/**
 * Implementation of a video booking dto
 * @author Marcus Krantz [marcus.krantz@callistaenterprise.se]
 *
 */
public class VideoBookingImpl implements VideoBooking {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Date start;
	private Date end;
	
	private boolean started;
	
	@Override
	public Long getId() {
		return this.id;
	}
	@Override
	public Date getStart() {
		return this.start;
	}
	@Override
	public Date getEnd() {
		return this.end;
	}
	@Override
	public boolean isStarted() {
		return this.started;
	}
}
