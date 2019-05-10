package com.foxminded.university.servlets;

import java.io.IOException;

import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.university.dao.DaoException;
import com.foxminded.university.dao.JdbcGroupDao;
import com.foxminded.university.dao.JdbcStudentDao;
import com.foxminded.university.domain.Group;
import com.foxminded.university.domain.Student;

public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JdbcGroupDao jdbcGroupDao = new JdbcGroupDao();
	private JdbcStudentDao jdbcStudentDao = new JdbcStudentDao(jdbcGroupDao);
	private Group group;
	private Collection<Student> students;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			students = jdbcStudentDao.findAll();
		} catch (DaoException e) {
		}
		req.setAttribute("students", students);
		RequestDispatcher dispatcher = req.getRequestDispatcher("StudentList.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method = req.getParameter("_method");
		if (method != null && method.equals("put"))
			doPut(req, resp);
		else if (method != null && method.equals("delete"))
			doDelete(req, resp);
		else {
			long studentID = Long.parseLong(req.getParameter("studentID"));
			long groupID = Long.parseLong(req.getParameter("groupID"));
			String firstName = req.getParameter("firstName");
			String lastName = req.getParameter("lastName");
			try {
				group = jdbcGroupDao.findById(groupID);
			} catch (DaoException e1) {
			}
			Student student = new Student();
			student.setPersonID(studentID);
			student.setGroup(group);
			student.setFirstName(firstName);
			student.setLastName(lastName);
			try {
				jdbcStudentDao.addStudent(student);
			} catch (DaoException e) {
			}
			resp.sendRedirect("/com.foxminded/view/students");
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long studentID = Long.parseLong(req.getParameter("studentID"));
		Student student = null;
		try {
			student = jdbcStudentDao.findById(studentID);
		} catch (DaoException e2) {
		}
		try {
			student.setGroup(jdbcGroupDao.findById(Long.parseLong(req.getParameter("groupID"))));
		} catch (NumberFormatException | DaoException e1) {
		}
		student.setFirstName(req.getParameter("firstName"));
		student.setLastName(req.getParameter("lastName"));
		try {
			jdbcStudentDao.updateStudent(student);
		} catch (DaoException e) {
		}
		resp.sendRedirect("/com.foxminded/view/students");
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		long studentID = Long.parseLong(req.getParameter("studentID"));
		Student student = null;
		try {
			student = jdbcStudentDao.findById(studentID);
		} catch (DaoException e2) {
		}
		try {
			jdbcStudentDao.removeStudent(student);
		} catch (DaoException e) {
		}
		resp.sendRedirect("/com.foxminded/view/students");
	}
}