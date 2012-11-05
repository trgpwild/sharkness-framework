package org.contato.controller;

import javax.faces.bean.ManagedBean;

import org.contato.entity.Tarefa;
import org.contato.service.TarefaService;
import org.sharkness.jsf.support.ModelController;

@ManagedBean
@SuppressWarnings("serial")
public class TarefaController extends ModelController<Long, Tarefa, TarefaService> {

}