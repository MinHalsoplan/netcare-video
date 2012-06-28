package org.callistasoftware.netcare.video.core.spi.impl;

import java.util.List;

import org.callistasoftware.netcare.service.api.ServiceResult;
import org.callistasoftware.netcare.service.api.impl.ServiceResultImpl;
import org.callistasoftware.netcare.service.messages.EntityDeletedMessage;
import org.callistasoftware.netcare.service.messages.EntityNotFoundMessage;
import org.callistasoftware.netcare.service.messages.GenericSuccessMessage;
import org.callistasoftware.netcare.service.messages.ListEntitiesMessage;
import org.callistasoftware.netcare.service.util.DateUtil;
import org.callistasoftware.netcare.video.core.api.MeetingNote;
import org.callistasoftware.netcare.video.core.api.MeetingNoteFormBean;
import org.callistasoftware.netcare.video.core.api.VideoBooking;
import org.callistasoftware.netcare.video.core.api.VideoMeetingFormBean;
import org.callistasoftware.netcare.video.core.api.impl.MeetingNoteImpl;
import org.callistasoftware.netcare.video.core.api.impl.VideoBookingImpl;
import org.callistasoftware.netcare.video.core.exception.ServiceException;
import org.callistasoftware.netcare.video.core.spi.VideoBookingService;
import org.callistasoftware.netcare.video.model.entity.CareGiverEntity;
import org.callistasoftware.netcare.video.model.entity.MeetingNoteEntity;
import org.callistasoftware.netcare.video.model.entity.PatientEntity;
import org.callistasoftware.netcare.video.model.entity.UserEntity;
import org.callistasoftware.netcare.video.model.entity.VideoMeetingEntity;
import org.callistasoftware.netcare.video.model.entity.VideoParticipantEntity;
import org.callistasoftware.netcare.video.model.repository.CareGiverRepository;
import org.callistasoftware.netcare.video.model.repository.PatientRepository;
import org.callistasoftware.netcare.video.model.repository.VideoMeetingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${rtmp.url}")
	private String rtmpServerUrl;
	
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

	@Override
	public ServiceResult<Boolean> deleteVideoMeeting(Long meeting) {
		log.info("Delete video meeting {}", meeting);
		
		final VideoMeetingEntity vm = this.repo.findOne(meeting);
		if (vm == null) {
			return ServiceResultImpl.createFailedResult(new EntityNotFoundMessage(VideoMeetingEntity.class, meeting));
		}
		
		if (this.getCareGiver().getCareUnit().getId().equals(vm.getCareUnit().getId())) {
			this.repo.delete(vm);
			return ServiceResultImpl.createSuccessResult(Boolean.TRUE, new EntityDeletedMessage(VideoMeetingEntity.class, meeting));
		} else {
			throw new IllegalStateException("A user not belonging to the care unit that owns the video meeting tried to delete it.");
		}
	}

	@Override
	public ServiceResult<Boolean> createMeetingNote(MeetingNoteFormBean note) {
		log.info("Adding meeting note to meeting {}", note.getMeetingId());
		final VideoMeetingEntity meeting = this.repo.findOne(note.getMeetingId());
		final UserEntity user = this.getCurrentUser();
		
		/*
		 * Is meeting ongoing?
		 */
		if (!meeting.isOngoing()) {
			throw new IllegalStateException("Cannot add note to meeting that is not ongoing.");
		}
		
		/*
		 * Is the current user a participant in the meeting
		 */
		if (!meeting.isUserParticipant(user)) {
			throw new IllegalStateException("User that is trying to create a meeting note is not amongst the participants of the meeting.");
		}
		
		meeting.addNote(note.getNote(), getCurrentUser());
		return ServiceResultImpl.createSuccessResult(Boolean.TRUE, new GenericSuccessMessage());
	}

	@Override
	public ServiceResult<MeetingNote[]> loadNotesForMeeting(Long meeting) {
		log.info("Load meeting notes for meeting {}", meeting);
		final VideoMeetingEntity m = this.repo.findOne(meeting);
		if (m == null) {
			return ServiceResultImpl.createFailedResult(new EntityNotFoundMessage(VideoMeetingEntity.class, meeting));
		}
		
		final UserEntity user = this.getCurrentUser();
		final boolean allowed;
		if (user.isCareGiver()) {
			if (((CareGiverEntity) user).getCareUnit().equals(m.getCareUnit().getId())) {
				allowed = true;
			} else if (m.isUserParticipant(user)) {
				allowed = true;
			} else {
				allowed = false;
			}	
		} else if (!m.isUserParticipant(user)) {
			allowed = false;
		} else {
			allowed = false;
		}
		
		if (!allowed) {
			throw new IllegalStateException("User is not allowed to view meeting notes.");
		}
		
		return ServiceResultImpl.createSuccessResult(MeetingNoteImpl.newFromEntities(m.getNotes()), new ListEntitiesMessage(MeetingNoteEntity.class, m.getNotes().size()));
	}

	@Override
	public String getVideoServer() {
		return this.rtmpServerUrl;
	}
}
