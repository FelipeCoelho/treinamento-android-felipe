package br.com.cast.treinamento.app.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public abstract class BaseDAO extends SQLiteOpenHelper {

	private static final String TAG = "DAO";
	private static final String DB_NAME = "TreinamentoAndroid.db";
	private static final int DB_VERSION = 2;

	private Context contexto;

	public BaseDAO(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.contexto = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createSQL = "create.sql";
		try {
			executarScriptAsset(db, createSQL);
		} catch (IOException e) {
			String create = "CREATE TABLE tb_contato (id INTEGER PRIMARY KEY,nome TEXT UNIQUE NOT NULL,endereco TEXT UNIQUE NOT NULL, site TEXT NOT NULL,telefone TEXT NOT NULL,relevancia REAL);";
			db.execSQL(create);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String createSQL = "drops.sql";
		try {
			executarScriptAsset(db, createSQL);
		} catch (Exception e) {
			String drop = "DROP TABLE IF EXISTS tb_contato;";
			db.execSQL(drop);
		}
		this.onCreate(db);
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		this.onUpgrade(db, oldVersion, newVersion);
	}

	private void executarScriptAsset(SQLiteDatabase db, String createSQL) throws IOException, IOException {
		db.beginTransaction();
		try {
			InputStream is = contexto.getAssets().open(createSQL);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String linha;
			while ((linha = br.readLine()) != null) {
				SQLiteStatement statement = db.compileStatement(linha);
				statement.execute();
			}
			db.setTransactionSuccessful();
		} catch (IOException e) {
			e.printStackTrace();
			Log.i(TAG, "i/O: " + e.getMessage());
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			Log.i(TAG, "SQL: " + e.getMessage());
			throw e;
		} finally {
			db.endTransaction();
		}
	}

}
