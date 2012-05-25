package org.callistasoftware.netcare.video.model.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import org.callistasoftware.netcare.video.model.entity.CareGiverEntity;
import org.callistasoftware.netcare.video.model.entity.CareUnitEntity;
import org.callistasoftware.netcare.video.model.entity.PatientEntity;
import org.callistasoftware.netcare.video.model.entity.VideoMeetingEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:/netcare-video-config.xml")
@ActiveProfiles(profiles={"test", "db-embedded"})
public class VideoRepositoryTest {

	@Autowired private CareUnitRepository cuRepo;
	@Autowired private CareGiverRepository cgRepo;
	@Autowired private PatientRepository pRepo;
	@Autowired private VideoMeetingRepository repo;
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetVideoBookingsForUser() throws Exception {
		
		final CareUnitEntity cu = this.cuRepo.save(CareUnitEntity.newEntity("hsa-unit", "careunit"));
		final CareGiverEntity cg = this.cgRepo.save(CareGiverEntity.newEntity("Dr. Hook", "hsa-user", cu));
		final PatientEntity p = this.pRepo.save(PatientEntity.newEntity("Marcus", "191212121212"));
		
		final VideoMeetingEntity booking = VideoMeetingEntity.newEntity("Videomöte", new Date(System.currentTimeMillis() - 1800), cg);
		booking.addParticipant(cg, true);
		booking.addParticipant(p, false);
		this.repo.save(booking);
		
		
		final List<VideoMeetingEntity> bookings = this.repo.findByUser(cg.getId());
		assertNotNull(bookings);
		assertEquals(1, bookings.size());
	}
}
