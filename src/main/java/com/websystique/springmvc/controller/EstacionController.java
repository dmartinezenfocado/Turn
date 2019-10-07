/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.websystique.springmvc.controller;


import com.websystique.springmvc.dao.DepartamentosDAO;
import com.websystique.springmvc.dao.EstacionesDAO;
import com.websystique.springmvc.model.Departamentos;
import com.websystique.springmvc.model.Estaciones;

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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;




@Controller
@RequestMapping("/")
@SessionAttributes("roles")
    public class EstacionController {

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
        
        
        @RequestMapping(value = "/estview" ,method=RequestMethod.GET)
        public ModelAndView showEstaciones(HttpServletRequest request) throws Exception {
           ModelAndView mv = new ModelAndView("estview");

           try{
       
               PagedListHolder pagedListHolder = new PagedListHolder(EstacionesDAO.listEstaciones());
 
               int page = ServletRequestUtils.getIntParameter(request, "p", 0);
               pagedListHolder.setPage(page);
               pagedListHolder.setPageSize(10);
            
               mv.addObject("pagedListHolder",pagedListHolder);
       
           }catch(Exception e){
               e.printStackTrace();
           }

           return mv;
        }
        
        
        @RequestMapping(value = "/addest",method=RequestMethod.GET)
          public ModelAndView addEstacionView()
          {
                ModelAndView mav = new ModelAndView();
                mav.setViewName("addest");
                mav.addObject("est", new Estaciones());
                return mav;

          }
          
          @RequestMapping(value = "/addest",method=RequestMethod.POST)
          public ModelAndView addServicio(@ModelAttribute("est") Estaciones p)
          {
                   p.setUsuarioFk(0);
                   p.setEstatus("NO");
                   EstacionesDAO.insertEstacion(p);

                    return new ModelAndView("redirect:/estview");


          }
          
          
        @RequestMapping("deleteest")
        public ModelAndView deleteEstacion(HttpServletRequest request)
        {
            Estaciones p = null;

            int id =Integer.parseInt(request.getParameter("id"));
            p = EstacionesDAO.selectEstacionByID(id);

            EstacionesDAO.deleteEstacion(p);

            return new ModelAndView("redirect:/estview");

        } 
        
        
         @RequestMapping(value ="/editest",method=RequestMethod.GET)
        public ModelAndView editEstacionView(HttpServletRequest request)
        {
            ModelAndView mav = new ModelAndView();
            int id =Integer.parseInt(request.getParameter("id"));
            Estaciones datos = EstacionesDAO.selectEstacionByID(id);
            mav.setViewName("editest");
            mav.addObject("est", new Estaciones(id,datos.getEstacion(),datos.getDepartamento()));
            return mav;

        }                    


        @RequestMapping(value ="/editest",method=RequestMethod.POST)
        public ModelAndView updateEstaciones(@ModelAttribute("est") Estaciones p,HttpServletRequest request)
        {

                p.setEstatus("NO");
                p.setUsuarioFk(0);
                EstacionesDAO.updateEstacion(p);

                 return new ModelAndView("redirect:/estview");


        }
            
          
          @ModelAttribute("depLista")
        public Map<String,String> listaDepartamentos()
        {
            Map<String,String> lst = new LinkedHashMap<>();
            
            
            List<Departamentos> lsts = DepartamentosDAO.listDepartamentos();
            for (Iterator<Departamentos> i = lsts.iterator(); i.hasNext();) {
                Departamentos item = i.next();
                
                lst.put(item.getDepartamento(), item.getDepartamento());
                
                
            }

            return lst;
        }
   
  


}
