package org.callistasoftware.netcare.video.core.spi;

import org.callistasoftware.netcare.service.api.ServiceResult;
import org.callistasoftware.netcare.video.core.api.VideoBooking;
import org.callistasoftware.netcare.video.core.api.VideoMeetingFormBean;
import org.callistasoftware.netcare.video.core.exception.ServiceException;

/**
 * Service interface for video bookings
 * 
 * @author Marcus Krantz [marcus.krantz@callistaenterprise.se]
 */
public interface VideoBookingService {

	/**
	 * Get all video bookings for a user
	 * @param user The user to fetch video bookings for
	 * @param includePastBookings Whether to include past bookings or not
	 * @return
	 */
	ServiceResult<VideoBooking[]> getBookingsForUser(final Long user, boolean includePastBookings);
	
	/**
	 * Get all video booking for a care unit
	 * @param careUnit
	 * @param includePastBookings
	 * @return
	 */
	ServiceResult<VideoBooking[]> getBookingsForCareUnit(final Long careUnit, boolean includePastBookings);
	
	/**
	 * Load a specific video booking
	 * @param bookingId
	 * @return
	 */
	ServiceResult<VideoBooking> loadVideoBooking(final Long bookingId) throws ServiceException;
	
	/**
	 * The current user leaves the video meeting
	 * @param bookingId
	 * @throws ServiceException
	 */
	ServiceResult<Boolean> leaveVideoMeeting(final Long bookingId) throws ServiceException;
	
	/**
	 * Create a new video meeting
	 * @param data
	 * @return
	 */
	ServiceResult<VideoBooking> createNewVideoMeeting(final VideoMeetingFormBean data);
}
