package mj.classroom.service;

import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mj.classroom.dao.TeacherDAO;
import mj.classroom.entity.TeacherEntity;
import mj.classroom.entity.TeachingEntity;
import mj.classroom.exception.DAOException;
import mj.classroom.exception.ServiceException;

@Service("teacherService")
public class TeacherServiecImpl implements TeacherService {

	
	@Autowired
	private TeacherDAO teacherDAO;

	public void setTeacherDAO(TeacherDAO teacherDAO) {
		this.teacherDAO = teacherDAO;
	}

	@Override
	public boolean addTeacher(TeacherEntity teacher) throws ServiceException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			flag = teacherDAO.create(teacher);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		return flag;
	}

	@Override
	public boolean deleteTeacher(TeacherEntity teacher) throws ServiceException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			flag = teacherDAO.delete(teacher);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		return flag;
	}

	@Override
	public boolean updateTeacher(TeacherEntity teacher) throws ServiceException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			flag = teacherDAO.update(teacher);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		return flag;
	}

	@Override
	public Set<TeacherEntity> retrieveTeachers(int subjectId) throws ServiceException {
		// TODO Auto-generated method stub
		Set<TeacherEntity> teachers = new TreeSet<>();
		try {
			teachers = teacherDAO.retrieve(subjectId);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		return teachers;
	}

	@Override
	public TeacherEntity retrieveTeacher(TeachingEntity teaching) throws ServiceException {
		// TODO Auto-generated method stub
		TeacherEntity teacherEntity = new TeacherEntity();
		try {
			teacherEntity = teacherDAO.retrieve(teaching);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		return teacherEntity;
	}

	@Override
	public Set<TeacherEntity> retrieveTeachers(String teacherName) throws ServiceException {
		// TODO Auto-generated method stub
		Set<TeacherEntity> teachers = new TreeSet<>();
		try {
			teachers = teacherDAO.retrieve(teacherName);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		return teachers;
	}

	@Override
	public Set<TeacherEntity> retrieveTeachers() throws ServiceException {
		// TODO Auto-generated method stub
		Set<TeacherEntity> teachers = new TreeSet<>();
		try {
			teachers = teacherDAO.retrieveTeachers();
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		return teachers;
	}

	@Override
	public Set<Integer> retrieveSubjects() throws ServiceException {
		// TODO Auto-generated method stub
		Set<Integer> subjects = new TreeSet<>();
		try {
			subjects = teacherDAO.retrieveSubjects();
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		return subjects;
	}

}
