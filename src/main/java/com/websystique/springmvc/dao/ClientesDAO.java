/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.Clientes;
import com.websystique.springmvc.util.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author delvinmartinez
 */
public class ClientesDAO {
    

    public static Clientes getClienteByCedula(String cedula){
       Conexion con = new Conexion();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(con.conectar());
        
        final Clientes cliente = new Clientes();
        String quer = "SELECT * FROM clientes WHERE cedula like '"+cedula+"'";
        return (Clientes) jdbcTemplate.query
        (
            quer, new ResultSetExtractor<Clientes>()
            {
                public Clientes extractData(ResultSet rs) throws SQLException, DataAccessException
                {
                    if(rs.next())
                    {
                        cliente.setNombre(rs.getString("nombre"));
                        cliente.setApellido(rs.getString("apellido"));
                        cliente.setCedula(rs.getString("cedula"));
                       
                    }
                    return cliente;

                }

            }
        );
        
        
    }
    
    
    
}
