package org.callistasoftware.netcare.video.model.repository;

import org.callistasoftware.netcare.video.model.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

	/**
	 * Find patient by civic registration number
	 * @param civicRegistrationNumber
	 * @return
	 */
	PatientEntity findByCivicRegistrationNumber(final String civicRegistrationNumber);
}
