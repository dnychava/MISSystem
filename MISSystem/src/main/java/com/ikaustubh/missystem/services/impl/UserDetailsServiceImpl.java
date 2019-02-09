package com.ikaustubh.missystem.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ikaustubh.missystem.entities.RoleEntity;
import com.ikaustubh.missystem.entities.UserInfoEntity;
import com.ikaustubh.missystem.repository.UserInfoRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserInfoRepository userInfoRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInfoEntity userInfo = userInfoRepository.findByUsername(username);
	
		List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();
		/*Set<RoleEntity> roles = userInfo.getRoles();
		System.out.println("************roles*******["+roles+"]");
		for (RoleEntity role : roles) {
			GrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleTypeName());
			grantedAuthorityList.add(authority );
		}*/
		GrantedAuthority authority = new SimpleGrantedAuthority("admin");
		grantedAuthorityList.add(authority);
		User user = new User(userInfo.getUsername(), userInfo.getPassword(), grantedAuthorityList);
		return (UserDetails) user;
	}
}
