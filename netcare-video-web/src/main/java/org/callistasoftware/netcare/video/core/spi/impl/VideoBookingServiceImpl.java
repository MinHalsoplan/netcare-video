package org.callistasoftware.netcare.video.core.spi.impl;

import java.util.List;

import org.callistasoftware.netcare.video.core.api.VideoBooking;
import org.callistasoftware.netcare.video.core.spi.VideoBookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VideoBookingServiceImpl implements VideoBookingService {

	private static final Logger log = LoggerFactory.getLogger(VideoBookingServiceImpl.class);
	
	@Override
	public List<VideoBooking> getBookingsForUser(Long user,
			boolean includePastBookings) {
		log.info("Get video bookings for user {}", user);
		throw new UnsupportedOperationException("Not yet implemented.");
	}

}
