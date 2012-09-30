package org.contato.service.impl;

import java.lang.Long;
import org.contato.dao.TarefaDao;
import org.contato.entity.Tarefa;
import org.contato.service.TarefaService;
import org.sharkness.business.support.ModelServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("serial")
@Service(value = "tarefaService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TarefaServiceImpl extends ModelServiceImpl<Long, Tarefa, TarefaDao> implements TarefaService {

}