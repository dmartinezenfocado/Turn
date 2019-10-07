<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <title>Editar Servicio</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
       
    </head>
    <body>
        
        <%@include file="menu.jsp" %>
        <%@include file="authheader.jsp" %>
        <div class="container">
            
            <ol class="breadcrumb">
                <li><a href="<c:url value="serview" />">Listado de Servicios</a></li>
                <li class="active">Agregar</li>
               
            </ol>
                
            <div class="panel panel-primary">
                
                <div class="panel-heading">Agregar</div>
                
                <div class="panel-body">
                    
                    <form:form method="post" commandName="serv">
                        <h1>Modificar</h1>
                        
                        <form:errors path="*" element="div" cssClass="alert alert-danger"/>

                        <p>
                            <form:label path="tipoServicio">Tipo de servicio</form:label>
                            <form:input path="tipoServicio" cssClass="form-control"/>

                        </p>

                         <p>
                            <form:label path="departamento">Departamento</form:label>
                            <form:select path="departamento" cssClass="form-control">
                                <form:option value="None">None</form:option>
                                <form:options items="${depLista}"/>
                            </form:select>
                        </p>
                        
                        <hr/>
                        <input type="submit" value="Modificar" class="btn btn-danger" />


                    </form:form>
                </div>    

            </div>
            
          
        </div>
    </body>
</html>
