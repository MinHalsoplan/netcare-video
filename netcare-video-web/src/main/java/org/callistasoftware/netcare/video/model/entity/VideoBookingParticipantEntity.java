package org.callistasoftware.netcare.video.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="video_booking_user")
public class VideoBookingParticipantEntity {

	@Column
	private String stream;
	
	@Column
	private boolean owner;
	
	@ManyToOne
	private VideoBookingEntity booking;
	
	@OneToOne
	private UserEntity user;
	
	VideoBookingParticipantEntity(final VideoBookingEntity booking, final UserEntity user, final boolean owner) {
		this.setBooking(booking);
		this.setUser(user);
		this.setOwner(owner);
	}

	public String getStream() {
		return stream;
	}

	public void setStream(String stream) {
		this.stream = stream;
	}

	public boolean isOwner() {
		return owner;
	}

	public void setOwner(boolean owner) {
		this.owner = owner;
	}

	public VideoBookingEntity getBooking() {
		return booking;
	}

	public void setBooking(VideoBookingEntity booking) {
		this.booking = booking;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}
}
