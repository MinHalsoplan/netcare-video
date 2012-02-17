package org.callistasoftware.netcare.video.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class UserEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(unique=true, nullable=false, updatable=false)
	private String username;
	
	UserEntity(final String username) {
		this.setUsername(username);
	}
	
	public static UserEntity newEntity(final String username) {
		return new UserEntity(username);
	}
	
	public Long getId() {
		return this.id;
	}
	
	void setId(final Long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	void setUsername(final String username) {
		this.username = username;
	}
}
