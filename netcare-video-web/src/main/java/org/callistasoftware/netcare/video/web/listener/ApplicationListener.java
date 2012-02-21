package org.callistasoftware.netcare.video.web.listener;

import java.util.Date;

import javax.servlet.ServletContextEvent;

import org.callistasoftware.netcare.video.model.entity.CareGiverEntity;
import org.callistasoftware.netcare.video.model.entity.CareUnitEntity;
import org.callistasoftware.netcare.video.model.entity.PatientEntity;
import org.callistasoftware.netcare.video.model.entity.VideoBookingEntity;
import org.callistasoftware.netcare.video.model.repository.CareGiverRepository;
import org.callistasoftware.netcare.video.model.repository.CareUnitRepository;
import org.callistasoftware.netcare.video.model.repository.PatientRepository;
import org.callistasoftware.netcare.video.model.repository.VideoBookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ApplicationListener extends ContextLoaderListener {

	private static final Logger log = LoggerFactory.getLogger(ApplicationListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		
		final WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		
		final CareGiverRepository cgRepo = wc.getBean(CareGiverRepository.class);
		final PatientRepository patientRepo = wc.getBean(PatientRepository.class);
		final CareUnitRepository cuRepo = wc.getBean(CareUnitRepository.class);
		final VideoBookingRepository repo = wc.getBean(VideoBookingRepository.class);
		
		final CareUnitEntity cu = cuRepo.save(CareUnitEntity.newEntity("care-unit-hsa")); 
		final CareGiverEntity cg = cgRepo.save(CareGiverEntity.newEntity("Dr. Test Testgren", "hsa-id-1234", cu));
		
		final PatientEntity p = patientRepo.save(PatientEntity.newEntity("Peter Larsson", "191212121212"));
		
		final VideoBookingEntity booking = VideoBookingEntity.newEntity(new Date(System.currentTimeMillis()));
		booking.addParticipant(cg, true);
		booking.addParticipant(p, false);
		
		repo.save(booking);
		
		log.info("==== NETCARE VIDEO STARTED ====");
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
		log.info("==== NETCARE VIDEO SHUTDOWN ====");
	}
}
