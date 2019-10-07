/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.Tickets;
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
public class TicketsDAO {
    
    public static List<Tickets> listTickets(){
        List<Tickets> lst = null;
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            String hql = "from Tickets order by id desc";
            Query query = session.createQuery(hql);
            lst = query.list();
            
            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return lst;
    }
    
    
    public static Tickets nextTicket(String dep){
        List<Tickets> lst = null;  
        Tickets p = null;
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            String hql = "from Tickets where status like 'ABIERTO' and departamento like '"+dep+"' order by id asc";
            Query query = session.createQuery(hql);
            lst = query.list();
            if(lst.isEmpty() || lst == null){
                session.close();
                return null;
            }else{
                p = lst.get(0);
                session.close();
            }
           
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return p;
    }
    
    public static void insertTicket(Tickets ticket){
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.save(ticket);
            tx.commit();

            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public static void deleteTicket(Tickets ticket){
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.delete(ticket);
            tx.commit();

            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public static void updateTicket(Tickets ticket){
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.update(ticket);
            tx.commit();

            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public static Tickets selectTicket(int id){
        List<Tickets> lst = null;  
        Tickets p = null;
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            String hql = "from Tickets where id ="+id;
            Query query = session.createQuery(hql);
            lst = query.list();
             if(lst.isEmpty() || lst == null){
                session.close();
                return null;
            }else{
                p = lst.get(0);
                session.close();
            }
         
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return p;
    }
    
      public static List<Tickets> buscarTickets(String busqueda){
        List<Tickets> lst = null;  
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            String hql = "from Tickets where turno like '"+busqueda+"' or servicio like '%"+busqueda+"%' or nombre_cliente like '%"+busqueda+"%' or apellido_cliente like '%"+busqueda+"%' or cedula_cliente like '"+busqueda+"' or status like '%"+busqueda+"%' or departamento like '%"+busqueda+"%'";
            Query query = session.createQuery(hql);
            lst = query.list();
            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return lst;
    }
      
      
    public static Tickets consultarTicket(int id){
    
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session sesion = sf.openSession();
        
        Tickets t = (Tickets)sesion.get(Tickets.class, id);
        
    
        return t;
    }
    
    
}
