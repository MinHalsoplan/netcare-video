package org.callistasoftware.netcare.video.core.spi.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.callistasoftware.netcare.service.api.ServiceResult;
import org.callistasoftware.netcare.service.api.impl.ServiceResultImpl;
import org.callistasoftware.netcare.service.messages.GenericSuccessMessage;
import org.callistasoftware.netcare.video.core.api.User;
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

	@Override
	public ServiceResult<User[]> findUsersBySearch(String search) {
		log.info("Searching for users using term: {}", search.toLowerCase());
		
		final List<PatientEntity> patients = this.pRepo.findBySearch("%" + search.toLowerCase() + "%");
		log.debug("Found {} patients that matched: {}", patients.size(), search);
		
		final List<CareGiverEntity> careGivers = this.cgRepo.findBySearch("%" + search.toLowerCase() + "%");
		log.debug("Found {} care givers that matched: {}", careGivers.size(), search);
		
		final List<User> users = new ArrayList<User>();
		for (final PatientEntity p : patients) {
			users.add(PatientImpl.newFromEntity(p));
		}
		
		for (final CareGiverEntity cg : careGivers) {
			users.add(CareGiverImpl.newFromEntity(cg));
		}
		
		Collections.sort(users, new Comparator<User>() {

			@Override
			public int compare(User o1, User o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		
		return ServiceResultImpl.createSuccessResult(users.toArray(new User[users.size()]), new GenericSuccessMessage());
	}

}
