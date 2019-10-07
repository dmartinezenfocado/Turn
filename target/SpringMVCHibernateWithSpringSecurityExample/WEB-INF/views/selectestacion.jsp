<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tg" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <title>Seleccionar estacion</title>
         <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"> </script>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"> </script>
        <link href="<c:url value='/static/css/index.css' />" rel="stylesheet"></link>
        <link href="<c:url value='/static/css/bootstrap.css' />"  rel="stylesheet"></link>
         <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
       
    </head>
    <body>
        
        <%@include file="m2.jsp" %>
        <%@include file="authheader.jsp" %>
        <div class="container">
            

            <div class="panel panel-primary">
                
                <div class="panel-heading">Seleccione</div>
                
                <div class="panel-body">
                    
                    <form:form method="post" commandName="estacion">
                        <h1>Seleccione una Estacion de trabajo</h1>
                        
                        <form:errors path="*" element="div" cssClass="alert alert-danger"/>

                        <p>
                            <form:label path="estacion">Estacion</form:label>
                            <form:select path="estacion" cssClass="form-control">
                                <form:option value="None">None</form:option>
                                <form:options items="${estacionesLista}"/>
                            </form:select>
                        </p>
                         
                        
                        <hr/>
                        <input type="submit" value="Iniciar" class="btn btn-danger" />


                    </form:form>
                </div>    

            </div>
            
          
        </div>
    </body>
</html>
