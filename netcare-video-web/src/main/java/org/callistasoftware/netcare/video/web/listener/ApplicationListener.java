package org.callistasoftware.netcare.video.web.listener;

import java.util.Date;

import javax.servlet.ServletContextEvent;

import org.callistasoftware.netcare.video.model.entity.CareGiverEntity;
import org.callistasoftware.netcare.video.model.entity.CareUnitEntity;
import org.callistasoftware.netcare.video.model.entity.PatientEntity;
import org.callistasoftware.netcare.video.model.entity.VideoMeetingEntity;
import org.callistasoftware.netcare.video.model.repository.CareGiverRepository;
import org.callistasoftware.netcare.video.model.repository.CareUnitRepository;
import org.callistasoftware.netcare.video.model.repository.PatientRepository;
import org.callistasoftware.netcare.video.model.repository.VideoMeetingRepository;
import org.callistasoftware.netcare.video.web.util.WebUtil;
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
		
		if (WebUtil.isProfileActive(event.getServletContext(), "test")) {
			final WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
			
			final CareGiverRepository cgRepo = wc.getBean(CareGiverRepository.class);
			final PatientRepository patientRepo = wc.getBean(PatientRepository.class);
			final CareUnitRepository cuRepo = wc.getBean(CareUnitRepository.class);
			final VideoMeetingRepository repo = wc.getBean(VideoMeetingRepository.class);
			
			final CareUnitEntity cu = cuRepo.save(CareUnitEntity.newEntity("care-unit-hsa", "Callista vårdcentral")); 
			final CareGiverEntity cg = cgRepo.save(CareGiverEntity.newEntity("Marcus Andersson", "hsa-id-1234", cu));
			
			final CareGiverEntity cg2 = cgRepo.save(CareGiverEntity.newEntity("Anders Hansson", "anders", cu));
			cgRepo.save(CareGiverEntity.newEntity("Hans Krisp", "hans", cu));
			cgRepo.save(CareGiverEntity.newEntity("Christian Lökin", "christian", cu));
			cgRepo.save(CareGiverEntity.newEntity("Jasmina Blom", "jasmina", cu));
			
			final PatientEntity p = patientRepo.save(PatientEntity.newEntity("Peter Storch", "191212121212"));
			
			final VideoMeetingEntity booking = VideoMeetingEntity.newEntity("Videomöte", new Date(System.currentTimeMillis()), null, cg);
			booking.addParticipant(cg, true);
			booking.addParticipant(cg2, false);
			booking.addParticipant(p, false);
			//booking.addParticipant(cg3, false);
			//booking.addParticipant(cg4, false);
			//booking.addParticipant(cg5, false);
			
			repo.save(booking);
		}
		
		log.info("==== NETCARE VIDEO STARTED ====");
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
		log.info("==== NETCARE VIDEO SHUTDOWN ====");
	}
}
