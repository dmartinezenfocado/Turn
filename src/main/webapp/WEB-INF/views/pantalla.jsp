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
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"> </script>
        <link href="<c:url value='/static/css/index.css' />" rel="stylesheet"></link>
        <link href="<c:url value='/static/css/bootstrap.css' />"  rel="stylesheet"></link>
         <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
        <title>Pantalla</title>
    </head>
    <body>
        <br>
        <div class="container">
            <h1>Turnos</h1>
            <br>
            
            <div class="row">
                <button class="btn btn-primary">Ajax1</button>
                <button class="btn btn-danger">Ajax2</button>
            </div>
             
            <h3>Turno&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Estacion</h3>
             <c:forEach items="${turnos}" var="dato">
                            
                <FONT SIZE=30 color=red>   ${dato.getTurno()}&nbsp;&nbsp;  <span class="glyphicon glyphicon-arrow-right" aria-hidden="true"></span>  &nbsp;&nbsp;  ${dato.getEstacion()} </font>      
                <br>
                                   
                            
             </c:forEach>
        </div>
        
        <div class="container">
            <iframe width="600" height="500" src="https://www.youtube.com/embed/R1D7LuIf3Zc" frameborder="0" allowfullscreen align="right"></iframe>
        </div>
    </body>
</html>
