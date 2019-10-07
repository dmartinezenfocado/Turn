<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tg" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <title>Estaciones</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"> </script>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"> </script>
        <link href="<c:url value='/static/css/index.css' />" rel="stylesheet"></link>
        <link href="<c:url value='/static/css/bootstrap.css' />"  rel="stylesheet"></link>
         <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>

        
    </head>
    <body>
        
       
         <%@include file="menu.jsp" %>
         <%@include file="authheader.jsp" %>	
        <div class="container">
           
            <jsp:useBean id="pagedListHolder" scope="request"
                         type="org.springframework.beans.support.PagedListHolder" />
            <c:url value="estview" var="pagedLink">
                <c:param name="p" value="~" />
            </c:url>
            
            
            <div class="row">
                <h1>Estaciones</h1>
                <p>
                    <a href="<c:url value="addest"/>" class="btn btn-success"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>Nueva Estacion</a>
                </p>
                <div class="panel-body">
                    <form method="get" action = "estview">
                                <input name="busqueda" type="text" cssClass="form-control"/>
                                <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                                <input type="submit" value="Buscar" class="btn btn-success"/>
                    </form>
                </div>    
                <table class="table table-bordered table-striped table-hover">
                    <thead>
                        <tr>
                            <th>Estacion</th>
                            <th>Departamento</th>
                            <th>Estatus</th>
                            <th>Accion</th>
                        </tr>
                    </thead>
                    <tbody> 
                        <c:forEach items="${pagedListHolder.pageList}" var="dato">
                            <tr>
                                <td><c:out value="${dato.getEstacion()}" /></td>
                                <td><c:out value="${dato.getDepartamento()}" /></td>
                                <td><c:out value="${dato.getEstatus()}" /></td>
                                
                                <td>
                                    <a href="<c:url value="editest?id=${dato.getId()}"/>" ><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>&nbsp;&nbsp;
         
                                    <a href="<c:url value="deleteest?id=${dato.getId()}"/>"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a>&nbsp;&nbsp;
      
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                    
                </table>
                
                  <tg:paging pagedListHolder="${pagedListHolder}"
                           pagedLink="${pagedLink}" />
                
             
            </div>
        </div>
                  
                  
   
    </body>
</html>
