package br.com.cast.treinamento.app.service;

import java.util.List;

import br.com.cast.treinamento.app.dao.ContatoDao;
import br.com.cast.treinamento.app.entite.Contato;

public final class ContatoService {

	private static final ContatoService INSTANCIA = new ContatoService();

	private ContatoService() {
		super();
	}

	public static ContatoService getInstancia() {
		return INSTANCIA;
	}

	public List<Contato> listarTodos() {
		return ContatoDao.getInstancia().listarTodos();
	}

	public void salvar(Contato contato) {
		ContatoDao.getInstancia().salvar(contato);
	}

}
