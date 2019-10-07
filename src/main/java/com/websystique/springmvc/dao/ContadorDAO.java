/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.Contador;
import com.websystique.springmvc.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author delvinmartinez
 */
public class ContadorDAO {
    
   
     
    public static void deleteContador(Contador contador){
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.delete(contador);
            tx.commit();

            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public static void updateContador(Contador contador){
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.update(contador);
            tx.commit();

            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public static void resetContador(){
        Contador contador = new Contador();
        contador.setId(1);
        contador.setNumero(1);
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.update(contador);
            tx.commit();

            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public static Contador selectContador(){
        List<Contador> lst = null;  
        Contador p = null;
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            String hql = "from Contador";
            Query query = session.createQuery(hql);
            lst = query.list();
            p = lst.get(0);
            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return p;
    }
    
    public static List<Contador> buscarContador(String busqueda){
        List<Contador> lst = null;  
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            String hql = "from Contador where turno like '%"+busqueda+"%' or servicio like '%"+busqueda+"%' or nombre_cliente like '%"+busqueda+"%' or apellido_cliente like '%"+busqueda+"%' or cedula_cliente like '%"+busqueda+"%' or status like '%"+busqueda+"%'";
            Query query = session.createQuery(hql);
            lst = query.list();
            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return lst;
    }
      
      
    public static Contador consultarContador(int id){
    
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session sesion = sf.openSession();
        
        Contador t = (Contador)sesion.get(Contador.class, id);
        
    
        return t;
    }
    
    
}
