package org.callistasoftware.netcare.video.core.spi.impl;

import java.util.List;

import org.callistasoftware.netcare.video.core.api.VideoBooking;
import org.callistasoftware.netcare.video.core.api.impl.VideoBookingImpl;
import org.callistasoftware.netcare.video.core.exception.ServiceException;
import org.callistasoftware.netcare.video.core.spi.VideoBookingService;
import org.callistasoftware.netcare.video.model.entity.VideoBookingEntity;
import org.callistasoftware.netcare.video.model.entity.VideoParticipantEntity;
import org.callistasoftware.netcare.video.model.repository.VideoBookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VideoBookingServiceImpl extends ServiceSupport implements VideoBookingService {

	private static final Logger log = LoggerFactory.getLogger(VideoBookingServiceImpl.class);
	
	@Autowired
	private VideoBookingRepository repo;
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	public List<VideoBooking> getBookingsForUser(Long user,
			boolean includePastBookings) {
		log.info("Get video bookings for user {}", user);
		return VideoBookingImpl.newFromEntities(this.repo.findByUser(user));
	}

	@Override
	public VideoBooking loadVideoBooking(Long bookingId) throws ServiceException {
		log.info("Loading video booking {}", bookingId);
		final VideoBookingEntity booking = this.repo.findOne(bookingId);
		if (booking == null) {
			throw new ServiceException(this.messageSource.getMessage("exception.noSuchBooking", null, LocaleContextHolder.getLocale()));
		}
		
		final Long currentUserId = this.getCurrentUserId();
		
		boolean found = false;
		for (final VideoParticipantEntity vp : booking.getParticipants()) {
			if (vp.getUser().getId().equals(currentUserId)) {
				vp.setConnected(true);
				found = true;
			}
		}
		
		if (!found) {
			throw new ServiceException(this.messageSource.getMessage("exception.noSuchBooking", null, LocaleContextHolder.getLocale()));
		}
		
		return VideoBookingImpl.newFromEntity(booking);
	}

	@Override
	public void leaveVideoMeeting(Long bookingId) throws ServiceException {
		log.info("User leaves video meeting");
		final VideoBookingEntity meeting = this.repo.findOne(bookingId);
		if (meeting == null) {
			throw new ServiceException(this.messageSource.getMessage("exception.noSuchBooking", null, LocaleContextHolder.getLocale()));
		}
		
		final Long userId = this.getCurrentUserId();
		
		boolean found = false;
		for (final VideoParticipantEntity vp : meeting.getParticipants()) {
			if (vp.getUser().getId().equals(userId)) {
				vp.setConnected(false);
				found = true;
			}
		}
		
		if (!found) {
			throw new ServiceException(this.messageSource.getMessage("exception.noSuchBooking", null, LocaleContextHolder.getLocale()));
		}
	}
}
