package com.foxminded.university.servlets;

import java.io.IOException;



import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.foxminded.university.dao.DaoException;
import com.foxminded.university.dao.JdbcGroupDao;
import com.foxminded.university.dao.JdbcStudentDao;
import com.foxminded.university.domain.Student;

@Controller
public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired
	private JdbcGroupDao jdbcGroupDao;
	@Autowired
	private JdbcStudentDao jdbcStudentDao;
	private Student student;
	private static String INSERT_OR_EDIT = "/StudentAdd.jsp";
    private static String LIST_STUDENT = "/StudentList.jsp";
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forward="";
	        String action = request.getParameter("action");
	    	if (action.equalsIgnoreCase("delete")){
	            long studentID = Long.parseLong(request.getParameter("personID"));
	            try {
					student = jdbcStudentDao.findById(studentID);
				} catch (DaoException e1) {
					e1.printStackTrace();
				}
	            try {
					jdbcStudentDao.removeStudent(student);
				} catch (DaoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            forward = LIST_STUDENT;
	            try {
					request.setAttribute("students", jdbcStudentDao.findAll());
				} catch (DaoException e) {
					e.printStackTrace();
				}    
	        } else if (action.equalsIgnoreCase("edit")){
	            forward = INSERT_OR_EDIT;
	            int studentID = Integer.parseInt(request.getParameter("personID"));
	            try {
					student = jdbcStudentDao.findById(studentID);
				} catch (DaoException e) {
					e.printStackTrace();
				}
	            request.setAttribute("student", student);
	        } else if (action.equalsIgnoreCase("StudentList")){
	            forward = LIST_STUDENT;
	            try {
					request.setAttribute("students", jdbcStudentDao.findAll());
				} catch (DaoException e) {
					e.printStackTrace();
				}
	        } else {
	            forward = INSERT_OR_EDIT;
	        }
	        RequestDispatcher view = request.getRequestDispatcher(forward);
	        view.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (student== null) {
        	long studentID = Long.parseLong(request.getParameter("studentID"));
			long groupID = Long.parseLong(request.getParameter("groupID"));
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			Student student = new Student();
			student.setPersonID(studentID);
			try {
				student.setGroup(jdbcGroupDao.findById(groupID));
			} catch (DaoException e1) {
				e1.printStackTrace();
			}
			student.setFirstName(firstName);
			student.setLastName(lastName);
			try {
				jdbcStudentDao.addStudent(student);
			} catch (DaoException e) {
			}
        }
        else
        {
        	long studentID = Long.parseLong(request.getParameter("studentID"));
    		try {
    			student = jdbcStudentDao.findById(studentID);
    		} catch (DaoException e2) {
    		}
    		try {
    			student.setGroup(jdbcGroupDao.findById(Long.parseLong(request.getParameter("groupID"))));
    		} catch (NumberFormatException | DaoException e1) {
    		}
    		student.setFirstName(request.getParameter("firstName"));
    		student.setLastName(request.getParameter("lastName"));
    		try {
    			jdbcStudentDao.updateStudent(student);
    		} catch (DaoException e) {
    		}
        }
        RequestDispatcher view = request.getRequestDispatcher(LIST_STUDENT);
        try {
			request.setAttribute("students", jdbcStudentDao.findAll());
		} catch (DaoException e) {
			e.printStackTrace();
		}
        view.forward(request, response);
    }
}