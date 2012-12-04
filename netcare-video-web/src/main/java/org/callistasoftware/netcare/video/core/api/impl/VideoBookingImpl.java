package org.callistasoftware.netcare.video.core.api.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.callistasoftware.netcare.service.util.DateUtil;
import org.callistasoftware.netcare.video.core.api.CareGiver;
import org.callistasoftware.netcare.video.core.api.CareUnit;
import org.callistasoftware.netcare.video.core.api.VideoBooking;
import org.callistasoftware.netcare.video.core.api.VideoParticipant;
import org.callistasoftware.netcare.video.model.entity.VideoMeetingEntity;
import org.callistasoftware.netcare.video.model.entity.VideoParticipantEntity;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Implementation of a video booking dto
 * @author Marcus Krantz [marcus.krantz@callistaenterprise.se]
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class VideoBookingImpl implements VideoBooking {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String name;
	private String description;
	
	private String start;
	private String end;
	
	private boolean started;
	
	private CareUnit careUnit;
	private CareGiver createdBy;
	
	private List<VideoParticipant> participants;
	
	VideoBookingImpl() {
	
	}
	
	public static VideoBooking newFromEntity(final VideoMeetingEntity entity) {
		VideoBookingImpl dto = new VideoBookingImpl();
		dto.id = entity.getId();
		dto.name = entity.getName();
		dto.description = entity.getDescription();
		dto.start = DateUtil.toDateTime(entity.getStartDateTime());
		dto.end = DateUtil.toDateTime(entity.getEndDateTime());
		dto.careUnit = CareUnitImpl.newFromEntity(entity.getCareUnit());
		dto.createdBy = CareGiverImpl.newFromEntity(entity.getCreatedBy());
		
		final Calendar c = Calendar.getInstance();
		long n = c.getTime().getTime();
		dto.started = n >= entity.getStartDateTime().getTime() && n <= entity.getEndDateTime().getTime();
		
		dto.participants = new ArrayList<VideoParticipant>(entity.getParticipants().size());
		for (final VideoParticipantEntity part : entity.getParticipants()) {
			dto.participants.add(VideoParticipantImpl.newFromEntity(part));
		}
		
		return dto;
	}
	
	public static VideoBooking[] newFromEntities(final List<VideoMeetingEntity> entities) {
		final VideoBooking[] dtos = new VideoBooking[entities.size()];
		for (int i = 0; i < entities.size(); i++) {
			dtos[i] = newFromEntity(entities.get(i));
		}
		
		return dtos;
	}
	
	@Override
	public Long getId() {
		return this.id;
	}
	
	@Override
	public String getStart() {
		return this.start;
	}
	
	@Override
	public String getEnd() {
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

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public CareUnit getCareUnit() {
		return careUnit;
	}

	@Override
	public CareGiver getCreatedBy() {
		return createdBy;
	}
}
