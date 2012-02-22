package org.callistasoftware.netcare.video.model.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="video_booking_user")
public class VideoParticipantEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(nullable=false)
	private String stream;
	
	@Column
	private boolean owner;
	
	@Column
	private boolean connected;
	
	@ManyToOne(optional=false)
	private VideoMeetingEntity booking;
	
	@ManyToOne(optional=false)
	private UserEntity user;
	
	VideoParticipantEntity() {}
	
	VideoParticipantEntity(final VideoMeetingEntity booking, final UserEntity user, final boolean owner) {
		this.setBooking(booking);
		this.setUser(user);
		this.setOwner(owner);
		
		this.setStream(UUID.randomUUID().toString());
	}
	
	public Long getId() {
		return this.id;
	}
	
	void setId(final Long id) {
		this.id = id;
	}

	public String getStream() {
		return stream;
	}

	void setStream(String stream) {
		this.stream = stream;
	}

	public boolean isOwner() {
		return owner;
	}

	public void setOwner(boolean owner) {
		this.owner = owner;
	}

	public VideoMeetingEntity getBooking() {
		return booking;
	}

	public void setBooking(VideoMeetingEntity booking) {
		this.booking = booking;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}
}
