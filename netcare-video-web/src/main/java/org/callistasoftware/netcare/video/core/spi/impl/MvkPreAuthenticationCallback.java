package org.callistasoftware.netcare.video.core.spi.impl;

import org.callistasoftware.netcare.mvk.authentication.service.api.AuthenticationResult;
import org.callistasoftware.netcare.mvk.authentication.service.api.PreAuthenticationCallback;
import org.callistasoftware.netcare.service.util.EntityUtil;
import org.callistasoftware.netcare.video.core.api.CareGiver;
import org.callistasoftware.netcare.video.core.api.Patient;
import org.callistasoftware.netcare.video.core.api.impl.CareGiverImpl;
import org.callistasoftware.netcare.video.core.api.impl.PatientImpl;
import org.callistasoftware.netcare.video.model.entity.CareGiverEntity;
import org.callistasoftware.netcare.video.model.entity.CareUnitEntity;
import org.callistasoftware.netcare.video.model.entity.PatientEntity;
import org.callistasoftware.netcare.video.model.repository.CareGiverRepository;
import org.callistasoftware.netcare.video.model.repository.CareUnitRepository;
import org.callistasoftware.netcare.video.model.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MvkPreAuthenticationCallback extends ServiceSupport implements PreAuthenticationCallback {

	@Autowired
	private CareUnitRepository cuRepo;
	
	@Autowired
	private CareGiverRepository cgRepo;
	
	@Autowired
	private PatientRepository pRepo;
	
	@Override
	public UserDetails createMissingUser(AuthenticationResult preAuthenticated) {
		getLog().info("User {} has not been here before, create the user...", preAuthenticated.getUsername());
		
		if (preAuthenticated.isCareGiver()) {
			
			getLog().debug("The user is a care giver...");
			
			final String careUnit = preAuthenticated.getCareUnitHsaId();
			CareUnitEntity cu = this.cuRepo.findByHsaId(careUnit);
			if (cu == null) {
				getLog().debug("Could not find care unit {}, create it.", careUnit);
				
				cu = CareUnitEntity.newEntity(careUnit, preAuthenticated.getCareUnitName());
				
				if (preAuthenticated.getCareUnitName() == null) {
					cu.setName("Vårdenhetsnamn saknas");
				} else {
					cu.setName(preAuthenticated.getCareUnitName());
				}
				
				cu = this.cuRepo.save(CareUnitEntity.newEntity(careUnit, preAuthenticated.getCareUnitName()));
				getLog().debug("Created care unit {}, {}", cu.getHsaId(), cu.getName());
			}
			
			final CareGiverEntity cg = this.cgRepo.save(CareGiverEntity.newEntity("system-generated-name", preAuthenticated.getUsername(), cu));
			getLog().debug("Created care giver {}", cg.getName());
			
			return CareGiverImpl.newFromEntity(cg);
		} else {
			
			getLog().info("The user is a patient...");
			final PatientEntity p = this.pRepo.save(PatientEntity.newEntity("system-generated-name", preAuthenticated.getUsername()));
			getLog().debug("User {} has now been saved.", p.getCivicRegistrationNumber());
			
			return PatientImpl.newFromEntity(p);
		}
	}

	@Override
	public UserDetails verifyPrincipal(Object principal) {
		if (principal instanceof CareGiverImpl) {
			getLog().debug("Already authenticated as a care giver. Return principal object.");
			return (CareGiver) principal;
		} else if (principal instanceof PatientImpl) {
			getLog().debug("Already authenticated as a patient. Return principal object.");
			return (Patient) principal;
		} else {
			return null;
		}
	}

	@Override
	public UserDetails lookupPrincipal(AuthenticationResult auth) throws UsernameNotFoundException {
		if (auth.isCareGiver()) {
			getLog().debug("The authentication result indicates that the user is a care giver. Check for the user in care giver repository");
			final CareGiverEntity cg = this.cgRepo.findByHsaId(auth.getUsername());
			if (cg == null) {
				getLog().debug("Could not find any care giver matching {}", auth.getUsername());
			} else {
				return CareGiverImpl.newFromEntity(cg);
			}
		} else {
			getLog().debug("The authentication result indicates that the user is a patient. Check for the user in patient repository");
			final PatientEntity patient = this.pRepo.findByCivicRegistrationNumber(EntityUtil.formatCrn(auth.getUsername()));
			if (patient == null) {
				getLog().debug("Could not find any patients matching {}. Trying with care givers...", auth.getUsername());
			} else {
				return PatientImpl.newFromEntity(patient);
			}
		}
		
		throw new UsernameNotFoundException("User " + auth.getUsername() + " could not be found in the system.");
	}

}
