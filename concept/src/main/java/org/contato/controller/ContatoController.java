package org.contato.controller;

import javax.faces.bean.ManagedBean;

import org.contato.entity.Contato;
import org.contato.service.ContatoService;
import org.sharkness.jsf.support.ModelController;

@ManagedBean
@SuppressWarnings("serial")
public class ContatoController extends ModelController<Long, Contato, ContatoService> {

}