package br.com.cast.treinamento.app.dao;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.cast.treinamento.app.dao.mapping.ContatoEntity;
import br.com.cast.treinamento.app.domain.Contato;

public final class ContatoDao extends BaseDAO {

	private static ContatoDao INSTANCIA;

	private ContatoDao(Context contexto) {
		super(contexto);
	}

	public static ContatoDao getInstancia(Context contexto) {
		if (INSTANCIA == null) {
			INSTANCIA = new ContatoDao(contexto);
		}
		return INSTANCIA;
	}

	public List<Contato> listarTodos() throws SQLException {
		try {
			SQLiteDatabase db = super.getReadableDatabase();
			String orderBy = String.format("UPPER(%s)", ContatoEntity.COLUNA_NOME);
			Cursor cursor = db.query(ContatoEntity.TABELA, ContatoEntity.COLUNAS, null, null, null, null, orderBy);
			return ContatoEntity.bindContatos(cursor);
		} catch (SQLException e) {
			Log.e("DAO", e.getMessage());
			throw e;
		} finally {
			super.close();
		}
	}

	public void salvar(Contato contato) {
		SQLiteDatabase db = super.getWritableDatabase();
		try {
			db.beginTransaction();
			ContentValues values = new ContentValues();
			values.put(ContatoEntity.COLUNA_NOME, contato.getNome());
			values.put(ContatoEntity.COLUNA_ENDERECO, contato.getEndereco());
			values.put(ContatoEntity.COLUNA_SITE, contato.getSite());
			values.put(ContatoEntity.COLUNA_TELEFONE, contato.getTelefone());

			if (contato.getRelevancia().floatValue() == 0) {
				values.putNull(ContatoEntity.COLUNA_RELEVANCIA);
			} else {
				values.put(ContatoEntity.COLUNA_RELEVANCIA, contato.getRelevancia());
			}

			if (contato.getId() == null) {
				db.insert(ContatoEntity.TABELA, null, values);
			} else {
				String[] argumentos = new String[] { contato.getId().toString() };
				String clausulaWhere = ContatoEntity.COLUNA_ID + " = ?";
				db.update(ContatoEntity.TABELA, values, clausulaWhere, argumentos);
			}
			db.setTransactionSuccessful();

		} catch (SQLException e) {
			Log.e("DAO", e.getMessage());
			throw e;
		} finally {
			db.endTransaction();
			super.close();
		}
	}

	public void excluir(Contato contato) {
		SQLiteDatabase db = super.getWritableDatabase();
		try {
			db.beginTransaction();
			// OPÇÃO 1:db.delete
			String clausulaWhere = ContatoEntity.COLUNA_ID + " = ?";
			String[] argumentos = new String[] { contato.getId().toString() };
			db.delete(ContatoEntity.TABELA, clausulaWhere, argumentos);
			// OPÇÃO 2:db.compileStatement
			// String sqlDelete = String.format("DELETE FROM %s WHERE %s = ?", ContatoEntity.TABELA, ContatoEntity.COLUNA_ID);
			// SQLiteStatement comandoSQL = db.compileStatement(sqlDelete);
			// comandoSQL.bindLong(1, contato.getId());
			// comandoSQL.execute();

			db.setTransactionSuccessful();
		} catch (SQLException e) {
			Log.e("DAO", e.getMessage());
			throw e;
		} finally {
			db.endTransaction();
			super.close();
		}
	}

	public List<Contato> listarContatoPor(Contato contato) {
		SQLiteDatabase db = super.getReadableDatabase();
		try {
			String sql = String.format("SELECT * FROM %s WHERE %s = ? || %s = ? ", ContatoEntity.TABELA, ContatoEntity.COLUNA_NOME,
					ContatoEntity.COLUNA_TELEFONE);
			String[] argumentos = new String[] { contato.getNome().toString(), contato.getTelefone().toString() };
			Cursor cursor = db.rawQuery(sql, argumentos);
			return ContatoEntity.bindContatos(cursor);
		} catch (SQLException e) {
			Log.e("DAO", e.getMessage());
			throw e;
		} finally {
			super.close();
		}
	}

}
