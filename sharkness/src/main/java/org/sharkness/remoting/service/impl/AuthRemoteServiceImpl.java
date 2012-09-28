package org.sharkness.remoting.service.impl;

import java.util.List;

import org.sharkness.remoting.service.AuthRemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.rcp.RemoteAuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@SuppressWarnings("unchecked")
public class AuthRemoteServiceImpl implements AuthRemoteService {

	@Autowired
	private RemoteAuthenticationManager remoteAuthenticationManager;
	
	public AuthRemoteServiceImpl(RemoteAuthenticationManager remoteAuthenticationManager) {
		this.remoteAuthenticationManager = remoteAuthenticationManager;
	}

	@Override
	public Object login(String user, String pass) {

		try {
			
	        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_GLOBAL);
			List<GrantedAuthority> authorities = (List<GrantedAuthority>) remoteAuthenticationManager.attemptAuthentication(user, pass);
	        SecurityContextHolder.getContext().setAuthentication(
	        	new UsernamePasswordAuthenticationToken(user, pass, authorities)
	        );
	        return SecurityContextHolder.getContext().getAuthentication();
	        
		} catch (Exception e) {
			
			return e;
			
		}
		
	}

}