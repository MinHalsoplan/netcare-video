package org.callistasoftware.netcare.video.core.api;

import java.io.Serializable;
import java.util.List;

import org.callistasoftware.netcare.video.core.api.impl.VideoBookingImpl;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(as=VideoBookingImpl.class)
public interface VideoBooking extends Serializable {

	/**
	 * The id of this video booking
	 * @return
	 */
	Long getId();
	
	/**
	 * Get the name of this meeting
	 * @return
	 */
	String getName();
	
	/**
	 * The care unit that owns the meeting
	 * @return
	 */
	CareUnit getCareUnit();
	
	/**
	 * The care giver who created the meeting
	 * @return
	 */
	CareGiver getCreatedBy();
	
	/**
	 * Get the description of this meeting
	 * @return
	 */
	String getDescription();
	
	/**
	 * The start date and time for this video booking
	 * @return
	 */
	String getStart();
	
	/**
	 * The end date and time for this video booking
	 * @return
	 */
	String getEnd();
	
	/**
	 * Whether the booking has been started by a user
	 * @return
	 */
	boolean isStarted();
	
	/**
	 * Get the participants of this video meeting
	 * @return
	 */
	List<VideoParticipant> getParticipants();
}
