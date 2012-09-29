package org.contato.service.impl;

import java.lang.Long;
import org.contato.dao.UsuarioDao;
import org.contato.entity.Usuario;
import org.contato.service.UsuarioService;
import org.sharkness.business.support.ModelServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("serial")
@Service(value = "usuarioService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UsuarioServiceImpl extends ModelServiceImpl<Long, Usuario, UsuarioDao> implements UsuarioService {

}