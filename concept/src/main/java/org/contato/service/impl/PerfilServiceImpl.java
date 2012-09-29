package org.contato.service.impl;

import java.lang.Long;
import org.contato.dao.PerfilDao;
import org.contato.entity.Perfil;
import org.contato.service.PerfilService;
import org.sharkness.business.support.ModelServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("serial")
@Service(value = "perfilService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PerfilServiceImpl extends ModelServiceImpl<Long, Perfil, PerfilDao> implements PerfilService {

}