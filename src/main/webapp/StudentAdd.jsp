<%@ page isELIgnored="false" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE xml>
<html>
<head>
<title>University Application</title>
</head>
<body>
	<center>
		<h1>University Management</h1>
		<h2><a href="students?action=StudentList">List of Students</a></h2>
	</form>
	</center>
	<div align="center">
		<form method="POST" action='students' name="frmAddUser">
			<table border="1" cellpadding="5">
				<caption>
					 <h2>
                    <c:if test="${student != null}">
                        Edit Student
                    </c:if>
                    <c:if test="${student == null}">
                        Add New Student
                    </c:if>
                </h2>
				</caption>
				<c:if test="${student != null}">
                    <input type="hidden" name="studentID" value="<c:out value='${student.personID}' />" />
                </c:if> 
				<c:if test="${student==null}">
				<tr>
					<th>Student ID:</th>
					<td><input type="text" name="studentID" size="45"
						value="<c:out value='${student.personID}' />" /></td>
				</tr>
				</c:if> 
				<tr>
					<th>Group ID:</th>
					<td><input type="text" name="groupID" size="45"
						value="<c:out value='${student.getGroup().getGroupID()}' />" /></td>
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
			</form>
	</div>
</body>
</html>