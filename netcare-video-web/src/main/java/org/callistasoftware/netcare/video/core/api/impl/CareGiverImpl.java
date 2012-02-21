package org.callistasoftware.netcare.video.core.api.impl;

import java.util.Collection;
import java.util.Collections;

import org.callistasoftware.netcare.video.core.api.CareGiver;
import org.callistasoftware.netcare.video.model.entity.CareGiverEntity;
import org.springframework.security.core.GrantedAuthority;

public class CareGiverImpl extends UserImpl implements CareGiver {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String hsaId;
	
	protected CareGiverImpl(Long id, String name, String email, final String hsaId) {
		super(id, name, email);
		this.hsaId = hsaId;
	}
	
	public static CareGiver newFromEntity(final CareGiverEntity entity) {
		return new CareGiverImpl(entity.getId(), entity.getName(), entity.getEmail(), entity.getHsaId());
	}
	
	@Override
	public String getHsaId() {
		return this.hsaId;
	}

	@Override
	public String getUsername() {
		return this.hsaId;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new GrantedAuthority() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public String getAuthority() {
				return "ROLE_CAREGIVER";
			}
		});
	}

	@Override
	public boolean isCareGiver() {
		return true;
	}

}
