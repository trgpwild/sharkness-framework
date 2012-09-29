package org.contato.dao;

import java.lang.Long;
import org.contato.dao.TarefaDao;
import org.contato.entity.Tarefa;
import org.sharkness.business.support.ModelDao;
import org.springframework.stereotype.Repository;

@SuppressWarnings("serial")
@Repository(value="tarefaDao")
public class TarefaDao extends ModelDao<Long, Tarefa> {

}