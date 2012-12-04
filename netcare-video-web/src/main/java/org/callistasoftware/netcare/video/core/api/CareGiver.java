package org.callistasoftware.netcare.video.core.api;

import org.callistasoftware.netcare.video.core.api.impl.CareGiverImpl;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonTypeName("careactor")
@JsonDeserialize(as=CareGiverImpl.class)
public interface CareGiver extends User {

	/**
	 * Get the care giver's hsa id
	 * @return
	 */
	String getHsaId();
	
	/**
	 * Get the care unit of this care giver
	 * @return
	 */
	CareUnit getCareUnit();
}
