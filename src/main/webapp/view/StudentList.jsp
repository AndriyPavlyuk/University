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
			<a href="StudentForm.jsp">Add Student</a> &nbsp;&nbsp;&nbsp; 
			<a href="students">List of Students</a>
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
				<td>
					<form method="post">
						<input name="_method" type="hidden" value="delete"> <input
							name="studentID" type="hidden" value="${student.getPersonID()}">
						<input style="background: #F00;" type="submit" value="Delete">
					</form>
				</td>
				<td>
					<form action = "StudentUpdate.jsp"  method="post">
						<input name="studentID" type="hidden" value="${student.getPersonID()}">
						<input name="groupID" type="hidden" value="${student.getGroup().getName()}">
						<input name="firstName" type="hidden" value="${student.firstName}">
						<input name="lastName" type="hidden" value="${student.lastName}">
						<input type="submit" value="Update">
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>