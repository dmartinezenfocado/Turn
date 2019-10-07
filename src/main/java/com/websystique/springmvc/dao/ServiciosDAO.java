/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.Servicios;
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
public class ServiciosDAO {
    
     public static List<Servicios> listServicios(){
        List<Servicios> lst = null;
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            String hql = "from Servicios";
            Query query = session.createQuery(hql);
            lst = query.list();
            
            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return lst;
    }
    
    public static void insertServicios(Servicios servicios){
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.save(servicios);
            tx.commit();

            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public static void deleteServicios(Servicios servicios){
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.delete(servicios);
            tx.commit();

            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public static void updateServicios(Servicios servicios){
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.update(servicios);
            tx.commit();

            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public static Servicios selectServicio(int id){
        List<Servicios> lst = null;  
        Servicios p = null;
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            String hql = "from Servicios where id ="+id;
            Query query = session.createQuery(hql);
            lst = query.list();
            p = lst.get(0);
            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return p;
    }
    
   
    
      public static List<Servicios> buscarServicios(String busqueda){
        List<Servicios> lst = null;  
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            String hql = "from Servicios where tipo_servicio like '%"+busqueda+"%' or departamento like '%"+busqueda+"%'";
            Query query = session.createQuery(hql);
            lst = query.list();
            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return lst;
    }
      
      
    public static Servicios consultarServicios(int id){
    
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session sesion = sf.openSession();
        
        Servicios t = (Servicios)sesion.get(Servicios.class, id);
        
    
        return t;
    }
    
    
     public static String getDepartamento(String tiposervicio){
        List<Servicios> lst = null;  
        Servicios p = null;
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            String hql = "from Servicios where tipo_servicio like '"+tiposervicio+"'";
            Query query = session.createQuery(hql);
            lst = query.list();
            p = lst.get(0);
            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return p.getDepartamento();
    }
    
}
