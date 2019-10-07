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
        
       
         <%@include file="menu.jsp" %>
         <%@include file="authheader.jsp" %>	
        <div class="container">
           
            <jsp:useBean id="pagedListHolder" scope="request"
                         type="org.springframework.beans.support.PagedListHolder" />
            <c:url value="turnosview" var="pagedLink">
                <c:param name="p" value="~" />
            </c:url>
            
           <span class="glyphicon glyphicon-bullhorn" aria-hidden="true"> Timbrar</span>
            <div class="panel-body">
            
                
                <audio controls>
                    <source src="${pageContext.request.contextPath}/static/audio/tim.mp3" type="audio/mpeg">
                </audio>
            </div>
            <div class="row">
                <h1>Turnos</h1>
                <p>
                    <a href="<c:url value="callnext"/>" class="btn btn-success"><span class="glyphicon glyphicon-forward" aria-hidden="true"></span>Llamar Siguiente Turno</a>
                    <a href="<c:url value="cerrarestacion"/>" class="btn btn-danger"><span class="glyphicon glyphicon-off" aria-hidden="true"></span>Cerrar Estacion</a>
                </p>
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
        </div>
                  
    <center> ${msj} </center>     
    
    <br>
    <br>
    <br>
    <br>
     <br>
    <br>
     <br>
    <br>
     <div class="container">
            

            <div class="panel panel-primary">
                
                <div class="panel-heading">Seleccione</div>
                
                <div class="panel-body">
                    
                    <form:form method="post" action="selectestacion" commandName="estacion">
                        <h1>Cambiar estacion de trabajo</h1>
                        
                        <form:errors path="*" element="div" cssClass="alert alert-danger"/>

                        <p>
                            <form:label path="estacion">Estacion</form:label>
                            <form:select path="estacion" cssClass="form-control">
                                <form:option value="None">None</form:option>
                                <form:options items="${estacionesLista}"/>
                            </form:select>
                        </p>
                         
                        
                        <hr/>
                        <input type="submit" value="Cambiar" class="btn btn-danger" />


                    </form:form>
                </div>    

            </div>
            
          
        </div>
   
    </body>
</html>
