<%--
  Created by IntelliJ IDEA.
  User: katja
  Date: 11/06/2020
  Time: 09:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Bookstore Company Home Page</title>
    <link  rel="stylesheet" type="text/css"
           href="${pageContext.request.contextPath}/resources/css/style.css"/>
</head>

<body>

    <div id="wrapper">
        <div id="header">
            <h2>Books available on the bookstore</h2>
        </div>
    </div>
    
    <!-- Add a login button -->
    <form:form action="${pageContext.request.contextPath}/profile"
               method="GET">
        
        <input type="submit" value="Log In" />
    
    </form:form>
    
    <div id="container">
        
        <div id="content">
        
            <c:if test="${data.size() > 0 }">
                <div class="panel-footer">
                    Showing books ${number*size+1} to ${number*size+data.size()} of total ${totalElements}
                    <ul class="pagination">
                        <c:forEach begin="0" end="${totalPages-1}" var="page">
                            <a href="list?page=${page}&size=${size}" class="page-link">${page+1}</a>
                            <wbr>  <wbr>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>
            
            <form:form action="search" method="GET">
                Search book: <input type="text" name="theSearchName" />
                
                <input type="submit" value="Search" class="add-button" />
            </form:form>
            
            
            <table border="2" width="70%" cellpadding="3"
                       class="table">
                <thead class="thead-dark">
                <tr align="center">
                    <th scope="col">Id</th>
                    <th scope="col">Title</th>
                    <th scope="col">Author</th>
                    <th scope="col">Genre</th>
                    <th scope="col">Pages</th>
                    <th scope="col">Price</th>
                </tr>
                </thead>
                    <tbody id="myTable">
                        <c:choose>
                            <c:when test="${data.size() > 0 }">
                                <c:forEach var="book" items="${data}">
                                    <tr align="center">
                                        <td>${book.id}</td>
                                        <td>${book.title}</td>
                                        <td>${book.author}</td>
                                        <td>${book.genre}</td>
                                        <td>${book.pagesCount}</td>
                                        <td>${book.price}</td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr align="center">
                                    <td colspan="6">No Books available</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
            </table>
        
        </div>
    </div>
</body>

</html>



