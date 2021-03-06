package org.contato.controller;

import javax.faces.bean.ManagedBean;

import org.contato.entity.Usuario;
import org.contato.service.UsuarioService;
import org.sharkness.jsf.support.ModelController;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;

@ManagedBean
@SuppressWarnings("serial")
public class UsuarioController extends ModelController<Long, Usuario, UsuarioService> {

	@Override
	protected Usuario modelProcessBeforeSave(Usuario model) {
		try {
			MessageDigestPasswordEncoder passwordEncoder = ctx().getBean(MessageDigestPasswordEncoder.class);
			Usuario usuario = model;
			if (usuario.getPassword() != null && usuario.getPassword().trim().length() > 0) {
				usuario.setPassword(passwordEncoder.encodePassword(usuario.getPassword(), null));
			} else {
				usuario.setPassword(getModelService().getById(usuario.getId()).getPassword());
			}
			return usuario;
		} catch (Exception e) {
			return model;
		}
	}

}