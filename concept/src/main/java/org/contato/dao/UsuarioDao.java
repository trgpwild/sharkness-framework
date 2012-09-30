package org.contato.dao;

import java.lang.Long;
import org.contato.dao.UsuarioDao;
import org.contato.entity.Usuario;
import org.sharkness.business.support.ModelDao;
import org.springframework.stereotype.Repository;

@SuppressWarnings("serial")
@Repository(value="usuarioDao")
public class UsuarioDao extends ModelDao<Long, Usuario> {

}