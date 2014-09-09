package br.com.cast.treinamento.app.dao.mapping;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.provider.BaseColumns;
import br.com.cast.treinamento.app.domain.Contato;

public class ContatoEntity implements BaseColumns {

	public static final String TABELA = "tb_contato";

	public static final String COLUNA_ID = "id";
	public static final String COLUNA_NOME = "nome";
	public static final String COLUNA_ENDERECO = "endereco";
	public static final String COLUNA_SITE = "site";
	public static final String COLUNA_TELEFONE = "telefone";
	public static final String COLUNA_RELEVANCIA = "relevancia";

	public static final String[] COLUNAS = new String[] { COLUNA_ID, COLUNA_NOME, COLUNA_ENDERECO, COLUNA_SITE, COLUNA_TELEFONE, COLUNA_RELEVANCIA };

	private ContatoEntity() {
		super();
	}

	public static Contato bindContato(Cursor cursor) {
		if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
			Contato contato = new Contato();
			contato.setId(cursor.getLong(cursor.getColumnIndex(COLUNA_ID)));
			contato.setNome(cursor.getString(cursor.getColumnIndex(COLUNA_NOME)));
			contato.setEndereco(cursor.getString(cursor.getColumnIndex(COLUNA_ENDERECO)));
			contato.setSite(cursor.getString(cursor.getColumnIndex(COLUNA_SITE)));
			contato.setTelefone(cursor.getString(cursor.getColumnIndex(COLUNA_TELEFONE)));
			int indceRelevancia = cursor.getColumnIndex(COLUNA_RELEVANCIA);

			if (!cursor.isNull(indceRelevancia)) {
				contato.setRelevancia(cursor.getFloat(indceRelevancia));
			}
			return contato;
		}
		return null;
	}

	public static List<Contato> bindContatos(Cursor cursor) {
		List<Contato> contatos = new ArrayList<Contato>();

		while (cursor.moveToNext()) {
			contatos.add(ContatoEntity.bindContato(cursor));
		}

		return contatos;
	}

}
