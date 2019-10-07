/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.websystique.springmvc.controller;

import com.websystique.springmvc.dao.ClientesDAO;
import com.websystique.springmvc.dao.ContadorDAO;
import com.websystique.springmvc.dao.DepartamentosDAO;
import com.websystique.springmvc.dao.ServiciosDAO;
import com.websystique.springmvc.dao.TicketsDAO;
import com.websystique.springmvc.model.Clientes;
import com.websystique.springmvc.model.Contador;
import com.websystique.springmvc.model.Servicios;
import com.websystique.springmvc.model.Tickets;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.websystique.springmvc.service.UserProfileService;
import com.websystique.springmvc.service.UserService;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class TicketController {

	@Autowired
	UserService userService;
	
	@Autowired
	UserProfileService userProfileService;
	
	@Autowired
	MessageSource messageSource;

	@Autowired
	PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;
	
	@Autowired
	AuthenticationTrustResolver authenticationTrustResolver;
	
        

        
	/**
	 * This method returns the principal[user-name] of logged-in user.
	 */
	private String getPrincipal(){
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}
	
	/**
	 * This method returns true if users is already authenticated [logged-in], else false.
	 */
	private boolean isCurrentAuthenticationAnonymous() {
	    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    return authenticationTrustResolver.isAnonymous(authentication);
	}
        
        
        @RequestMapping(value = "/ticketsview" ,method=RequestMethod.GET)
        public ModelAndView showTickets(HttpServletRequest request) throws Exception {
           ModelAndView mv = new ModelAndView("ticketsview");

           try{
               PagedListHolder pagedListHolder;
               String busqueda =request.getParameter("busqueda");


               if(busqueda == null){
                   pagedListHolder = new PagedListHolder(TicketsDAO.listTickets());
               }else if(busqueda!=null && busqueda.isEmpty()){
                   pagedListHolder = new PagedListHolder(TicketsDAO.listTickets());
               }else if(busqueda.equals("*")){
                      pagedListHolder = new PagedListHolder(TicketsDAO.listTickets());
               }else{
                   pagedListHolder = new PagedListHolder(TicketsDAO.buscarTickets(busqueda));
               }

               int page = ServletRequestUtils.getIntParameter(request, "p", 0);
               pagedListHolder.setPage(page);
               pagedListHolder.setPageSize(10);
               mv.addObject("pagedListHolder",pagedListHolder);
           }catch(Exception e){
               e.printStackTrace();
           }

           return mv;
        }
        
        
            
    @RequestMapping(value = "/addticket",method=RequestMethod.GET)
    public ModelAndView addTicketView(HttpServletRequest request)
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("addticket");
        mav.addObject("ticket", new Tickets());
 
        return mav;
       
    }
    
      @RequestMapping(value = "/addticket2",method=RequestMethod.POST)
    public ModelAndView addTicketView2(@ModelAttribute("ticket") Tickets p)
    {
        Clientes cliente = (Clientes) ClientesDAO.getClienteByCedula(p.getCedulaCliente());
        ModelAndView mav = new ModelAndView();
        mav.setViewName("addticket2");
        mav.addObject("ticket", p);
        mav.addObject("cedula", p.getCedulaCliente());
        mav.addObject("nombre", cliente.getNombre()+" "+cliente.getApellido());
 
        return mav;
        
    
    }
    
    @RequestMapping(value = "/verticket",method=RequestMethod.POST)
    public ModelAndView addTicket(@ModelAttribute("ticket") Tickets p)
    {
           int newContador;
           Clientes cliente = (Clientes) ClientesDAO.getClienteByCedula(p.getCedulaCliente());
           Contador contador = ContadorDAO.selectContador();
 
           Date date = new Date();
           String departamento = ServiciosDAO.getDepartamento(p.getServicio());
           String prefijo = DepartamentosDAO.getPrefijo(departamento);
           
           newContador =contador.getNumero()+1;
           contador.setNumero(newContador);
           ContadorDAO.updateContador(contador);
           
           p.setTurno(prefijo+"-"+newContador);
           p.setNombreCliente(cliente.getNombre());
           p.setApellidoCliente(cliente.getApellido());
           p.setStatus("ABIERTO");
           p.setFechaHora(date);
           p.setDepartamento(departamento);
           
           TicketsDAO.insertTicket(p);
                     
            return new ModelAndView("verticket");
        
    
    }
    
    @RequestMapping(value = "/ticketv",method=RequestMethod.GET)
    public ModelAndView verTicket(HttpServletRequest request)
    {
           ModelAndView mav = new ModelAndView();
           mav.setViewName("ticketv");
           int id =Integer.parseInt(request.getParameter("id"));
           Tickets ticket = TicketsDAO.selectTicket(id);
           mav.addObject("ticket", ticket);
           
                     
            return mav;
        
    
    }
    
    @RequestMapping("deleteticket")
    public ModelAndView deleteTicket(HttpServletRequest request)
    {
        Tickets p = null;
        
        int id =Integer.parseInt(request.getParameter("id"));
        p = TicketsDAO.selectTicket(id);
        
        TicketsDAO.deleteTicket(p);
        
        return new ModelAndView("redirect:/ticketsview");
       
    }  
    
    
    
 
        @ModelAttribute("serviciosLista")
        public Map<String,String> listaServicios()
        {
            Map<String,String> lst = new LinkedHashMap<>();
            
            
            List<Servicios> lsts = ServiciosDAO.listServicios();
            for (Iterator<Servicios> i = lsts.iterator(); i.hasNext();) {
                Servicios item = i.next();
                
                lst.put(item.getTipoServicio(), item.getTipoServicio());
                
                
            }

            return lst;
        }
        
  


}
