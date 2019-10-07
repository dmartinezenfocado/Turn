package com.websystique.springmvc.dao;

import com.websystique.springmvc.model.Estaciones;
import com.websystique.springmvc.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


public class EstacionesDAO {
    
    public static List<Estaciones> listEstaciones(){
        List<Estaciones> lst = null;
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            String hql = "from Estaciones";
            Query query = session.createQuery(hql);
            lst = query.list();
            
            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return lst;
    }
    
     public static List<Estaciones> selectEstacionesNoAssignadas(){
        List<Estaciones> lst = null;
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            String hql = "from Estaciones where estatus like 'NO'";
            Query query = session.createQuery(hql);
            lst = query.list();
            
            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return lst;
    }
     
     
    
    public static void insertEstacion(Estaciones estaciones){
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.save(estaciones);
            tx.commit();

            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public static void deleteEstacion(Estaciones estaciones){
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.delete(estaciones);
            tx.commit();

            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public static void updateEstacion(Estaciones estaciones){
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.update(estaciones);
            tx.commit();

            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public static Estaciones selectEstacionByEstacion(String estacion){
        List<Estaciones> lst = null;  
        Estaciones p = null;
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            String hql = "from Estaciones where estacion like'"+estacion+"'";
            Query query = session.createQuery(hql);
            lst = query.list();
            p = lst.get(0);
            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return p;
    }
    
    public static String selectDepartamentoByEstacion(String estacion){
        List<Estaciones> lst = null;  
        Estaciones p = null;
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            String hql = "from Estaciones where estacion like'"+estacion+"'";
            Query query = session.createQuery(hql);
            lst = query.list();
            p = lst.get(0);
            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return p.getDepartamento();
    }
    
       public static String selectDepartamentoByUserFk(int usuariofk){
        List<Estaciones> lst = null;  
        Estaciones p = null;
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            String hql = "from Estaciones where usuario_fk ="+usuariofk;
            Query query = session.createQuery(hql);
            lst = query.list();
            p = lst.get(0);
            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return p.getDepartamento();
    }
    
    
       public static Estaciones selectEstacionByID(int id){
        List<Estaciones> lst = null;  
        Estaciones p = null;
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            String hql = "from Estaciones where id ="+id;
            Query query = session.createQuery(hql);
            lst = query.list();
            p = lst.get(0);
            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return p;
    }
       
       
    public static String selectEstacionByUserFk(int usuariofk){
        List<Estaciones> lst = null;  
        Estaciones p = null;
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            String hql = "from Estaciones where usuario_fk ="+usuariofk;
            Query query = session.createQuery(hql);
            lst = query.list();
            p = lst.get(0);
            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return p.getEstacion();
    }
    
     public static Estaciones selectEstacionByUser(int usuariofk){
        List<Estaciones> lst = null;  
        Estaciones p = null;
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            String hql = "from Estaciones where usuario_fk ="+usuariofk;
            Query query = session.createQuery(hql);
            lst = query.list();
            if(lst.isEmpty() || lst == null){
                session.close();
                return null;
            }
            p = lst.get(0);
            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return p;
    }
    
      public static List<Estaciones> buscarEstaciones(String busqueda){
        List<Estaciones> lst = null;  
        
        try{
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            String hql = "from Estaciones where estacion like '%"+busqueda+"%' or departamento like '%"+busqueda+"%'";
            Query query = session.createQuery(hql);
            lst = query.list();
            session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return lst;
    }
      
      
    public static Estaciones consultarEstacion(int id){
    
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session sesion = sf.openSession();
        
        Estaciones t = (Estaciones)sesion.get(Estaciones.class, id);
        
    
        return t;
    }
    
}
