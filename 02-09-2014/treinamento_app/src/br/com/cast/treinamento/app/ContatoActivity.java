package br.com.cast.treinamento.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import br.com.cast.treinamento.app.entite.Contato;
import br.com.cast.treinamento.app.service.ContatoService;

public class ContatoActivity extends ActionBarActivity {

	private EditText txtNome;
	private EditText txtEndereco;
	private EditText txtSite;
	private EditText txtTelefone;
	private RatingBar ratingBarRelevancia;
	private Button btnSalvar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contato);
		bindingElementosLayout();
		criaBotaoSalvar();

	}

	private void criaBotaoSalvar() {
		btnSalvar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Contato contato = new Contato();
				contato.setNome(txtNome.getText().toString());
				contato.setEndereco(txtEndereco.getText().toString());
				contato.setSite(txtSite.getText().toString());
				contato.setTelefone(txtTelefone.getText().toString());
				contato.setRelevancia(ratingBarRelevancia.getRating());
				ContatoService.getInstancia().salvar(contato);
				ContatoActivity.this.finish();
			}
		});
	}

	private void bindingElementosLayout() {
		txtNome = (EditText) findViewById(R.id.txtNome);
		txtEndereco = (EditText) findViewById(R.id.txtEnd);
		txtSite = (EditText) findViewById(R.id.txtSite);
		txtTelefone = (EditText) findViewById(R.id.txtTelefone);
		ratingBarRelevancia = (RatingBar) findViewById(R.id.ratingBarRelevancia);
		btnSalvar = (Button) findViewById(R.id.btnSalvar);
	}

}
