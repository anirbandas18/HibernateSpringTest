package mj.classroom.dao;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mj.classroom.entity.TeacherEntity;
import mj.classroom.entity.TeachingEntity;
import mj.classroom.exception.DAOException;

@Repository("teacherDAO")
public class TeacherDAOImpl implements TeacherDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public boolean create(TeacherEntity teacher) throws DAOException {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			TeachingEntity teachingBefore = teacher.getTeaches();
			session.save(teacher);
			teacher = session.get(TeacherEntity.class, teacher.getTeaches());
			flag = teachingBefore.equals(teacher.getTeaches());
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			// log Exception
			// e.printStackTrace();
			throw new DAOException(e.getMessage());
		} finally {
			session.close();
		}
		return flag;
	}

	@Override
	public boolean delete(TeacherEntity teacher) throws DAOException {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			session.delete(teacher);
			tx.commit();
			flag = true;
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new DAOException(e.getMessage());
		} finally {
			session.close();
		}
		return flag;
	}

	@Override
	public boolean update(TeacherEntity teacher) throws DAOException {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			session.update(teacher);
			tx.commit();
			flag = true;
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new DAOException(e.getMessage());
		} finally {
			session.close();
		}
		return flag;
	}

	@Override
	public TeacherEntity retrieve(TeachingEntity teaching) throws DAOException {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		TeacherEntity teacher = null;
		try {
			tx = session.beginTransaction();
			teacher = session.get(TeacherEntity.class, teaching);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new DAOException(e.getMessage());
		} finally {
			session.close();
		}
		return teacher;
	}

	@Override
	public Set<TeacherEntity> retrieve(String teacherName) throws DAOException {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Set<TeacherEntity> teachers = new TreeSet<>();
		try {
			tx = session.beginTransaction();
			Query q = session.createQuery("select t from TeacherEntity t where lower(t.teaches.name) like lower(:teacherName)");
			q = q.setString("teacherName", teacherName);
			List<TeacherEntity> teacherResultSet = q.list();
			teachers.addAll(teacherResultSet);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new DAOException(e.getMessage());
		} finally {
			session.close();
		}
		return teachers;
	}

	@Override
	public Set<TeacherEntity> retrieve(int subjectId) throws DAOException {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Set<TeacherEntity> teachers = new TreeSet<>();
		try {
			tx = session.beginTransaction();
			//select distinct s from SubjectEntity s inner join s.children where s.parent = :id
			Query q = session.createQuery("select t from TeacherEntity t, SubjectEntity s, s.children where s.id = :subjectId and s.children.id = s.id and s.id = t.teaches.subject and s.children.id = t.teaches.subject");
			q = q.setInteger("subjectId", subjectId);
			List<TeacherEntity> teacherResultSet = q.list();
			teachers.addAll(teacherResultSet);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new DAOException(e.getMessage());
		} finally {
			session.close();
		}
		return teachers;
	}

	@Override
	public Set<TeacherEntity> retrieveTeachers() throws DAOException {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Set<TeacherEntity> teachers = new TreeSet<>();
		try {
			tx = session.beginTransaction();
			//select distinct s from SubjectEntity s inner join s.children where s.parent = :id
			Query q = session.createQuery("select distinct t from TeacherEntity");
			List<TeacherEntity> teacherResultSet = q.list();
			teachers.addAll(teacherResultSet);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new DAOException(e.getMessage());
		} finally {
			session.close();
		}
		return teachers;
	}

	@Override
	public Set<Integer> retrieveSubjects() throws DAOException {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Set<Integer> subjects = new TreeSet<>();
		try {
			tx = session.beginTransaction();
			//select distinct s from SubjectEntity s inner join s.children where s.parent = :id
			Query q = session.createQuery("select distinct s.id from TeacherEntity t, SubjectEntity s, s.children where s.children.id = s.id and s.id = t.teaches.subject");
			List<Integer> subjecteResultSet = q.list();
			subjects.addAll(subjecteResultSet);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new DAOException(e.getMessage());
		} finally {
			session.close();
		}
		return subjects;
	}

	

}
