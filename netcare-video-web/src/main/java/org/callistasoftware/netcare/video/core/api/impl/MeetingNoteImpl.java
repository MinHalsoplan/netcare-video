package org.callistasoftware.netcare.video.core.api.impl;

import java.util.List;

import org.callistasoftware.netcare.service.util.DateUtil;
import org.callistasoftware.netcare.video.core.api.MeetingNote;
import org.callistasoftware.netcare.video.core.api.User;
import org.callistasoftware.netcare.video.model.entity.CareGiverEntity;
import org.callistasoftware.netcare.video.model.entity.MeetingNoteEntity;
import org.callistasoftware.netcare.video.model.entity.PatientEntity;

public class MeetingNoteImpl implements MeetingNote {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final Long id;
	private final String note;
	private final User createdBy;
	private final String createdAt;
	
	MeetingNoteImpl(final Long id, final String note, final User createdBy, final String createdAt) {
		this.id = id;
		this.note = note;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
	}
	
	public static MeetingNote newFromEntity(final MeetingNoteEntity entity) {
		final User user;
		if (entity.getCreatedBy().isCareGiver()) {
			user = CareGiverImpl.newFromEntity((CareGiverEntity) entity.getCreatedBy());
		} else {
			user = PatientImpl.newFromEntity((PatientEntity) entity.getCreatedBy());
		}
		
		return new MeetingNoteImpl(entity.getId(), entity.getNote(), user, DateUtil.toDateTime(entity.getCreatedAt()));
	}
	
	public static MeetingNote[] newFromEntities(final List<MeetingNoteEntity> entities) {
		final MeetingNote[] notes = new MeetingNote[entities.size()];
		for (int i = 0; i < entities.size(); i++) {
			notes[i] = newFromEntity(entities.get(i));
		}
		
		return notes;
	}
	
	@Override
	public String getNote() {
		return this.note;
	}

	@Override
	public User getCreatedBy() {
		return this.createdBy;
	}

	@Override
	public String getCreatedAt() {
		return this.createdAt;
	}

	@Override
	public Long getId() {
		return this.id;
	}

}
