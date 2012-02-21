package org.callistasoftware.netcare.video.core.spi;

import java.util.List;

import org.callistasoftware.netcare.video.core.api.VideoBooking;
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
	List<VideoBooking> getBookingsForUser(final Long user, boolean includePastBookings);
	
	/**
	 * Load a specific video booking
	 * @param bookingId
	 * @return
	 */
	VideoBooking loadVideoBooking(final Long bookingId) throws ServiceException;
	
	/**
	 * The current user leaves the video meeting
	 * @param bookingId
	 * @throws ServiceException
	 */
	void leaveVideoMeeting(final Long bookingId) throws ServiceException;
}
