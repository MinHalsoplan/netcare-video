package org.callistasoftware.netcare.video.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="video_booking")
public class VideoBookingEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDateTime;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDateTime;
	
	@Column
	private boolean started;
	
	@OneToMany(mappedBy="booking", cascade={ CascadeType.PERSIST, CascadeType.REMOVE }, orphanRemoval=true, fetch=FetchType.LAZY)
	private List<VideoParticipantEntity> participants;

	VideoBookingEntity() {}
	
	VideoBookingEntity(final Date start) {
		this.setParticipants(new ArrayList<VideoParticipantEntity>());
		this.setStartDateTime(start);
		this.setStarted(false);
		this.setEndDateTime(new Date(start.getTime() + (60000 * 60)));
	}
	
	public static VideoBookingEntity newEntity(final Date start) {
		return new VideoBookingEntity(start);
	}
	
	public Long getId() {
		return id;
	}

	void setId(Long id) {
		this.id = id;
	}

	public Date getStartDateTime() {
		return startDateTime;
	}

	void setStartDateTime(Date start) {
		this.startDateTime = start;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	void setEndDateTime(Date end) {
		this.endDateTime = end;
	}

	public boolean isStarted() {
		return started;
	}

	void setStarted(boolean started) {
		this.started = started;
	}
	
	public void addParticipant(final UserEntity user, final boolean owner) {
		this.getParticipants().add(new VideoParticipantEntity(this, user, owner));
	}
	
	public List<VideoParticipantEntity> getParticipants() {
		return this.participants;
	}
	
	void setParticipants(final List<VideoParticipantEntity> participants) {
		this.participants = participants;
	}
}
