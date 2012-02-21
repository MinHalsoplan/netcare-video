package org.callistasoftware.netcare.video.core.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.callistasoftware.netcare.video.core.api.VideoBooking;
import org.callistasoftware.netcare.video.core.api.VideoParticipant;
import org.callistasoftware.netcare.video.model.entity.VideoBookingEntity;
import org.callistasoftware.netcare.video.model.entity.VideoParticipantEntity;

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
	
	private List<VideoParticipant> participants;
	
	VideoBookingImpl() {
	
	}
	
	public static VideoBooking newFromEntity(final VideoBookingEntity entity) {
		VideoBookingImpl dto = new VideoBookingImpl();
		dto.id = entity.getId();
		dto.start = entity.getStartDateTime();
		dto.end = entity.getEndDateTime();
		dto.started = entity.isStarted();
		
		dto.participants = new ArrayList<VideoParticipant>(entity.getParticipants().size());
		for (final VideoParticipantEntity part : entity.getParticipants()) {
			dto.participants.add(VideoParticipantImpl.newFromEntity(part));
		}
		
		return dto;
	}
	
	public static List<VideoBooking> newFromEntities(final List<VideoBookingEntity> entities) {
		final List<VideoBooking> dtos = new ArrayList<VideoBooking>(entities.size());
		for (final VideoBookingEntity ent : entities) {
			dtos.add(newFromEntity(ent));
		}
		
		return dtos;
	}
	
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
	
	@Override
	public List<VideoParticipant> getParticipants() {
		return this.participants;
	}
}
