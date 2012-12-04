package org.callistasoftware.netcare.video.core.api.impl;

import java.util.Collection;

import org.callistasoftware.netcare.video.core.api.User;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;

@JsonIgnoreProperties(ignoreUnknown=true)
public abstract class UserImpl implements User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String email;

	UserImpl() {
	
	}
	
	protected UserImpl(final Long id, final String name, final String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}
	
	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public abstract String getUsername();
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public String getEmail() {
		return this.email;
	}

	@Override
	public abstract Collection<? extends GrantedAuthority> getAuthorities();

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
