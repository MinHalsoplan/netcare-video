package org.callistasoftware.netcare.video.model.repository;

import java.util.List;

import org.callistasoftware.netcare.video.model.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

	/**
	 * Find patient by civic registration number
	 * @param civicRegistrationNumber
	 * @return
	 */
	PatientEntity findByCivicRegistrationNumber(final String civicRegistrationNumber);
	
	/**
	 * Find patients by free text search
	 * @param search
	 * @return
	 */
	@Query(value="select e from PatientEntity as e where lower(e.name) like :search or lower(e.civicRegistrationNumber) like :search")
	List<PatientEntity> findBySearch(@Param("search") final String search);
}
