package org.contato.dao;

import java.lang.Long;
import org.contato.dao.ContatoDao;
import org.contato.entity.Contato;
import org.sharkness.business.support.ModelDao;
import org.springframework.stereotype.Repository;

@SuppressWarnings("serial")
@Repository(value="contatoDao")
public class ContatoDao extends ModelDao<Long, Contato> {

}