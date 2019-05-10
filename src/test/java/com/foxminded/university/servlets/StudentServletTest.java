package com.foxminded.university.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;


public class StudentServletTest {
	private StudentServlet servlet;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private RequestDispatcher dispatcher;

	@Before
	public void setUp() {
		servlet = new StudentServlet();
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		dispatcher= mock(RequestDispatcher.class);
	}
	
	@Test
	public void testDoGet()throws ServletException, IOException {
		when(request.getRequestDispatcher("StudentList.jsp")).thenReturn(dispatcher);
		servlet.doGet(request, response);
		verify(request, times(1)).getRequestDispatcher("StudentList.jsp");
		verify(request, never()).getSession();
		verify(dispatcher).forward(request, response);	
	}
	
	@Test
	public void testDoPost() throws ServletException, IOException {
		when(request.getParameter("studentID")).thenReturn("12");
		when(request.getParameter("groupID")).thenReturn("2");
		when(request.getParameter("firstName")).thenReturn("Pablo");
		when(request.getParameter("lastName")).thenReturn("Keke");
		servlet.doPost(request, response);
		verify(request).getParameter("studentID");
		verify(response).sendRedirect("/com.foxminded/view/students");
	}
	@Test
	public void testDoPut() throws ServletException, IOException {
		when(request.getParameter("studentID")).thenReturn("12");
		when(request.getParameter("groupID")).thenReturn("2");
		when(request.getParameter("firstName")).thenReturn("Pablo");
		when(request.getParameter("lastName")).thenReturn("Keke");
		servlet.doPut(request, response);
		verify(request).getParameter("studentID");
		verify(response).sendRedirect("/com.foxminded/view/students");
	}
	@Test
	public void testDoDelete() throws ServletException, IOException {
		when(request.getParameter("studentID")).thenReturn("12");
		servlet.doDelete(request, response);
		verify(request).getParameter("studentID");
		verify(response).sendRedirect("/com.foxminded/view/students");
	}
}