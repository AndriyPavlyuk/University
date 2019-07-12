package com.foxminded.university.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.foxminded.university.domain.Room;
import com.foxminded.university.util.HibernateUtil;

public class HibernateRoomDao implements RoomDao {
	public HibernateRoomDao() {

	}

	public List<Room> findAll() {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			List<Room> roomList = session.createNamedQuery(Room.FIND_ALL, Room.class).getResultList();
			session.getTransaction().commit();
			return roomList;
		} catch (Exception ex) {
			if (session != null && session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			throw new RuntimeException(ex);
		}
	}

	@Override
	public Room findById(long id) throws DaoException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Room room = session.get(Room.class, id);
		session.close();
		return room;
	}

	@Override
	public void addRoom(Room room) throws DaoException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.save(room);
		transaction.commit();
		session.close();
	}

	@Override
	public void updateRoom(Room room) throws DaoException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.update(room);
		transaction.commit();
		session.close();
	}

	@Override
	public void removeRoom(Room room) throws DaoException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.delete(room);
		transaction.commit();
		session.close();
	}
}
