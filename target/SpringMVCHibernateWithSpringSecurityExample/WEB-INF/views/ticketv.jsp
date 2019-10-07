<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tg" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"> </script>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"> </script>
        <title>Ver Ticket</title>
        <script type="text/javascript" src="static/js/qrcode.js"></script>
        <script type="text/javascript" src="static/js/jquery.min.js"></script>
        <script type="text/javascript" src="static/js/qrcode.min.js"></script>
        
    </head>
    <body>
        

        <%@include file="menu.jsp" %>
         <%@include file="authheader.jsp" %>
        
        <div class="container">
            
            <ol class="breadcrumb">
                <li><a href="<c:url value="ticketsview" />">Listado de Tickets</a></li>
                <li class="active">Agregar</li>
               
            </ol>
                
            <div class="panel panel-primary">
                
                <p>
                    <a href="javascript:print()" class="btn btn-success"><span class="glyphicon glyphicon-print" aria-hidden="true"></span>IMPRIMIR</a>
                </p>
                
                <div class="panel-heading">Ticket</div>
                
                <div class="panel-body">
                    
                    <form:form method="post" commandName="ticket">
                        <h1>Ticket: ${ticket.getTurno()}</h1>
                        
                        <form:errors path="*" element="div" cssClass="alert alert-danger"/>

                        <p>
                            <form:label path="turno">Turno: ${ticket.getTurno()}</form:label>
                     

                        </p>

                         <p>
                            <form:label path="servicio">Servicio: ${ticket.getServicio()}</form:label>
                 
                            
                        </p>

                         <p>
                            <form:label path="nombreCliente">Cliente: ${ticket.getNombreCliente()} ${ticket.getApellidoCliente()}</form:label>
           
                        </p>
                        
                         <p>
                            <form:label path="cedulaCliente">Cedula: ${ticket.getCedulaCliente()} </form:label>
           
                        </p>


                         <p>
                            <form:label path="status">Estado: ${ticket.getStatus()}</form:label>
                        </p>
                        
                          <p>
                            <form:label path="fechaHora">Fecha de creacion: ${ticket.getFechaHora()}</form:label>
                        </p>
                        
                  
                        <hr/>


                    </form:form>
                        
                        
      
                        
                </div>    
                
                <center>
                    <div id="qrcode"></div>
                    <script type="text/javascript">
                    new QRCode(document.getElementById("qrcode"), 
                    "\n\
                     Turno: ${ticket.getTurno()}\n\
                     Servicio: ${ticket.getServicio()}\n\
                     Cliente: ${ticket.getNombreCliente()} ${ticket.getApellidoCliente()}\n\
                     Cedula: ${ticket.getCedulaCliente()}");
                    </script>
                </center>
                
                
               

  

            </div>

                
                 <hr/>
                 

          
        </div>
                
       
                
                
                
        
    </body>
</html>
