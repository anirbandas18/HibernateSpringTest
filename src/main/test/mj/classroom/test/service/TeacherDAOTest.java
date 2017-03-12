package mj.classroom.test.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import mj.classroom.entity.SubjectEntity;
import mj.classroom.entity.TeacherEntity;
import mj.classroom.entity.TeachingEntity;
import mj.classroom.exception.ServiceException;
import mj.classroom.service.SubjectService;
import mj.classroom.service.TeacherService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/application-context.xml" })
public class TeacherDAOTest {

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private SubjectService subjectService;

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
	}


	//Creating data template
	private static TeacherEntity createTeacherEntity(int index, SubjectEntity subject) {
		TeachingEntity teaches = new TeachingEntity();
		teaches.setName("Name" + String.valueOf(index));
		teaches.setSubject(subject);

		TeacherEntity teacher = new TeacherEntity();
		teacher.setEmail("email_" + String.valueOf(index) + "@example.com");
		teacher.setFees(1000d * Double.parseDouble(String.valueOf(index)));
		teacher.setPhone(987654321l + Long.parseLong(String.valueOf(index)));
		teacher.setTeaches(teaches);

		return teacher;
	}

	// Creating dummy teacher data set
	private List<TeacherEntity> getPersistableTeacherEntities() throws ServiceException {
		List<SubjectEntity> subjects = SubjectDAOTest.getPersistableSubjectEntities();
		List<TeacherEntity> teachers = new ArrayList<>();
		int index = 1;
		for (SubjectEntity subject : subjects) {
			subject.setId(subjectService.addSubject(subject));
			TeacherEntity major = createTeacherEntity(index, subject);
			if (subject.getParent() != null) {
				for (SubjectEntity child : subject.getChildren()) {
					TeacherEntity minor = createTeacherEntity(index, child);
					teachers.add(minor);
				}
			} else {
				teachers.add(major);
			}
		}
		return teachers;
	}

	@Test
	@Transactional("transactionManager")
	@Rollback(true)
	public void testAddFeature() {
		try {
			/*
			 * Persisting all created entities and storing the generated IDs
			 * back into the respective entities
			 */
			List<TeacherEntity> teachers = getPersistableTeacherEntities();

			for (TeacherEntity teacher : teachers) {
				Assert.assertTrue(teacherService.addTeacher(teacher));
			}

		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	@Transactional("transactionManager")
	@Rollback(true)
	public void testGetFeature() {
		try {
			/*
			 * Persisting all created entities and storing the generated IDs
			 * back into the respective entities
			 */
			List<TeacherEntity> teachers = getPersistableTeacherEntities();
			for (TeacherEntity teacher : teachers) {
				Assert.assertTrue(teacherService.addTeacher(teacher));
			}

			/**
			 * Positive test cases
			 */
			
			TeacherEntity teacher = teachers.get(0);
			Assert.assertSame(teacher, teacherService.retrieveTeacher(teacher.getTeaches()));
			
			Assert.assertSame(teachers.toArray(), teacherService.retrieveTeachers().toArray());

			List<TeacherEntity> unexpectedTeachersForSubject = teachers.stream()
					.filter(t -> t.getTeaches().getSubject() == teacher.getTeaches().getSubject())
					.collect(Collectors.toList());
			SubjectEntity subjectTaught = teacher.getTeaches().getSubject();
			Set<TeacherEntity> originalTeachersForSubject = teacherService.retrieveTeachers(subjectTaught.getId());
			Assert.assertSame(unexpectedTeachersForSubject.toArray(), originalTeachersForSubject.toArray());
			
			List<TeacherEntity> unexpectedTeachersWithName = teachers.stream()
					.filter(t -> t.getTeaches().getName().toLowerCase().matches(teacher.getTeaches().getName().toLowerCase()))
					.collect(Collectors.toList());
			String teacherName = teacher.getTeaches().getName();
			Set<TeacherEntity> originalTeachersWithName = teacherService.retrieveTeachers(teacherName);
			Assert.assertSame(unexpectedTeachersWithName.toArray(), originalTeachersWithName.toArray());
			
			Set<TeachingEntity> unexpectedTeachings = teachers.stream().map(TeacherEntity::getTeaches).collect(Collectors.toSet());
			Set<SubjectEntity> unexpectedSubjects = unexpectedTeachings.stream().map(TeachingEntity::getSubject).collect(Collectors.toSet());
			Set<Integer> unexpectedSubjectIDs = unexpectedSubjects.stream().map(SubjectEntity::getId).collect(Collectors.toSet());
			Assert.assertSame(unexpectedSubjectIDs, teacherService.retrieveSubjects());

			/**
			 * Negative test cases
			 */
			
			Assert.assertNotEquals(teacher, teacherService.retrieveTeacher(teacher.getTeaches()));
			
			originalTeachersForSubject = teacherService.retrieveTeachers(-1);
			Assert.assertSame(new Integer[0],  originalTeachersForSubject.toArray());
			
			originalTeachersWithName = teacherService.retrieveTeachers("Anirban");
			Assert.assertSame(new TeacherEntity[0], originalTeachersWithName.toArray());
			

		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	@Transactional("transactionManager")
	@Rollback(true)
	public void testDeleteFeature() {
		try {
			
			/* Persisting all created entities and storing the generated IDs
			 * back into the respective entities
			 */
			List<TeacherEntity> teachers = getPersistableTeacherEntities();
			for (TeacherEntity teacher : teachers) {
				Assert.assertTrue(teacherService.addTeacher(teacher));
			}
			
			/**
			 * Positive test cases
			 */
			
			TeacherEntity teacher = teachers.get(0);
			Assert.assertTrue(teacherService.deleteTeacher(teacher));

			/**
			 * Negative test cases
			 */
			TeachingEntity teaching = teacher.getTeaches();
			teaching.setName("Anirban");
			teacher.setTeaches(teaching);
			Assert.assertFalse(teacherService.deleteTeacher(teacher));
			
			teaching.setName("Anirban");
			teaching.setSubject(new SubjectEntity(-1));
			teacher.setTeaches(teaching);
			Assert.assertFalse(teacherService.deleteTeacher(teacher));
			
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	@Transactional("transactionManager")
	@Rollback(true)
	public void testUpdateFeature() {
		try {
			/* Persisting all created entities and storing the generated IDs
			 * back into the respective entities
			 */
			List<TeacherEntity> teachers = getPersistableTeacherEntities();
			for (TeacherEntity teacher : teachers) {
				Assert.assertTrue(teacherService.addTeacher(teacher));
			}
			
			/**
			 * Positive test cases
			 */
			
			TeacherEntity teacher = teachers.get(0);
			TeachingEntity teaching = teacher.getTeaches();
			teacher = teacherService.retrieveTeacher(teaching);
			teaching.setName("Anirban");
			teacher.setTeaches(teaching);
			teacher.setFees(10d);
			teacher.setPhone(9804441870l);
			Assert.assertTrue(teacherService.updateTeacher(teacher));

			/**
			 * Negative test cases
			 */
			teacher = teacherService.retrieveTeacher(teaching);
			teaching = teacher.getTeaches();
			teaching.setName("Anirban");
			teacher.setTeaches(teaching);
			Assert.assertFalse(teacherService.updateTeacher(teacher));
			
			
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
	}
}
