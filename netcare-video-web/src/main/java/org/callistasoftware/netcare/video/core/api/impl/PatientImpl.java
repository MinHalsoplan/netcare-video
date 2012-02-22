package org.callistasoftware.netcare.video.core.api.impl;

import java.util.Collection;
import java.util.Collections;

import org.callistasoftware.netcare.video.core.api.Patient;
import org.callistasoftware.netcare.video.model.entity.PatientEntity;
import org.springframework.security.core.GrantedAuthority;

public class PatientImpl extends UserImpl implements Patient {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String civicRegistrationNumber;
	
	PatientImpl(Long id, String name, String email, final String civicRegistrationNumber) {
		super(id, name, email);
		this.civicRegistrationNumber = civicRegistrationNumber;
	}
	
	public static Patient newFromEntity(final PatientEntity patient) {
		return new PatientImpl(patient.getId(), patient.getName(), patient.getEmail(), patient.getCivicRegistrationNumber());
	}

	@Override
	public String getCivicRegistrationNumber() {
		return this.civicRegistrationNumber;
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
				return "ROLE_PATIENT";
			}
		});
	}

	@Override
	public String getUsername() {
		return this.getName();
	}

	@Override
	public boolean isCareGiver() {
		return false;
	}
}
