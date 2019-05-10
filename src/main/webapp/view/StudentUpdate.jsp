<%@ page isELIgnored="false" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<center>
		<h1>University Management</h1>
		<h2>
			<a href="new">Add New Student</a> &nbsp;&nbsp;&nbsp; 
			<a href="list">List of All Students</a>
		</h2>
	</center>
	<div align="center">
			<form action="update" method="post">
			<input name="_method" type="hidden" value="put">
		<table border="1" cellpadding="5">
			<caption>
				<h2>Edit Student</h2>
			</caption>
			<tr>
				<th>Student ID:</th>
				<td><input type="text" name="studentID" size="45"
					value="<c:out value='${student.personID}'/>" /></td>
			</tr>
			<tr>
				<th>Group ID:</th>
				<td><input type="text" name="groupID" size="45"
					value="<c:out value='${student.getGroup().getGroupID()}'/>" /></td>
			</tr>
			<tr>
				<th>First Name:</th>
				<td><input type="text" name="firstName" size="5"
					value="<c:out value='${student.firstName}' />" /></td>
			</tr>
			<tr>
				<th>Last Name:</th>
				<td><input type="text" name="lastName" size="5"
					value="<c:out value='${student.lastName}' />" /></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit"
					value="Save" /></td>
			</tr>
		</table>
	</div>
</body>
</body>
</html>