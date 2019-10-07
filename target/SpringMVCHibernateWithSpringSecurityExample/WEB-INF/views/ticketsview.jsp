<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tg" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <title>Tickets</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"> </script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"> </script>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"> </script>
        <link href="<c:url value='/static/css/index.css' />" rel="stylesheet"></link>
        <link href="<c:url value='/static/css/bootstrap.css' />"  rel="stylesheet"></link>
         <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>

        
    </head>
    <body>
        
       
         <%@include file="menu.jsp" %>
         <%@include file="authheader.jsp" %>	
        <div class="container" id="tv">
           
            <jsp:useBean id="pagedListHolder" scope="request"
                         type="org.springframework.beans.support.PagedListHolder" />
            <c:url value="ticketsview" var="pagedLink">
                <c:param name="p" value="~" />
            </c:url>
            
            
            <div class="row">
                <h1>Tickets</h1>
                <p>
                    <a href="<c:url value="addticket"/>" class="btn btn-success"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>Nuevo Ticket</a>
                </p>
                <div class="panel-body">
                    <form method="get" action = "ticketsview">
                                <input name="busqueda" type="text" cssClass="form-control"/>
                                <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                                <input type="submit" value="Buscar" class="btn btn-success"/>
                    </form>
                </div>    
                <table class="table table-bordered table-striped table-hover">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Turno</th>
                            <th>Servicio</th>
                            <th>Departamento</th>
                            <th>Nombre</th>
                            <th>Cedula</th>
                            <th>Estado</th>
                            <th>Fecha de apertura</th>
                            <th>Fecha de Cierre</th>
                            <th>Accion</th>
                        </tr>
                    </thead>
                    <tbody> 
                        <c:forEach items="${pagedListHolder.pageList}" var="dato">
                            <tr>
                                <td><c:out value="${dato.getId()}" /></td>
                                <td><c:out value="${dato.getTurno()}" /></td>
                                <td><c:out value="${dato.getServicio()}" /></td>
                                <td><c:out value="${dato.getDepartamento()}" /></td>
                                <td><c:out value="${dato.getNombreCliente()} ${dato.getApellidoCliente()}" /></td>
                                <td><c:out value="${dato.getCedulaCliente()}" /></td>
                                <td><c:out value="${dato.getStatus()}" /></td>
                                <td><c:out value="${dato.getFechaHora()}" /></td>
                                <td><c:out value="${dato.getFechaCierre()}" /></td>
                                <td>
                                    <a href="<c:url value="ticketv?id=${dato.getId()}"/>"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></a>&nbsp;&nbsp;
         
                                    <a href="<c:url value="deleteticket?id=${dato.getId()}"/>"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a>&nbsp;&nbsp;&nbsp;&nbsp;
      
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
