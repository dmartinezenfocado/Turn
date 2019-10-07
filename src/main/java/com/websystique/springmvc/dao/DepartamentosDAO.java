/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.Departamentos;
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
public class DepartamentosDAO {
    
     public static List<Departamentos> listDepartamentos(){
        List<Departamentos> lst = null;
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            String hql = "from Departamentos";
            Query query = session.createQuery(hql);
            lst = query.list();
            
            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return lst;
    }
    
    public static void insertDepartamentos(Departamentos departamentos){
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.save(departamentos);
            tx.commit();

            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public static void deleteDepartamentos(Departamentos departamentos){
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.delete(departamentos);
            tx.commit();

            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public static void updateDepartamentos(Departamentos departamentos){
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.update(departamentos);
            tx.commit();

            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public static Departamentos selectDepartamentos(int id){
        List<Departamentos> lst = null;  
        Departamentos p = null;
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            String hql = "from Departamentos where id ="+id;
            Query query = session.createQuery(hql);
            lst = query.list();
            p = lst.get(0);
            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return p;
    }
    
    public static List<Departamentos> buscarDepartamentos(String busqueda){
        List<Departamentos> lst = null;  
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            String hql = "from Departamentos where departamento like '%"+busqueda+"%' or prefijo like '%"+busqueda+"%' ";
            Query query = session.createQuery(hql);
            lst = query.list();
            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return lst;
    }
      
      
    public static Departamentos consultarDepartamento(int id){
    
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session sesion = sf.openSession();
        
        Departamentos t = (Departamentos)sesion.get(Departamentos.class, id);
        
    
        return t;
    }
    
    
    public static String getPrefijo(String departamento){
        List<Departamentos> lst = null;  
        Departamentos p = null;
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            String hql = "from Departamentos where departamento like '"+departamento+"'";
            Query query = session.createQuery(hql);
            lst = query.list();
            p = lst.get(0);
            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return p.getPrefijo();
    }
    
    
    
}
