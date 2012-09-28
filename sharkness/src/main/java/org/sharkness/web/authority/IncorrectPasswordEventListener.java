package org.sharkness.web.authority;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.sharkness.business.entity.User;
import org.sharkness.business.factory.PropertiesFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SuppressWarnings({ "unchecked", "rawtypes" })
public class IncorrectPasswordEventListener implements ApplicationListener {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void onApplicationEvent(ApplicationEvent event) {

		try {
			
			String hql = PropertiesFactory.getDatabaseUsernameHQL();

			if (event instanceof AbstractAuthenticationEvent) {
				
				Query query = sessionFactory.getCurrentSession().createQuery(hql);

				query.setString(0, ((AbstractAuthenticationEvent)event).getAuthentication().getName());
				
				List<User<?>> results = query.list();

				if (results != null && results.size() > 0) {
					
					User user = results.get(0);
					
					if (event instanceof AuthenticationFailureBadCredentialsEvent) {
						
						user.setTries(user.getTries() + 1);
						
					} else if (event instanceof AuthenticationSuccessEvent) {
						
						user.setTries(0);
						
					}
					
					sessionFactory.getCurrentSession().update(user);
					
				}
				
			}
			
		} catch (Exception e) {
			
			System.out.println(new StringBuilder("Error on authentication: ").append(e.getMessage()).toString());
			
		}
		
	}

}