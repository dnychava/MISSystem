package com.ikaustubh.missystem.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikaustubh.missystem.entities.UserInfoEntity;
import com.ikaustubh.missystem.repository.UserInfoRepository;

//@CrossOrigin(exposedHeaders="Access-Control-Allow-Origin")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	UserInfoRepository userInfoRepository;
	
	@PostMapping(path="/login")
	public String authincateUser(Principal principal) throws JsonProcessingException {	
		UserInfoEntity userInfo = userInfoRepository.findByUsername(principal.getName());
		//System.out.println("Employee["+userInfo.getEmployeeEntity()+"]");
		//System.out.println("Unit["+userInfo.getUnitEntity()+"]");
		String userInfoJSON = new ObjectMapper().writeValueAsString(userInfo);
		
		return userInfoJSON;
	}
	@PostMapping(path="/logout")
	public String lougoutLoggedUser(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CookieClearingLogoutHandler cookieClearingLogoutHandler = new CookieClearingLogoutHandler(AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY);
	    SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
	    
	    if (auth != null){    
	    	cookieClearingLogoutHandler.logout(request, response, null);
		    securityContextLogoutHandler.logout(request, response, null);
	    }
	    String redirectURLJSON = new ObjectMapper().writeValueAsString("/login");
	    return redirectURLJSON ;//You can redirect wherever you want, but generally it's a good practice to show login screen again.
	}
	
	/*@GetMapping(path="/auth/user")
	//@RequestMapping(value="/user", method=RequestMethod.GET)
	public ResponseEntity<?> getCurrentLoggedUser( HttpServletRequest req, HttpServletResponse res ) {
		HttpSession session =  req.getSession();;
		UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
		return new ResponseEntity<UserInfo>(userInfo, HttpStatus.OK);
	}*/

}