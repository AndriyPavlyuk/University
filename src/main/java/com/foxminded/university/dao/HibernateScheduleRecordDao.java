package com.foxminded.university.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.foxminded.university.domain.ScheduleRecord;
import com.foxminded.university.util.HibernateUtil;

public class HibernateScheduleRecordDao implements ScheduleRecordDao{
public HibernateScheduleRecordDao() {
		
	}
	public List<ScheduleRecord> findAll() {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			List<ScheduleRecord> groupList = session.createNamedQuery(ScheduleRecord.FIND_ALL, ScheduleRecord.class).getResultList();
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
	public ScheduleRecord findById(long id) throws DaoException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		ScheduleRecord scheduleRecord = session.get(ScheduleRecord.class, id);
		session.close();
		return scheduleRecord;
	}

	@Override
	public void addScheduleRecord(ScheduleRecord scheduleRecord) throws DaoException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.save(scheduleRecord);
		transaction.commit();
		session.close();
	}

	@Override
	public void updateScheduleRecord(ScheduleRecord scheduleRecord) throws DaoException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.update(scheduleRecord);
		transaction.commit();
		session.close();
	}

	@Override
	public void removeScheduleRecord(ScheduleRecord scheduleRecord) throws DaoException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.delete(scheduleRecord);
		transaction.commit();
		session.close();
	}
}
