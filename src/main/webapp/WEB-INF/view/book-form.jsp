<%--
  Created by IntelliJ IDEA.
  User: katja
  Date: 11/06/2020
  Time: 18:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<html>
<head>
    <title>Save book</title>
    
    <link  rel="stylesheet" type="text/css"
           href="${pageContext.request.contextPath}/resources/css/style.css"/>
    
    <link  rel="stylesheet" type="text/css"
           href="${pageContext.request.contextPath}/resources/css/add-book-style.css"/>
    <style>
        .error {color:red}
    </style>
</head>
<body>

    <div id="wrapper">
        <div id="header">
            <h2>CRM - Book Store Relationship Manager</h2>
        </div>
    </div>
    <div id="container">
        
        <h3>Add new Book</h3>
        
    <c:if test="${not empty exception}">
        <div class="exception">
            <h4>Error appeared: "${exception}"</h4>
        </div>
    </c:if>
    
    <c:if test="${not empty message}">
        <div class="message">
            <h4>"${message}"</h4>
        </div>
    </c:if>
    
    <form:form action="${pageContext.request.contextPath}/admin/saveBook" modelAttribute="book" method="POST">
        
        <form:hidden path="id"/>
        
        <table>
            <tbody>
            <tr>
                <td><label>Author:</label></td>
                <td><form:input path="author"/></td>
                <td><form:errors path="author" cssClass="error" /></td>
            </tr>
            <tr>
                <td><label>Title:</label></td>
                <td><form:input path="title"/></td>
                <td><form:errors path="title" cssClass="error" /></td>
            </tr>
            <tr>
                <td><label>Genre:</label></td>
                <td><form:input path="genre"/></td>
                <td><form:errors path="genre" cssClass="error" /></td>
            </tr>
            <tr>
                <td><label>Number of pages:</label></td>
                <td><form:input path="pagesCount" type="number"/></td>
                <td><form:errors path="pagesCount" cssClass="error" /></td>
            </tr>
            <tr>
                <td><label>Price:</label></td>
                <td><form:input path="price" type="number" step=".01"/></td>
                <td><form:errors path="price" cssClass="error" /></td>
            </tr>
            <tr>
                <td><label></label></td>
                <td><input type="submit" value="Save" class="save"/></td>
            </tr>
            </tbody>
        </table>
    </form:form>
    
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
