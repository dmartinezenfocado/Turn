<%-- 
    Document   : p
    Created on : Sep 27, 2017, 5:54:49 PM
    Author     : delvinmartinez
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="row" id="turnos">
          <h3>Turno&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Estacion</h3>
             <c:forEach items="${turnos}" var="dato">
                            
                <FONT SIZE=30 color=red>   ${dato.getTurno()}&nbsp;&nbsp;  <span class="glyphicon glyphicon-arrow-right" aria-hidden="true"></span>  &nbsp;&nbsp;  ${dato.getEstacion()} </font>      
                <br>
                                   
                            
             </c:forEach>
         </div>       
    </body>
</html>
