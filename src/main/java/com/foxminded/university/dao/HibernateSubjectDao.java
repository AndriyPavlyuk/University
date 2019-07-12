package com.foxminded.university.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.foxminded.university.domain.Subject;
import com.foxminded.university.util.HibernateUtil;

public class HibernateSubjectDao implements SubjectDao {
	public HibernateSubjectDao() {
		
	}

	public List<Subject> findAll() {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			List<Subject> subjectList = session.createNamedQuery(Subject.FIND_ALL, Subject.class).getResultList();
			session.getTransaction().commit();
			return subjectList;
		} catch (Exception ex) {
			if (session != null && session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			throw new RuntimeException(ex);
		}
	}

	@Override
	public Subject findById(long id) throws DaoException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Subject subject = session.get(Subject.class, id);
		session.close();
		return subject;
	}

	@Override
	public void addSubject(Subject subject) throws DaoException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.save(subject);
		transaction.commit();
		session.close();
	}

	@Override
	public void updateSubject(Subject subject) throws DaoException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.update(subject);
		transaction.commit();
		session.close();
	}

	@Override
	public void removeSubject(Subject subject) throws DaoException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.delete(subject);
		transaction.commit();
		session.close();
	}
}
