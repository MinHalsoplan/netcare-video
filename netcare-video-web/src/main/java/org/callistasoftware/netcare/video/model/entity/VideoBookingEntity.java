package org.callistasoftware.netcare.video.model.entity;

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
	private Date start;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date end;
	
	@Column
	private boolean started;
	
	@OneToMany(mappedBy="booking", cascade={ CascadeType.PERSIST, CascadeType.REMOVE }, orphanRemoval=true, fetch=FetchType.LAZY)
	private List<VideoBookingParticipantEntity> participants;

	VideoBookingEntity(final Date start) {
		this.setStart(start);
		this.setStarted(false);
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

	public Date getStart() {
		return start;
	}

	void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	void setEnd(Date end) {
		this.end = end;
	}

	public boolean isStarted() {
		return started;
	}

	void setStarted(boolean started) {
		this.started = started;
	}
	
	public void addParticipant(final UserEntity user, final boolean owner) {
		this.getParticipants().add(new VideoBookingParticipantEntity(this, user, owner));
	}
	
	public List<VideoBookingParticipantEntity> getParticipants() {
		return this.participants;
	}
	
	void setVideoBookingParticipants(final List<VideoBookingParticipantEntity> participants) {
		this.participants = participants;
	}
}
