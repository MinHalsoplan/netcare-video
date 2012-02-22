package org.callistasoftware.netcare.video.core.api.impl;

import org.callistasoftware.netcare.video.core.api.CareUnit;
import org.callistasoftware.netcare.video.model.entity.CareUnitEntity;

public class CareUnitImpl implements CareUnit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final Long id;
	private final String hsaId;
	private final String name;
	
	CareUnitImpl(final Long id, final String hsaId, final String name) {
		this.id = id;
		this.hsaId = hsaId;
		this.name = name;
	}
	
	public static CareUnit newFromEntity(final CareUnitEntity entity) {
		return new CareUnitImpl(entity.getId(), entity.getHsaId(), entity.getName());
	}
	
	@Override
	public String getHsaId() {
		return this.hsaId;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Long getId() {
		return this.id;
	}

}
