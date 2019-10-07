package com.websystique.springmvc.controller;

import com.websystique.springmvc.dao.EstacionesDAO;
import com.websystique.springmvc.dao.TicketsDAO;
import com.websystique.springmvc.dao.TurnosDAO;
import com.websystique.springmvc.model.Estaciones;
import com.websystique.springmvc.model.Tickets;
import com.websystique.springmvc.model.Turnos;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.websystique.springmvc.model.User;
import com.websystique.springmvc.model.UserProfile;
import com.websystique.springmvc.service.UserProfileService;
import com.websystique.springmvc.service.UserService;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class AppController {

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
	
	
	
	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String index(ModelMap model) {
		model.addAttribute("loggedinuser", getPrincipal());
		return "index";
	}

        /**
	 * This method will list all existing users.
	 */
	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public String listUsers(ModelMap model) {

		List<User> users = userService.findAllUsers();
		model.addAttribute("users", users);
		model.addAttribute("loggedinuser", getPrincipal());
		return "userslist";
	}
	/**
	 * This method will provide the medium to add a new user.
	 */
	@RequestMapping(value = { "/newuser" }, method = RequestMethod.GET)
	public String newUser(ModelMap model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("edit", false);
		model.addAttribute("loggedinuser", getPrincipal());
		return "registration";
	}

	/**
	 * This method will be called on form submission, handling POST request for
	 * saving user in database. It also validates the user input
	 */
	@RequestMapping(value = { "/newuser" }, method = RequestMethod.POST)
	public String saveUser(@Valid User user, BindingResult result,
			ModelMap model) {

		if (result.hasErrors()) {
			return "registration";
		}

		/*
		 * Preferred way to achieve uniqueness of field [sso] should be implementing custom @Unique annotation 
		 * and applying it on field [sso] of Model class [User].
		 * 
		 * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
		 * framework as well while still using internationalized messages.
		 * 
		 */
		if(!userService.isUserSSOUnique(user.getId(), user.getSsoId())){
			FieldError ssoError =new FieldError("user","ssoId",messageSource.getMessage("non.unique.ssoId", new String[]{user.getSsoId()}, Locale.getDefault()));
		    result.addError(ssoError);
			return "registration";
		}
		
		userService.saveUser(user);

		model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " registered successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		//return "success";
		return "registrationsuccess";
	}


	/**
	 * This method will provide the medium to update an existing user.
	 */
	@RequestMapping(value = { "/edit-user-{ssoId}" }, method = RequestMethod.GET)
	public String editUser(@PathVariable String ssoId, ModelMap model) {
		User user = userService.findBySSO(ssoId);
		model.addAttribute("user", user);
		model.addAttribute("edit", true);
		model.addAttribute("loggedinuser", getPrincipal());
		return "registration";
	}
	
	/**
	 * This method will be called on form submission, handling POST request for
	 * updating user in database. It also validates the user input
	 */
	@RequestMapping(value = { "/edit-user-{ssoId}" }, method = RequestMethod.POST)
	public String updateUser(@Valid User user, BindingResult result,
			ModelMap model, @PathVariable String ssoId) {

		if (result.hasErrors()) {
			return "registration";
		}

		/*//Uncomment below 'if block' if you WANT TO ALLOW UPDATING SSO_ID in UI which is a unique key to a User.
		if(!userService.isUserSSOUnique(user.getId(), user.getSsoId())){
			FieldError ssoError =new FieldError("user","ssoId",messageSource.getMessage("non.unique.ssoId", new String[]{user.getSsoId()}, Locale.getDefault()));
		    result.addError(ssoError);
			return "registration";
		}*/


		userService.updateUser(user);

		model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " updated successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		return "registrationsuccess";
	}

	
	/**
	 * This method will delete an user by it's SSOID value.
	 */
	@RequestMapping(value = { "/delete-user-{ssoId}" }, method = RequestMethod.GET)
	public String deleteUser(@PathVariable String ssoId) {
		userService.deleteUserBySSO(ssoId);
		return "redirect:/list";
	}
	

	/**
	 * This method will provide UserProfile list to views
	 */
	@ModelAttribute("roles")
	public List<UserProfile> initializeProfiles() {
		return userProfileService.findAll();
	}
	
	/**
	 * This method handles Access-Denied redirect.
	 */
	@RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
	public String accessDeniedPage(ModelMap model) {
		model.addAttribute("loggedinuser", getPrincipal());
		return "accessDenied";
	}

	/**
	 * This method handles login GET requests.
	 * If users is already logged-in and tries to goto login page again, will be redirected to list page.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		if (isCurrentAuthenticationAnonymous()) {
			return "login";
	    } else {
	    	return "redirect:/list";  
	    }
	}

	/**
	 * This method handles logout requests.
	 * Toggle the handlers if you are RememberMe functionality is useless in your app.
	 */
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
			//new SecurityContextLogoutHandler().logout(request, response, auth);
                         
                        User user = userService.findBySSO(this.getPrincipal());
                        Estaciones estacion =  EstacionesDAO.selectEstacionByUser(user.getId());
                        
                        if(estacion != null){
                            estacion.setUsuarioFk(0);
                            estacion.setEstatus("NO");
                        
                            EstacionesDAO.updateEstacion(estacion);
                        }
			persistentTokenBasedRememberMeServices.logout(request, response, auth);
			SecurityContextHolder.getContext().setAuthentication(null);
		}
		return "redirect:/login?logout";
	}

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
        
        
        @RequestMapping(value = "/selectestacion",method=RequestMethod.GET)
        public ModelAndView selectEstacionView(HttpServletRequest request)
        {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("selectestacion");
            mav.addObject("estacion", new Estaciones());

            return mav;

        }
        
        @RequestMapping(value = "/selectestacion",method=RequestMethod.POST)
        public ModelAndView selectEstacion(@ModelAttribute("estacion") Estaciones p)
        {
            
            User user = userService.findBySSO(this.getPrincipal());
                Estaciones est =  EstacionesDAO.selectEstacionByUser(user.getId());
                        
                if(est != null){
                    est.setUsuarioFk(0);
                    est.setEstatus("NO");
                        
                    EstacionesDAO.updateEstacion(est);
                }
            
            Estaciones estacion =  EstacionesDAO.selectEstacionByEstacion(p.getEstacion());
           


                estacion.setUsuarioFk(user.getId());
                estacion.setEstatus("ASIGNADO");

                EstacionesDAO.updateEstacion(estacion);

                return new ModelAndView("redirect:/turnosview");
 
        }
        
        @RequestMapping(value = "/cerrarestacion",method=RequestMethod.GET)
        public ModelAndView closeEstacion(HttpServletRequest request)
        {
             User user = userService.findBySSO(this.getPrincipal());
                Estaciones est =  EstacionesDAO.selectEstacionByUser(user.getId());
                        
                if(est != null){
                    est.setUsuarioFk(0);
                    est.setEstatus("NO");
                        
                    EstacionesDAO.updateEstacion(est);
                }

            return new ModelAndView("redirect:/selectestacion");

        }
        
        
      
        
         @RequestMapping(value = "/pantalla" ,method=RequestMethod.GET)
        public ModelAndView mostrarPantalla(HttpServletRequest request) throws Exception {
           ModelAndView mv = new ModelAndView("pantalla");
           
           
           try{
               User user = userService.findBySSO(this.getPrincipal());
              
               List<Turnos> turnos = TurnosDAO.selectTurnosopen();
               
         
               mv.addObject("turnos",turnos);
       
           }catch(Exception e){
               e.printStackTrace();
           }

           return mv;
        }
        
        
         @RequestMapping(value = "/testajax" ,method=RequestMethod.GET)
        public ModelAndView testAjax(HttpServletRequest request) throws Exception {
           ModelAndView mv = new ModelAndView("testajax");
      
           return mv;
        }
        
         @RequestMapping(value = "/test1" ,method=RequestMethod.GET)
        public ModelAndView sinAjax(HttpServletRequest request) throws Exception {
           ModelAndView mv = new ModelAndView("testajax");
           
           
           try{
               User user = userService.findBySSO(this.getPrincipal());
              
               List<Turnos> turnos = TurnosDAO.selectTurnosopen();
               
         
               mv.addObject("turnos",turnos);
       
           }catch(Exception e){
               e.printStackTrace();
           }

           return mv;
        }
        
         @RequestMapping(value = "/test2" ,method=RequestMethod.GET)
        public ModelAndView conAjax(HttpServletRequest request) throws Exception {
           ModelAndView mv = new ModelAndView("p");
           
           
           try{
               User user = userService.findBySSO(this.getPrincipal());
              
               List<Turnos> turnos = TurnosDAO.selectTurnosopen();
               
         
               mv.addObject("turnos",turnos);
       
           }catch(Exception e){
               e.printStackTrace();
           }

           return mv;
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