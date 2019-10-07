<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <title>Nuevo Ticket</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"> </script>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"> </script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"> </script>
        <link href="<c:url value='/static/css/index.css' />" rel="stylesheet"></link>
        <link href="<c:url value='/static/css/bootstrap.css' />"  rel="stylesheet"></link>
         <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
       
    </head>
    <body>
        
        <%@include file="m2.jsp" %>
        <%@include file="authheader.jsp" %>
        <div class="container" id="newti">
            
            <ol class="breadcrumb">
                <li><a href="<c:url value="ticketsview" />">Listado de Tickets</a></li>
                <li class="active">Agregar</li>
               
            </ol>
                
            <div class="panel panel-primary">
                
                <div class="panel-heading">Agregar</div>
                
                <div class="panel-body">
                    
                    <form:form method="post" action="addticket2" commandName="ticket">
                        <h1>Digite su Cedula</h1>
                        
                        <form:errors path="*" element="div" cssClass="alert alert-danger"/>

                        <p>
                            <form:input path="cedulaCliente"  id="inputlg"  class="form-control input-lg"/>
                        </p>

                        <hr/>
                        <input type="submit" value="Siguiente" class="btn btn-danger" />
                       

                    </form:form>
                </div>    

            </div>
            
          
        </div>
    </body>
</html>
