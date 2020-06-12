<%--
  Created by IntelliJ IDEA.
  User: katja
  Date: 12/06/2020
  Time: 18:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<html>
<head>
    <title>User Page</title>
    
    <link  rel="stylesheet" type="text/css"
           href="${pageContext.request.contextPath}/resources/css/style.css"/>
    
    <link  rel="stylesheet" type="text/css"
           href="${pageContext.request.contextPath}/resources/css/add-book-style.css"/>
</head>
<body>

<div id="wrapper">
    <div id="header">
        <h2>Welcome to user profile</h2>
    </div>
</div>
<div id="container">
    
    <div style="clear: both;"></div>
    <p>
        <a href="${pageContext.request.contextPath}/list">Book List</a>
    </p>

</div>

<br>

<!-- Add a logout button -->
<form:form action="${pageContext.request.contextPath}/logout"
           method="POST">
    
    <input type="submit" value="Logout" />

</form:form>
</body>
</html>
