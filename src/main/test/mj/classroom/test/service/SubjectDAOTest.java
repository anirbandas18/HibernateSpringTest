package mj.classroom.test.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import mj.classroom.entity.SubjectEntity;
import mj.classroom.exception.ServiceException;
import mj.classroom.service.SubjectService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/application-context.xml" })
public class SubjectDAOTest {

	@Autowired
	private SubjectService subjectService;

	//Creating dummy data set
	public static List<SubjectEntity> getPersistableSubjectEntities() {
		SubjectEntity science = new SubjectEntity();
		science.setName("Science");

		List<SubjectEntity> scienceChildren = new ArrayList<>();

		SubjectEntity physics = new SubjectEntity();
		physics.setCredits(20);
		physics.setDuration(40);
		physics.setName("Physics");
		physics.setParent(science);
		scienceChildren.add(physics);

		SubjectEntity chemistry = new SubjectEntity();
		chemistry.setCredits(20);
		chemistry.setDuration(40);
		chemistry.setName("Chemistry");
		chemistry.setParent(science);
		scienceChildren.add(chemistry);
		
		SubjectEntity biology = new SubjectEntity();
		biology.setCredits(20);
		biology.setDuration(40);
		biology.setName("Biology");
		biology.setParent(science);
		scienceChildren.add(biology);

		/**
		 * Calculating total credits and duration of parent subject based on the
		 * same attributes for each child subject under that parent
		 */
		int credits = 0;
		int duration = 0;

		for (SubjectEntity child : scienceChildren) {
			credits += child.getCredits();
			duration += child.getDuration();
		}

		science.setCredits(credits);
		science.setDuration(duration);
		science.setChildren(scienceChildren);

		SubjectEntity socialScience = new SubjectEntity();
		socialScience.setName("Social Science");

		List<SubjectEntity> socialScienceChildren = new ArrayList<>();

		SubjectEntity history = new SubjectEntity();
		history.setCredits(15);
		history.setDuration(20);
		history.setName("history");
		history.setParent(socialScience);
		socialScienceChildren.add(history);

		SubjectEntity civics = new SubjectEntity();
		civics.setCredits(10);
		civics.setDuration(30);
		civics.setName("civics");
		civics.setParent(socialScience);
		socialScienceChildren.add(civics);

		/**
		 * Calculating total credits and duration of parent subject based on the
		 * same attributes for each child subject under that parent
		 */
		credits = 0;
		duration = 0;
		for (SubjectEntity child : socialScienceChildren) {
			credits += child.getCredits();
			duration += child.getDuration();
		}

		socialScience.setCredits(credits);
		socialScience.setDuration(duration);

		List<SubjectEntity> subjects = new ArrayList<>();
		subjects.add(science);
		subjects.add(history);
		subjects.add(civics);

		return subjects;
	}

	@Test
	@Transactional("transactionManager")
	@Rollback(true)
	public void testAddFeature() {
		try {
			List<SubjectEntity> subjects = getPersistableSubjectEntities();
			/* Filtering out entity named 'history' from list of created entities */
			SubjectEntity history = subjects.stream().filter(subject -> subject.getName().equals("history"))
					.findFirst().get();
			/* Filtering out entity named 'civics' from list of created entities */
			SubjectEntity civics = subjects.stream().filter(subject -> subject.getName().equals("civics"))
					.findFirst().get();
			/* Filtering out entity named 'Science' from list of created entities */
			SubjectEntity science = subjects.stream().filter(subject -> subject.getName().equals("Science"))
					.findFirst().get();
			
			Integer scienceId = subjectService.addSubject(science);
			Assert.assertSame(scienceId, subjectService.getSubject(scienceId).getId());
			
			Integer historyId = subjectService.addSubject(history);
			Assert.assertSame(historyId, subjectService.getSubject(historyId).getId());
			Integer civicsId = subjectService.addSubject(civics);
			Assert.assertSame(civicsId, subjectService.getSubject(civicsId).getId());
			
			/* Filtering out entity named 'Chemistry' from list of created entities */
			SubjectEntity chemistry = science.getChildren().stream().filter(subject -> subject.getName().equals("Chemistry"))
					.findFirst().get();
			Assert.assertSame(chemistry.getName(), subjectService.getSubject(chemistry.getName()).getName());
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	@Transactional("transactionManager")
	@Rollback(true)
	public void testGetFeature() {
		try {
			/* Persisting all created entities and storing the generated IDs back into the respective entities */
			List<SubjectEntity> subjects = getPersistableSubjectEntities();
			for(SubjectEntity subject : subjects) {
				subject.setId(subjectService.addSubject(subject));
			}
			
			/* Filtering out children entities from data set */ 
			List<SubjectEntity> childrenExpected = subjects.stream().filter(subject -> subject.getParent() != null).collect(Collectors.toList());
			
			/* Filtering out parent entities from data set */
			List<SubjectEntity> parentExpected = subjects.stream().filter(subject -> subject.getParent() == null).collect(Collectors.toList());
			
			/* Filtering out parent entities from child entity */
			parentExpected.addAll(childrenExpected.stream().map(SubjectEntity::getParent).collect(Collectors.toSet()));
			
			/**
			 * Positive test cases
			 */
			
			List<SubjectEntity> children = subjectService.getChildSubjects();
			Assert.assertSame(childrenExpected.size(), children.size());
			
			List<SubjectEntity> parents = subjectService.getParentSubjects();
			Assert.assertSame(parentExpected.size(), parents.size());
			
			// Filtering out entity named 'civics' from list of created entities
			SubjectEntity civics = subjects.stream().filter(subject -> subject.getName().equals("civics"))
					.findFirst().get();
			Assert.assertSame(civics.getId(), subjectService.getSubject(civics.getId()).getId());
			
			// Filtering out entity named 'history' from list of created entities
			SubjectEntity history = subjects.stream().filter(subject -> subject.getName().equals("history"))
					.findFirst().get();
			Assert.assertSame(history.getId(), subjectService.getSubject(history.getId()).getId());
			
			// Filtering out entity named 'Science' from list of created entities
			SubjectEntity science = subjects.stream().filter(subject -> subject.getName().equals("Science"))
					.findFirst().get();
			
			// Testing feature of retrieving all subjects under a given subject by ID
			List<SubjectEntity> scienceChildren = subjectService.getSubjectsUnder(science.getId());
			assertThat(science.getChildren(), is(scienceChildren));
			
			// Testing feature of retrieving all subjects under a given subject by name
			List<SubjectEntity> socialScienceChildren = subjectService.getSubjectsUnder("Scoial Science");
			assertThat(childrenExpected, is(socialScienceChildren));
			
			/**
			 * Negative test cases
			 */
			Assert.assertNull(subjectService.getSubject(0));
			Assert.assertNull(subjectService.getSubject("English"));
			Assert.assertSame(0, subjectService.getSubjectsUnder("Mathematics").size());
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	@Transactional("transactionManager")
	@Rollback(true)
	public void testDeleteFeature() {
		try {
			/* Persisting all created entities and storing the generated IDs back into the respective entities */
			List<SubjectEntity> subjects = getPersistableSubjectEntities();
			for(SubjectEntity subject : subjects) {
				subject.setId(subjectService.addSubject(subject));
			}
			
			// Filtering out entity named 'history' from list of created entities
			SubjectEntity history = subjects.stream().filter(subject -> subject.getName().equals("history"))
				.findFirst().get();
						
			// Filtering out entity named 'civics' from list of created entities
			SubjectEntity civics = subjects.stream().filter(subject -> subject.getName().equals("civics"))
				.findFirst().get();
			
			// Filtering out entity named 'civics' from list of created entities
			SubjectEntity science = subjects.stream().filter(subject -> subject.getName().equals("Science"))
				.findFirst().get();
			
			/**
			 * Positive test cases
			 */
			Assert.assertTrue(subjectService.deleteSubject(history.getId()));
			Assert.assertTrue(subjectService.deleteSubject(civics.getName()));
			Assert.assertTrue(subjectService.deleteSubject(science));
			
			/**
			 * Negative test cases
			 */
			Assert.assertFalse(subjectService.deleteSubject(history.getId()));
			Assert.assertFalse(subjectService.deleteSubject(civics.getName()));
			Assert.assertFalse(subjectService.deleteSubject(science));
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	@Transactional("transactionManager")
	@Rollback(true)
	public void testUpdateFeature() {
		try {
			/* Persisting all created entities and storing the generated IDs back into the respective entities */
			List<SubjectEntity> subjects = getPersistableSubjectEntities();
			for(SubjectEntity subject : subjects) {
				subject.setId(subjectService.addSubject(subject));
			}
			
			// Filtering out entity named 'history' from list of created entities
			SubjectEntity history = subjects.stream().filter(subject -> subject.getName().equals("history"))
				.findFirst().get();
			history.setName("History");
			
			/**
			 * Positive test case
			 */
			Assert.assertTrue(subjectService.updateSubject(history));
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
	}
}
