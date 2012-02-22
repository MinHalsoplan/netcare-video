package org.callistasoftware.netcare.video.core.api;

import java.io.Serializable;

public interface CareUnit extends Serializable {
	
	/**
	 * Get the id of the care unit
	 * @return
	 */
	Long getId();

	/**
	 * Get the hsa id of this care unit
	 * @return
	 */
	String getHsaId();
	
	/**
	 * Get the name of this care unit
	 * @return
	 */
	String getName();
}
