package org.callistasoftware.netcare.video.core.api;

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
