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
import com.foxminded.university.dao.HibernateGroupDao;
import com.foxminded.university.dao.HibernateStudentDao;
import com.foxminded.university.domain.Student;

@Controller
public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired
	private HibernateGroupDao hibernateGroupDao;
	@Autowired
	private HibernateStudentDao hibernateStudentDao;
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
					student = hibernateStudentDao.findById(studentID);
				} catch (DaoException e1) {
					e1.printStackTrace();
				}
	            try {
	            	hibernateStudentDao.removeStudent(student);
				} catch (DaoException e) {
					e.printStackTrace();
				}
	            forward = LIST_STUDENT;
	            request.setAttribute("students", hibernateStudentDao.findAll());    
	        } else if (action.equalsIgnoreCase("edit")){
	            forward = INSERT_OR_EDIT;
	            int studentID = Integer.parseInt(request.getParameter("personID"));
	            try {
					student = hibernateStudentDao.findById(studentID);
				} catch (DaoException e) {
					e.printStackTrace();
				}
	            request.setAttribute("student", student);
	        } else if (action.equalsIgnoreCase("StudentList")){
	            forward = LIST_STUDENT;
	            request.setAttribute("students", hibernateStudentDao.findAll());
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
				student.setGroup(hibernateGroupDao.findById(groupID));
			} catch (DaoException e1) {
				e1.printStackTrace();
			}
			student.setFirstName(firstName);
			student.setLastName(lastName);
			try {
				hibernateStudentDao.addStudent(student);
			} catch (DaoException e) {
			}
        }
        else
        {
        	long studentID = Long.parseLong(request.getParameter("studentID"));
    		try {
    			student = hibernateStudentDao.findById(studentID);
    		} catch (DaoException e2) {
    		}
    		try {
    			student.setGroup(hibernateGroupDao.findById(Long.parseLong(request.getParameter("groupID"))));
    		} catch (NumberFormatException | DaoException e1) {
    		}
    		student.setFirstName(request.getParameter("firstName"));
    		student.setLastName(request.getParameter("lastName"));
    		try {
    			hibernateStudentDao.updateStudent(student);
    		} catch (DaoException e) {
    		}
        }
        RequestDispatcher view = request.getRequestDispatcher(LIST_STUDENT);
        request.setAttribute("students", hibernateStudentDao.findAll());
        view.forward(request, response);
    }
}