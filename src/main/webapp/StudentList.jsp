<%@ page isELIgnored="false" language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Students</title>
</head>
<style type="text/css">
body {
	text-align: center;
}

table {
	margin-left: 15%;
	min-width: 70%;
	border: 1px solid #CCC;
	border-collapse: collapse;
}

table tr {
	line-height: 30px;
}

table tr th {
	background: #000033;
	color: #FFF;
}

table tr td {
	border: 1px solid #CCC;
	margin: 5px;
}

input[type=text], input[type=email], input[type=tel] {
	min-width: 60%;
}

input[type=submit] {
	background: green;
	padding: 5px;
	margin: 5px;
	color: #FFF;
}
</style>
<body>
	<table>
		<h1>University Management</h1>
		<h2>
		 <a href="students?action=insert">Add Student</a>
		 &nbsp;&nbsp;&nbsp;
		 <a href="students?action=StudentList">List of Students</a>
		</h2>
	</table>
	<table>
		<caption>
			<h3>List of Students</h3>
		</caption>
		<tr>
			<th>Student ID</th>
			<th>Group Name</th>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Delete</th>
			<th>Update</th>
		</tr>
		<c:forEach items="${students}" var="student">
			<tr>
				<td>${student.personID}</td>
				<td>${student.getGroup().getName()}</td>
				<td>${student.firstName}</td>
				<td>${student.lastName}</td>
				<td><a href="students?action=delete&personID=<c:out value="${student.personID}"/>">Delete</a></td>
				<td><a href="students?action=edit&personID=<c:out value="${student.personID}"/>">Update</a></td> 
			</tr>
		</c:forEach>
	</table>
</body>
</html>