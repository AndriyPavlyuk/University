package com.foxminded.university.util;


import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import com.foxminded.university.domain.Group;
import com.foxminded.university.domain.Person;
import com.foxminded.university.domain.Room;
import com.foxminded.university.domain.ScheduleRecord;
import com.foxminded.university.domain.Student;
import com.foxminded.university.domain.Subject;
import com.foxminded.university.domain.Teacher;


public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();
	
	public HibernateUtil() {
		
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	private static SessionFactory buildSessionFactory() {
		try {
			ServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
			MetadataSources sources = new MetadataSources(registry);
			sources.addAnnotatedClass(Student.class);
			sources.addAnnotatedClass(Person.class);
			sources.addAnnotatedClass(Group.class);
			sources.addAnnotatedClass(Room.class);
			sources.addAnnotatedClass(Subject.class);
			sources.addAnnotatedClass(Teacher.class);
			sources.addAnnotatedClass(ScheduleRecord.class);
			return sources.buildMetadata().buildSessionFactory();
		} catch (HibernateException ex) {
			System.err.println("SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
}