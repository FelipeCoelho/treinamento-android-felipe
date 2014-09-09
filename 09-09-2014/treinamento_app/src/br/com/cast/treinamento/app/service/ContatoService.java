package br.com.cast.treinamento.app.service;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import br.com.cast.treinamento.app.R;
import br.com.cast.treinamento.app.dao.ContatoDao;
import br.com.cast.treinamento.app.domain.Contato;
import br.com.cast.treinamento.app.domain.exception.ExcecaoNegocio;

public final class ContatoService {

	private static ContatoService INSTANCIA;

	private Context contexto;

	private ContatoService(Context contexto) {
		this.contexto = contexto;
	}

	public static ContatoService getInstancia(Context contexto) {
		if (INSTANCIA == null) {
			INSTANCIA = new ContatoService(contexto);
		}
		return INSTANCIA;
	}

	public List<Contato> listarTodos() {
		return ContatoDao.getInstancia(contexto).listarTodos();
	}

	public void salvar(Contato contato) throws ExcecaoNegocio {
		ExcecaoNegocio excecao = new ExcecaoNegocio();
		if (TextUtils.isEmpty(contato.getNome())) {
			excecao.getMapaErros().put(R.id.txtNome, R.string.erro_preenchimento_obrigatrio);
		}
		if (TextUtils.isEmpty(contato.getEndereco())) {
			excecao.getMapaErros().put(R.id.txtEnd, R.string.erro_preenchimento_obrigatrio);
		}
		if (TextUtils.isEmpty(contato.getSite())) {
			excecao.getMapaErros().put(R.id.txtSite, R.string.erro_preenchimento_obrigatrio);
		}
		if (TextUtils.isEmpty(contato.getTelefone())) {
			excecao.getMapaErros().put(R.id.txtTelefone, R.string.erro_preenchimento_obrigatrio);
		}
		if (!excecao.getMapaErros().isEmpty()) {
			throw excecao;
		}
		ContatoDao.getInstancia(contexto).salvar(contato);
	}

	public void excluir(Contato contato) {
		ContatoDao.getInstancia(contexto).excluir(contato);
	}

	public List<Contato> listarContatoPor(Contato contato) {
		return ContatoDao.getInstancia(contexto).listarContatoPor(contato);
	}

}
