<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tg" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <title>Turnos</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"> </script>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"> </script>
        <link href="<c:url value='/static/css/index.css' />" rel="stylesheet"></link>
        <link href="<c:url value='/static/css/bootstrap.css' />"  rel="stylesheet"></link>
         <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
    </head>
    <body>

      
                <div class="row" id="turnt">
                 
               <jsp:useBean id="pagedListHolder" scope="request"
                         type="org.springframework.beans.support.PagedListHolder" />
                <c:url value="turnosview" var="pagedLink">
                    <c:param name="p" value="~" />
                </c:url>
                    
                <h2>
                <table class="table table-bordered table-striped table-hover">
                    <thead>
                        <tr>
                     
                            <th>Turno</th>
                            <th>Estacion</th>
                            <th>Nombre</th>
                            <th>Cedula</th>
                            <th>Servicio</th>
                            <th>Ticket ID</th>
                            <th>Accion</th>
                        
                        </tr>
                    </thead>
                    <tbody> 
                        <c:forEach items="${pagedListHolder.pageList}" var="dato">
                            <tr>
                                
                                <td><c:out value="${dato.getTurno()}" /></td>
                                <td><c:out value="${dato.getEstacion()}" /></td>
                                <td><c:out value="${nombre}" /></td>
                                <td><c:out value="${cedula}" /></td>
                                <td><c:out value="${dato.getServicio()}" /></td>
                                <td><c:out value="${dato.getTicketFk()}" /></td>
                                <td>
                                    <a href="<c:url value="completarturno?id=${dato.getId()}"/>"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span></a>&nbsp;&nbsp;
                                    
                                    <a href="<c:url value="transferirturno?id=${dato.getId()}"/>" ><span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span></a>&nbsp;&nbsp;
                                   
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                    
                </table>
                </h2> 
                  <tg:paging pagedListHolder="${pagedListHolder}"
                           pagedLink="${pagedLink}" />
                
             
            </div>

    </body>
</html>
