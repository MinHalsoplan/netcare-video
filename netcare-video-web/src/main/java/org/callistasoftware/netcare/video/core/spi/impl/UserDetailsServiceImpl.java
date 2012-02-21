package org.callistasoftware.netcare.video.core.spi.impl;

import org.callistasoftware.netcare.video.core.api.impl.CareGiverImpl;
import org.callistasoftware.netcare.video.core.api.impl.PatientImpl;
import org.callistasoftware.netcare.video.core.spi.UserDetailsService;
import org.callistasoftware.netcare.video.model.entity.CareGiverEntity;
import org.callistasoftware.netcare.video.model.entity.PatientEntity;
import org.callistasoftware.netcare.video.model.repository.CareGiverRepository;
import org.callistasoftware.netcare.video.model.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	private CareGiverRepository cgRepo;
	
	@Autowired
	private PatientRepository pRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		log.info("Find user {}", username);
		
		final CareGiverEntity u = this.cgRepo.findByHsaId(username);
		if (u != null) {
			return CareGiverImpl.newFromEntity(u);
		} else {
			final PatientEntity p = this.pRepo.findByCivicRegistrationNumber(username);
			if (p != null) {
				return PatientImpl.newFromEntity(p);
			}
		}
		
		throw new UsernameNotFoundException("User " + username + " not found in repository");
	}

}
