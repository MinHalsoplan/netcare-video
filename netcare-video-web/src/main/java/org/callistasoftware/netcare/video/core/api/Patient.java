package org.callistasoftware.netcare.video.core.api;

/**
 * Definition of a patient
 * 
 * @author Marcus Krantz [marcus.krantz@callistaenterprise.se]
 *
 */
public interface Patient extends User {

	/**
	 * Get the patient's civic registration number
	 * @return
	 */
	String getCivicRegistrationNumber();
}
