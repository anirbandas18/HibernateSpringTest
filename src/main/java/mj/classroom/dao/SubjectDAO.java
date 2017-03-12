package mj.classroom.dao;

import java.util.List;

import mj.classroom.entity.SubjectEntity;
import mj.classroom.exception.DAOException;

public interface SubjectDAO {

	public int create(SubjectEntity subject) throws DAOException;
	
	public boolean delete(SubjectEntity subject) throws DAOException;
	
	public boolean update(SubjectEntity subject) throws DAOException;
	
	public SubjectEntity retrieve(int subjectId) throws DAOException ;
	
	public SubjectEntity retrieve(String name) throws DAOException ;
	
	public List<SubjectEntity> retrieveParents() throws DAOException;
	
	public List<SubjectEntity> retrieveChildren() throws DAOException;
	
}
