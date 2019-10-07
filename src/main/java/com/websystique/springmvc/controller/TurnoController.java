/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.websystique.springmvc.controller;

import com.websystique.springmvc.dao.EstacionesDAO;
import com.websystique.springmvc.dao.TicketsDAO;
import com.websystique.springmvc.dao.TurnosDAO;
import com.websystique.springmvc.model.Estaciones;
import com.websystique.springmvc.model.Tickets;
import com.websystique.springmvc.model.Turnos;
import com.websystique.springmvc.model.User;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
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
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;




@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class TurnoController {

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
        
        
        @RequestMapping(value = "/turnosview" ,method=RequestMethod.GET)
        public ModelAndView showTurnos(HttpServletRequest request,@ModelAttribute("estacion") Estaciones p) throws Exception {
           ModelAndView mv = new ModelAndView("turnosview");
           
           String msj = request.getParameter("msj");
           
           try{
               User user = userService.findBySSO(this.getPrincipal());
               PagedListHolder pagedListHolder = new PagedListHolder(TurnosDAO.selectTurnosByUserFk(user.getId()));
               List<Turnos> turnos = TurnosDAO.selectTurnosByUserFk(user.getId());
               
               if(!turnos.isEmpty() && turnos!=null){
                   Tickets ticket = TicketsDAO.selectTicket(turnos.get(0).getTicketFk());
                   if(ticket!=null){
                        mv.addObject("nombre",ticket.getNombreCliente()+" "+ticket.getApellidoCliente());
                        mv.addObject("cedula",ticket.getCedulaCliente());
                   }
               }
               

               int page = ServletRequestUtils.getIntParameter(request, "p", 0);
               pagedListHolder.setPage(page);
               pagedListHolder.setPageSize(10);
               if(msj != null){
                   mv.addObject("msj",msj);
               }
               mv.addObject("pagedListHolder",pagedListHolder);
       
           }catch(Exception e){
               e.printStackTrace();
           }

           return mv;
        }
        
        
        @RequestMapping(value = "/callnext",method=RequestMethod.GET)
        public ModelAndView callNext(HttpServletRequest request)
        {
            User user = userService.findBySSO(this.getPrincipal());
            List<Turnos> turnos = TurnosDAO.selectTurnosByUserFk(user.getId());
            
            if(turnos.isEmpty() || turnos == null){
                
                String departamento = EstacionesDAO.selectDepartamentoByUserFk(user.getId());
                
                Tickets ticket = TicketsDAO.nextTicket(departamento);
                if(ticket != null){
                    ticket.setStatus("ASIGNADO");
                    TicketsDAO.updateTicket(ticket);
                    Turnos newTurno = new Turnos();
                    newTurno.setTurno(ticket.getTurno());
                    newTurno.setEstacion(EstacionesDAO.selectEstacionByUserFk(user.getId()));
                    newTurno.setUsuarioFk(user.getId());
                    newTurno.setDuracion(System.currentTimeMillis()+"");
                    newTurno.setStatus("ABIERTO");
                    newTurno.setServicio(ticket.getServicio());
                    newTurno.setTicketFk(ticket.getId());

                    TurnosDAO.insertTurno(newTurno);
                    System.out.println("Se agrego un nuevo turno");
                }else{
                    System.out.println("No hay mas tickets para este departamento");
                    return new ModelAndView("redirect:/turnosview?msj=No hay mas tickets para este departamento");
                }
                
            }else{
                System.out.println("ya existe un turno");
                return new ModelAndView("redirect:/turnosview?msj=Debe completar el turno actual");
            }

            return new ModelAndView("redirect:/turnosview");

        }
        
        
         @RequestMapping(value = "/completarturno",method=RequestMethod.GET)
        public ModelAndView completarTurno(HttpServletRequest request)
        {    
            User user = userService.findBySSO(this.getPrincipal());
            Date date = new Date();
            int id =Integer.parseInt(request.getParameter("id"));
            Turnos turno = TurnosDAO.selectTurno(id);
            Tickets ticket = TicketsDAO.selectTicket(turno.getTicketFk());
            
            long start = Long.parseLong(turno.getDuracion());
            long end = System.currentTimeMillis();
            long res = end - start;
            int num = (int) (res/1000);
            int hor=num/3600;
            int min=(num-(3600*hor))/60;
            int seg=num-((hor*3600)+(min*60));
            String duracion = hor+":"+min+":"+seg+"";
            
            turno.setDuracion(duracion);
            turno.setStatus("CERRADO");
            ticket.setStatus("CERRADO");
            ticket.setFechaCierre(date);
            ticket.setUsuariofk(user.getId());
            
            TurnosDAO.updateTurnos(turno);
            TicketsDAO.updateTicket(ticket);
          

            return new ModelAndView("redirect:/turnosview");

        }
        
         @ModelAttribute("estacionesLista")
        public Map<String,String> listaEstaciones()
        {
            Map<String,String> lst = new LinkedHashMap<>();
            
            
            List<Estaciones> lsts = EstacionesDAO.selectEstacionesNoAssignadas();
            for (Iterator<Estaciones> i = lsts.iterator(); i.hasNext();) {
                Estaciones item = i.next();
                
                lst.put(item.getEstacion(), item.getEstacion());
                
                
            }

            return lst;
        }
        
            
   
  


}
