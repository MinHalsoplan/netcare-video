package org.callistasoftware.netcare.video.core.api.impl;

import org.callistasoftware.netcare.video.core.api.User;
import org.callistasoftware.netcare.video.core.api.VideoParticipant;
import org.callistasoftware.netcare.video.model.entity.CareGiverEntity;
import org.callistasoftware.netcare.video.model.entity.PatientEntity;
import org.callistasoftware.netcare.video.model.entity.VideoParticipantEntity;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class VideoParticipantImpl implements VideoParticipant {

	private User user;
	private String stream;
	private boolean connected;
	
	VideoParticipantImpl() {
	
	}
	
	public VideoParticipantImpl(final User user, final String stream, final boolean connected) {
		this.user = user;
		this.stream = stream;
		this.connected = connected;
	}
	
	public static VideoParticipant newFromEntity(final VideoParticipantEntity entity) {
		if (entity.getUser().isCareGiver()) {
			return new VideoParticipantImpl(CareGiverImpl.newFromEntity((CareGiverEntity) entity.getUser()), entity.getStream(), entity.isConnected());
		} else {
			return new VideoParticipantImpl(PatientImpl.newFromEntity((PatientEntity) entity.getUser()), entity.getStream(), entity.isConnected());
		}
		
	}
	
	@Override
	public User getUser() {
		return this.user;
	}
	
	public void setUser(final User user) {
		this.user = user;
	}

	@Override
	public String getStream() {
		return this.stream;
	}
	
	public void setStream(final String stream) {
		this.stream = stream;
	}

	@Override
	public boolean isConnected() {
		return this.connected;
	}
	
	public void setConnected(final boolean connected) {
		this.connected = connected;
	}

}
