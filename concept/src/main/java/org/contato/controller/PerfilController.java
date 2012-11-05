package org.contato.controller;

import javax.faces.bean.ManagedBean;

import org.contato.entity.Perfil;
import org.contato.service.PerfilService;
import org.sharkness.jsf.support.ModelController;

@ManagedBean
@SuppressWarnings("serial")
public class PerfilController extends ModelController<Long, Perfil, PerfilService> {

}