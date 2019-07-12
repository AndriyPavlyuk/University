package com.foxminded.university.dao;

import java.util.List;



import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.foxminded.university.domain.Student;
import com.foxminded.university.util.HibernateUtil;

	@Repository
public class HibernateStudentDao implements StudentDao {
		
	public HibernateStudentDao() {
		
	}
		
	public List<Student> findAll() {
		Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            List<Student> studentList = session.createNamedQuery(Student.FIND_ALL, Student.class).getResultList();
            session.getTransaction().commit();
            return studentList;
        } catch (Exception ex) {
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            throw new RuntimeException(ex);
        }
    }

	@Override
	public Student findById(long id) throws DaoException {
		Session session = HibernateUtil.getSessionFactory().openSession();
        Student student = session.get(Student.class, id);
        session.close();
        return student;
	}

	@Override
	public void addStudent(Student student) throws DaoException {
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(student);
        transaction.commit();
        session.close();
	}

	@Override
	public void updateStudent(Student student) throws DaoException {
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(student);
        transaction.commit();
        session.close();
	}

	@Override
	public void removeStudent(Student student) throws DaoException {
		 Session session =HibernateUtil.getSessionFactory().openSession();
	        Transaction transaction = session.beginTransaction();
	        session.delete(student);
	        transaction.commit();
	        session.close();
	}
}
