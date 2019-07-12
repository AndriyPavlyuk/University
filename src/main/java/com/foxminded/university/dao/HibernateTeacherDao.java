package com.foxminded.university.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.foxminded.university.domain.Teacher;
import com.foxminded.university.util.HibernateUtil;

public class HibernateTeacherDao implements TeacherDao {
	public HibernateTeacherDao() {
		
	}

	public List<Teacher> findAll() {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			List<Teacher> teacherList = session.createNamedQuery(Teacher.FIND_ALL, Teacher.class).getResultList();
			session.getTransaction().commit();
			return teacherList;
		} catch (Exception ex) {
			if (session != null && session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			throw new RuntimeException(ex);
		}
	}

	@Override
	public Teacher findById(long id) throws DaoException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Teacher teacher = session.get(Teacher.class, id);
		session.close();
		return teacher;
	}

	@Override
	public void addTeacher(Teacher teacher) throws DaoException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.save(teacher);
		transaction.commit();
		session.close();
	}

	@Override
	public void updateTeacher(Teacher teacher) throws DaoException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.update(teacher);
		transaction.commit();
		session.close();
	}

	@Override
	public void removeTeacher(Teacher teacher) throws DaoException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.delete(teacher);
		transaction.commit();
		session.close();
	}
}
