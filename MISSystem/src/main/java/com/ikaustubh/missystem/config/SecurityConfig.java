package com.ikaustubh.missystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import com.ikaustubh.missystem.dao.impl.UserDetailsDaoImpl;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsDaoImpl userDetailsDaoImpl;	
	
	@Autowired
	private TopicAuthenticationEntryPoint authenticationEntryPoint;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class);
		
    	http
    		.authorizeRequests()
    		//.antMatchers("/master/*", "/expenditure/*").permitAll()
    		//.antMatchers("/auth/logout").permitAll()
            .antMatchers("/")
            .permitAll()
            .anyRequest()
            .fullyAuthenticated()
            /*.and()
                 "/logout" will log the user out by invalidating the HTTP Session,
                 * cleaning up any {link rememberMe()} authentication that was configured, */
                //.logout().permitAll().logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"))
            .and()
            	// enabling the basic authentication
            	.httpBasic()
            .and()
            	// configuring the session on the server
            	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            .and()
            	// disabling the CSRF - Cross Site Request Forgery
            .csrf().disable();
		
		
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		//auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("admin");
		//auth.inMemoryAuthentication().withUser("admin").password("{noop}$2a$10$hzq6g8gaeh.8B8LcFzuRRuxRrj/l0J.RLC9LITmsEq3DPodYIkkDy").roles("admin");
		//System.out.println("password encode="+NoOpPasswordEncoder.getInstance().encode("admin@123"));
		//System.out.println("password encode="+NoOpPasswordEncoder.getInstance().encode("user@123"));
		auth.userDetailsService(userDetailsDaoImpl).passwordEncoder(NoOpPasswordEncoder.getInstance());
	}
	
	/*@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}*/
}
