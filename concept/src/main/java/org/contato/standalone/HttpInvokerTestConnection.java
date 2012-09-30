package org.contato.standalone;

import java.util.HashMap;
import java.util.Locale;

import org.contato.entity.Contato;
import org.contato.entity.Usuario;
import org.contato.service.ContatoService;
import org.contato.service.UsuarioService;
import org.sharkness.remoting.factory.HttpInvokerFactory;
import org.sharkness.remoting.service.AuthRemoteService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class HttpInvokerTestConnection {

	public static void main(String[] args) throws Exception {
		
		HttpInvokerFactory factory = new HttpInvokerFactory(
			"http://localhost:8080/concept/service"
		);
		
		AuthRemoteService authRemoteService = factory.get(AuthRemoteService.class);
		
		UsuarioService usuarioService = factory.get(UsuarioService.class);
		
		ContatoService contatoService = factory.get(ContatoService.class);
		
		System.out.println("Test of UsuarioService without authentication...");
		
		try {
			System.out.println(usuarioService.list());
		} catch (Exception e) {
			System.err.println("No authenticated or access denied");
		}
		
		System.out.println("Authentication...");
		
		Object obj = authRemoteService.login("admin", "123");
		
		if (obj instanceof Authentication) {
			Authentication authentication = (Authentication)obj; 
			if (authentication != null) {
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		
		System.out.println("Test of UsuarioService...");
		
		for (Usuario u : usuarioService.list()) {
			System.out.println(u);
		}
		
		System.out.println("Test of ContatoService 1...");
		
		for (Contato c : contatoService.getListPagination(0, 1, null, null, new HashMap<String, String>(), Locale.getDefault())) {
			System.out.println(c);
		}
		
		System.out.println("Test of ContatoService 2...");

		for (Contato c : contatoService.getListPagination(1, 1, null, null, new HashMap<String, String>(), Locale.getDefault())) {
			System.out.println(c);
		}
		
	}

}
