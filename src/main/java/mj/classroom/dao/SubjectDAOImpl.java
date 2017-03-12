package mj.classroom.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mj.classroom.entity.SubjectEntity;
import mj.classroom.exception.DAOException;

@Repository("subjectDAO")
public class SubjectDAOImpl implements SubjectDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public boolean delete(SubjectEntity se) throws DAOException {
		// TODO Auto-generated method stub

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			session.delete(se);
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
	public boolean update(SubjectEntity se) throws DAOException {
		// TODO Auto-generated method stub

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		boolean flag = false;
		try {
			tx = session.beginTransaction();
			session.update(se);
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
	public SubjectEntity retrieve(int id) throws DAOException {
		// TODO Auto-generated method stub

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		SubjectEntity se = null;
		try {
			tx = session.beginTransaction();
			se = session.get(SubjectEntity.class, id);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new DAOException(e.getMessage());
		} finally {
			session.close();
		}
		return se;
	}

	@Override
	public SubjectEntity retrieve(String name) throws DAOException {
		// TODO Auto-generated method stub

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		SubjectEntity se = null;
		try {
			tx = session.beginTransaction();
			Query q = session.createQuery("from SubjectEntity where lower(name) like lower(:name)");
			q = q.setString("name", name);
			se = (SubjectEntity) q.uniqueResult();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new DAOException(e.getMessage());
		} finally {
			session.close();
		}
		return se;
	}

	@Override
	public int create(SubjectEntity se) throws DAOException {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Integer id = null;
		try {
			tx = session.beginTransaction();
			id = (Integer) session.save(se);
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
		return id;
	}

	@Override
	public List<SubjectEntity> retrieveParents() throws DAOException {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<SubjectEntity> subjects = null;
		try {
			tx = session.beginTransaction();
			Query q = session
					.createQuery("from SubjectEntity where parent is null");
			subjects = q.list();
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

	@Override
	public List<SubjectEntity> retrieveChildren() throws DAOException {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<SubjectEntity> subjects = null;
		try {
			tx = session.beginTransaction();
			Query q = session
					.createQuery("from SubjectEntity where parent is not null");
			subjects = q.list();
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
