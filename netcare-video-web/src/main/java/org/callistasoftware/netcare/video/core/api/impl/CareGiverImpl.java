package org.callistasoftware.netcare.video.core.api.impl;

import java.util.Collection;
import java.util.Collections;

import org.callistasoftware.netcare.video.core.api.CareGiver;
import org.callistasoftware.netcare.video.core.api.CareUnit;
import org.callistasoftware.netcare.video.model.entity.CareGiverEntity;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CareGiverImpl extends UserImpl implements CareGiver {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String hsaId;
	private CareUnit careUnit;
	
	CareGiverImpl() {
		super();
	}
	
	protected CareGiverImpl(Long id, String name, String email, final String hsaId, final CareUnit careUnit) {
		super(id, name, email);
		this.hsaId = hsaId;
		this.careUnit = careUnit;
	}
	
	public static CareGiver newFromEntity(final CareGiverEntity entity) {
		return new CareGiverImpl(entity.getId(), entity.getName(), entity.getEmail(), entity.getHsaId(), CareUnitImpl.newFromEntity(entity.getCareUnit()));
	}
	
	@Override
	public String getHsaId() {
		return this.hsaId;
	}

	@Override
	public String getUsername() {
		return this.getName();
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

	@Override
	public CareUnit getCareUnit() {
		return this.careUnit;
	}

}
