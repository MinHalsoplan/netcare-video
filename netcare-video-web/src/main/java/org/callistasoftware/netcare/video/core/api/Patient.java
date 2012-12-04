package org.callistasoftware.netcare.video.core.api;

import org.callistasoftware.netcare.video.core.api.impl.PatientImpl;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

/**
 * Definition of a patient
 * 
 * @author Marcus Krantz [marcus.krantz@callistaenterprise.se]
 *
 */
@JsonTypeName("patient")
@JsonDeserialize(as=PatientImpl.class)
public interface Patient extends User {

	/**
	 * Get the patient's civic registration number
	 * @return
	 */
	String getCivicRegistrationNumber();
}
