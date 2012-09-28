package org.sharkness.web.authority;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.sharkness.business.entity.User;
import org.sharkness.business.factory.PropertiesFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SuppressWarnings("unchecked")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		
		try {
			
			String hql = PropertiesFactory.getDatabaseUsernameHQL();

			Query query = sessionFactory.getCurrentSession().createQuery(hql);

			query.setString(0, username);
			
			List<User<?>> results = query.list();
					
			if(results.size() < 1) throw new UsernameNotFoundException(username + "not found");
			
			User<?> user = results.get(0);
			
			if (!user.isAtivo()) {
				
				user.setEnabled(false);
				
			} else if (user.getTries() >= 3) {
				
				user.setAccountNonLocked(false);
				
			}
			
			return (UserDetails) user;
			
		} catch (Exception e) {
			
			return null;
			
		}
		
	}
	
}