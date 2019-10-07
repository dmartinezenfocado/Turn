/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.websystique.springmvc.controller;

import com.websystique.springmvc.dao.DepartamentosDAO;
import com.websystique.springmvc.model.Departamentos;

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
import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;




@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class DepartamentoController {

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
        
        
        @RequestMapping(value = "/depview" ,method=RequestMethod.GET)
        public ModelAndView showDepartamentos(HttpServletRequest request) throws Exception {
           ModelAndView mv = new ModelAndView("depview");

           try{
       
               PagedListHolder pagedListHolder = new PagedListHolder(DepartamentosDAO.listDepartamentos());
 
               int page = ServletRequestUtils.getIntParameter(request, "p", 0);
               pagedListHolder.setPage(page);
               pagedListHolder.setPageSize(10);
            
               mv.addObject("pagedListHolder",pagedListHolder);
       
           }catch(Exception e){
               e.printStackTrace();
           }

           return mv;
        }
        
        
          @RequestMapping(value = "/depadd",method=RequestMethod.GET)
          public ModelAndView addDepartamentoView()
          {
                ModelAndView mav = new ModelAndView();
                mav.setViewName("depadd");
                mav.addObject("dep", new Departamentos());
                return mav;

          }
          
          @RequestMapping(value = "/depadd",method=RequestMethod.POST)
          public ModelAndView addDepartamento(@ModelAttribute("dep") Departamentos p)
          {

                   DepartamentosDAO.insertDepartamentos(p);

                    return new ModelAndView("redirect:/depview");


          }
          
         @RequestMapping("deletedep")
        public ModelAndView deleteDepartamento(HttpServletRequest request)
        {
            Departamentos p = null;

            int id =Integer.parseInt(request.getParameter("id"));
            p = DepartamentosDAO.selectDepartamentos(id);

            DepartamentosDAO.deleteDepartamentos(p);

            return new ModelAndView("redirect:/depview");

        }  
            
        
        @RequestMapping(value ="/editdep",method=RequestMethod.GET)
        public ModelAndView editDepartamentoView(HttpServletRequest request)
        {
            ModelAndView mav = new ModelAndView();
            int id =Integer.parseInt(request.getParameter("id"));
            Departamentos datos = DepartamentosDAO.selectDepartamentos(id);
            mav.setViewName("editdep");
            mav.addObject("dep", new Departamentos(id,datos.getDepartamento(),datos.getPrefijo()));
            return mav;

        }                    


        @RequestMapping(value ="/editdep",method=RequestMethod.POST)
        public ModelAndView updateDepartamento(@ModelAttribute("dep") Departamentos p,HttpServletRequest request)
        {


                DepartamentosDAO.updateDepartamentos(p);

                 return new ModelAndView("redirect:/depview");


        }
   
  


}
