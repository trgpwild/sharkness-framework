package org.contato.dao;

import java.lang.Long;
import org.contato.dao.PerfilDao;
import org.contato.entity.Perfil;
import org.sharkness.business.support.ModelDao;
import org.springframework.stereotype.Repository;

@SuppressWarnings("serial")
@Repository(value="perfilDao")
public class PerfilDao extends ModelDao<Long, Perfil> {

}