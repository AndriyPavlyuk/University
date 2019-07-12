package com.foxminded.university.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.foxminded.university.domain.Group;
import com.foxminded.university.util.HibernateUtil;
@Repository
public class HibernateGroupDao implements GroupDao{
	
	public HibernateGroupDao() {
		
	}
	public List<Group> findAll() {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			List<Group> groupList = session.createNamedQuery(Group.FIND_ALL, Group.class).getResultList();
			session.getTransaction().commit();
			return groupList;
		} catch (Exception ex) {
			if (session != null && session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			throw new RuntimeException(ex);
		}
	}

	@Override
	public Group findById(long id) throws DaoException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Group group = session.get(Group.class, id);
		session.close();
		return group;
	}

	@Override
	public void addGroup(Group group) throws DaoException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.save(group);
		transaction.commit();
		session.close();
	}

	@Override
	public void updateGroup(Group group) throws DaoException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.update(group);
		transaction.commit();
		session.close();
	}

	@Override
	public void removeGroup(Group group) throws DaoException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.delete(group);
		transaction.commit();
		session.close();
	}
}