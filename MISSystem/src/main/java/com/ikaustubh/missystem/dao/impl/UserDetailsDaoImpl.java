package com.ikaustubh.missystem.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.ikaustubh.missystem.entities.UserInfoEntity;
import com.ikaustubh.missystem.repository.UserInfoRepository;

@Repository
public class UserDetailsDaoImpl implements UserDetailsService {

	@Autowired
	UserInfoRepository userInfoRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    throw e;
		}
	}
}
