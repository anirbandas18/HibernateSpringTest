package mj.classroom.service;

import java.util.List;

import mj.classroom.entity.SubjectEntity;
import mj.classroom.exception.ServiceException;

public interface SubjectService {

	public int addSubject(SubjectEntity subject) throws ServiceException;
	
	//Get subject by subject name
	public SubjectEntity getSubject(String subjectName) throws ServiceException;
	
	//Get subject by subject ID
	public SubjectEntity getSubject(int subjectId) throws ServiceException;
	
	public boolean updateSubject(SubjectEntity subject) throws ServiceException;
	
	//Delete subject by entity
	public boolean deleteSubject(SubjectEntity subject) throws ServiceException;
	
	//Delete subject by subject name
	public boolean deleteSubject(String subjectName) throws ServiceException;
	
	//Delete subject by subject ID
	public boolean deleteSubject(int  subjectId) throws ServiceException;
	
	//Retrieve all parent subjects
	public List<SubjectEntity> getParentSubjects() throws ServiceException;
	
	//Retrieve all child subjects
	public List<SubjectEntity> getChildSubjects() throws ServiceException;
	
	//Retrieve all subjects under subject name
	public List<SubjectEntity> getSubjectsUnder(String subjectName) throws ServiceException;
	
	//Retrieve all subjects under subject ID
	public List<SubjectEntity> getSubjectsUnder(int subjectId) throws ServiceException;
	
}
