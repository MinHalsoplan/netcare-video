package org.callistasoftware.netcare.video.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="nc_video_meeting_note")
public class MeetingNoteEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String note;
	
	@ManyToOne(optional=false)
	private UserEntity createdBy;
	
	@Column(name="created_at", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	MeetingNoteEntity() {}
	
	MeetingNoteEntity(final String note, final UserEntity creator) {
		this.setNote(note);
		this.setCreatedBy(creator);
		this.setCreatedAt(new Date());
	}
	
	public static MeetingNoteEntity newEntity(final String note, final UserEntity creator) {
		return new MeetingNoteEntity(note, creator);
	}

	public Long getId() {
		return id;
	}

	void setId(Long id) {
		this.id = id;
	}

	public String getNote() {
		return note;
	}

	void setNote(String note) {
		this.note = note;
	}

	public UserEntity getCreatedBy() {
		return createdBy;
	}

	void setCreatedBy(UserEntity createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
}
