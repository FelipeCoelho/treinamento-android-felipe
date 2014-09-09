package br.com.cast.treinamento.app;

import br.com.cast.treinamento.app.domain.Contato;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {

	private View btn_ok;
	private Contato contatoSelecionado;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btn_ok = findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				contatoSelecionado = new Contato();
				contatoSelecionado.setNome(((EditText) findViewById(R.id.text_nome)).getText().toString());
				contatoSelecionado.setTelefone(((EditText) findViewById(R.id.text_tel)).getText().toString());
				Intent intent = new Intent(MainActivity.this, ListaContatosActivity.class);
				intent.putExtra(ListaContatosActivity.CHAVE_CONTATO, contatoSelecionado);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
