package mj.classroom.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mj.classroom.dao.SubjectDAO;
import mj.classroom.entity.SubjectEntity;
import mj.classroom.exception.DAOException;
import mj.classroom.exception.ServiceException;

@Service("subjectService")
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectDAO subjectDAO;

	public void setSubjectDAO(SubjectDAO subjectDAO) {
		this.subjectDAO = subjectDAO;
	}


	@Override
	public int addSubject(SubjectEntity subject) throws ServiceException {
		// TODO Auto-generated method stub
		Integer id = null;
		try {
			id = subjectDAO.create(subject);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		return id;
	}


	@Override
	public SubjectEntity getSubject(String subjectName) throws ServiceException {
		// TODO Auto-generated method stub
		SubjectEntity subjectEntity = new SubjectEntity();
		try {
			subjectEntity = subjectDAO.retrieve(subjectName);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		return subjectEntity;
	}


	@Override
	public SubjectEntity getSubject(int subjectId) throws ServiceException {
		// TODO Auto-generated method stub
		SubjectEntity subjectEntity = new SubjectEntity();
		try {
			subjectEntity = subjectDAO.retrieve(subjectId);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		return subjectEntity;
	}


	@Override
	public boolean updateSubject(SubjectEntity subject) throws ServiceException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			flag = subjectDAO.update(subject);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		return flag;
	}


	@Override
	public boolean deleteSubject(SubjectEntity subject) throws ServiceException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			flag = subjectDAO.delete(subject);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		return flag;
	}


	@Override
	public boolean deleteSubject(String subjectName) throws ServiceException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			SubjectEntity subject = subjectDAO.retrieve(subjectName);
			flag = subjectDAO.delete(subject);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		return flag;
	}


	@Override
	public boolean deleteSubject(int subjectId) throws ServiceException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			SubjectEntity subject = subjectDAO.retrieve(subjectId);
			flag = subjectDAO.delete(subject);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		return flag;
	}


	@Override
	public List<SubjectEntity> getParentSubjects() throws ServiceException {
		// TODO Auto-generated method stub
		List<SubjectEntity> parents = new ArrayList<>();
		try {
			parents = subjectDAO.retrieveParents();
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		return parents;
	}


	@Override
	public List<SubjectEntity> getSubjectsUnder(String subjectName) throws ServiceException {
		// TODO Auto-generated method stub
		List<SubjectEntity> children = new ArrayList<>();
		try {
			SubjectEntity subjectEntity = subjectDAO.retrieve(subjectName);
			children = subjectEntity.getChildren();
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		return children;
	}


	@Override
	public List<SubjectEntity> getSubjectsUnder(int subjectId) throws ServiceException {
		// TODO Auto-generated method stub
		List<SubjectEntity> children = new ArrayList<>();
		try {
			SubjectEntity subjectEntity = subjectDAO.retrieve(subjectId);
			children = subjectEntity.getChildren();
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		return children;
	}


	@Override
	public List<SubjectEntity> getChildSubjects() throws ServiceException {
		// TODO Auto-generated method stub
		List<SubjectEntity> children = new ArrayList<>();
		try {
			children = subjectDAO.retrieveParents();
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		return children;
	}

}
