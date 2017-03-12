package mj.classroom.dao;

import java.util.Set;

import mj.classroom.entity.TeacherEntity;
import mj.classroom.entity.TeachingEntity;
import mj.classroom.exception.DAOException;

public interface TeacherDAO {

	public boolean create(TeacherEntity teacher) throws DAOException;
	
	public boolean delete(TeacherEntity teacher) throws DAOException;
	
	public boolean update(TeacherEntity teacher) throws DAOException;
	
	public TeacherEntity retrieve(TeachingEntity teaching) throws DAOException;
	
	public Set<TeacherEntity> retrieve(String teacherName) throws DAOException;
	
	public Set<TeacherEntity> retrieve(int subjectId) throws DAOException;
	
	public Set<TeacherEntity> retrieveTeachers() throws DAOException;
	
	public Set<Integer> retrieveSubjects() throws DAOException;
	
	
}
