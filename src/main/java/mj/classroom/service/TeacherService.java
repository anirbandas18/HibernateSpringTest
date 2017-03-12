package mj.classroom.service;

import java.util.Set;

import mj.classroom.entity.TeacherEntity;
import mj.classroom.entity.TeachingEntity;
import mj.classroom.exception.ServiceException;

public interface TeacherService {

	public boolean addTeacher(TeacherEntity teacher) throws ServiceException;
	
	public boolean deleteTeacher(TeacherEntity teacher) throws ServiceException;
	
	public boolean updateTeacher(TeacherEntity teacher) throws ServiceException;
	
	//Retrieve teachers based on subject ID
	public Set<TeacherEntity> retrieveTeachers(int subjectId) throws ServiceException;
	
	//Retrieve teacher based on teacher teaching a subject
	public TeacherEntity retrieveTeacher(TeachingEntity teaching) throws ServiceException;
	
	//Retrieve teachers with similar names
	public Set<TeacherEntity> retrieveTeachers(String teacherName) throws ServiceException;
	
	//Retrieve all teachers
	public Set<TeacherEntity> retrieveTeachers() throws ServiceException;
	
	//Retrieve all subjects taught
	public Set<Integer> retrieveSubjects() throws ServiceException;
	
}
