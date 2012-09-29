package org.contato.service.impl;

import java.lang.Long;
import org.contato.dao.ContatoDao;
import org.contato.entity.Contato;
import org.contato.service.ContatoService;
import org.sharkness.business.support.ModelServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("serial")
@Service(value = "contatoService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ContatoServiceImpl extends ModelServiceImpl<Long, Contato, ContatoDao> implements ContatoService {

}