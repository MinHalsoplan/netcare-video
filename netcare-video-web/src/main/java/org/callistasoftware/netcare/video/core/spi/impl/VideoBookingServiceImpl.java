package org.callistasoftware.netcare.video.core.spi.impl;

import java.util.List;

import org.callistasoftware.netcare.service.api.ServiceResult;
import org.callistasoftware.netcare.service.api.impl.ServiceResultImpl;
import org.callistasoftware.netcare.service.messages.EntityNotFoundMessage;
import org.callistasoftware.netcare.service.messages.GenericSuccessMessage;
import org.callistasoftware.netcare.service.messages.ListEntitiesMessage;
import org.callistasoftware.netcare.service.util.DateUtil;
import org.callistasoftware.netcare.video.core.api.VideoBooking;
import org.callistasoftware.netcare.video.core.api.VideoMeetingFormBean;
import org.callistasoftware.netcare.video.core.api.impl.VideoBookingImpl;
import org.callistasoftware.netcare.video.core.exception.ServiceException;
import org.callistasoftware.netcare.video.core.spi.VideoBookingService;
import org.callistasoftware.netcare.video.model.entity.CareGiverEntity;
import org.callistasoftware.netcare.video.model.entity.PatientEntity;
import org.callistasoftware.netcare.video.model.entity.VideoMeetingEntity;
import org.callistasoftware.netcare.video.model.entity.VideoParticipantEntity;
import org.callistasoftware.netcare.video.model.repository.CareGiverRepository;
import org.callistasoftware.netcare.video.model.repository.PatientRepository;
import org.callistasoftware.netcare.video.model.repository.VideoMeetingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VideoBookingServiceImpl extends ServiceSupport implements VideoBookingService {

	private static final Logger log = LoggerFactory.getLogger(VideoBookingServiceImpl.class);
	
	@Autowired
	private CareGiverRepository cgRepo;
	
	@Autowired
	private PatientRepository pRepo;
	
	@Autowired
	private VideoMeetingRepository repo;
	
	@Override
	public ServiceResult<VideoBooking[]> getBookingsForUser(Long user,
			boolean includePastBookings) {
		log.info("Get video bookings for user {}", user);
		return ServiceResultImpl.createSuccessResult(VideoBookingImpl.newFromEntities(this.repo.findByUser(user)), new GenericSuccessMessage());
	}

	@Override
	public ServiceResult<VideoBooking> loadVideoBooking(Long bookingId) throws ServiceException {
		log.info("Loading video booking {}", bookingId);
		final VideoMeetingEntity booking = this.repo.findOne(bookingId);
		if (booking == null) {
			return ServiceResultImpl.createFailedResult(new EntityNotFoundMessage(VideoMeetingEntity.class, bookingId));
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
			return ServiceResultImpl.createFailedResult(new EntityNotFoundMessage(VideoMeetingEntity.class, bookingId));
		}
		
		return ServiceResultImpl.createSuccessResult(VideoBookingImpl.newFromEntity(booking), new GenericSuccessMessage());
	}

	@Override
	public ServiceResult<Boolean> leaveVideoMeeting(Long bookingId) throws ServiceException {
		log.info("User leaves video meeting");
		final VideoMeetingEntity meeting = this.repo.findOne(bookingId);
		if (meeting == null) {
			return ServiceResultImpl.createFailedResult(new EntityNotFoundMessage(VideoMeetingEntity.class, bookingId));
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
			return ServiceResultImpl.createFailedResult(new EntityNotFoundMessage(VideoMeetingEntity.class, bookingId));
		}
		
		return ServiceResultImpl.createSuccessResult(Boolean.TRUE, new GenericSuccessMessage());
	}

	@Override
	public ServiceResult<VideoBooking[]> getBookingsForCareUnit(Long careUnit,
			boolean includePastBookings) {
		log.info("Get video bookings for care unit {}", careUnit);
		final List<VideoMeetingEntity> ents = this.repo.findByCareUnit(careUnit);
		return ServiceResultImpl.createSuccessResult(VideoBookingImpl.newFromEntities(ents), new ListEntitiesMessage(VideoMeetingEntity.class, ents.size()));
	}

	@Override
	public ServiceResult<VideoBooking> createNewVideoMeeting(
			VideoMeetingFormBean data) {
		log.info("Creating new video meeting {}", data.getName());
		
		final CareGiverEntity cg = this.cgRepo.findOne(getCurrentUserId());
		final VideoMeetingEntity entity = VideoMeetingEntity.newEntity(data.getName(), DateUtil.parseDateTime(data.getDate(), data.getStart()), cg);
		entity.setEndDateTime(DateUtil.parseDateTime(data.getDate(), data.getEnd()));
		entity.setDescription(data.getDescription());
		
		for (int user : data.getParticipants()) {
			final CareGiverEntity c = this.cgRepo.findOne((long) user);
			if (c == null) {
				final PatientEntity p = this.pRepo.findOne((long) user);
				if (p == null) {
					log.warn("Could not find any user matching " + user + " when creating a video meeting.");
				} else {
					entity.addParticipant(p, true);
				}
			} else {
				entity.addParticipant(c, true);
			}
		}
		
		return ServiceResultImpl.createSuccessResult(VideoBookingImpl.newFromEntity(this.repo.save(entity)), new GenericSuccessMessage());
	}
}
