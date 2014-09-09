package br.com.cast.treinamento.app;

import java.util.Map.Entry;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import br.com.cast.treinamento.app.domain.Contato;
import br.com.cast.treinamento.app.domain.exception.ExcecaoNegocio;
import br.com.cast.treinamento.app.service.ContatoService;

public class ContatoActivity extends LifeCicleActivit {

	public static final String CHAVE_CONTATO = "CHAVE_CONTATO";
	private EditText txtNome;
	private EditText txtEndereco;
	private EditText txtSite;
	private EditText txtTelefone;
	private RatingBar ratingBarRelevancia;
	private Button btnSalvar;
	private Contato contato;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contato);
		bindingElementosLayout();
		contato = (Contato) getIntent().getSerializableExtra(CHAVE_CONTATO);

		int recursoSubTitulo;

		if (contato == null) {
			contato = new Contato();
			recursoSubTitulo = R.string.subtitle_incluir_contato;
		} else {
			carregarElementosLayout();
			recursoSubTitulo = R.string.subtitle_alterar_contato;
		}

		getSupportActionBar().setSubtitle(recursoSubTitulo);

		criaBotaoSalvar();

	}

	private void carregarElementosLayout() {
		txtNome.setText(contato.getNome());
		txtEndereco.setText(contato.getEndereco());
		txtSite.setText(contato.getSite());
		txtTelefone.setText(contato.getTelefone());
		if (null != contato.getRelevancia()) {
			ratingBarRelevancia.setRating(contato.getRelevancia());
		}
	}

	private void criaBotaoSalvar() {
		btnSalvar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				carregarContato();
				try {
					ContatoService.getInstancia(ContatoActivity.this).salvar(contato);
					ContatoActivity.this.finish();
				} catch (ExcecaoNegocio excecao) {
					for (Entry<Integer, Integer> erro : excecao.getMapaErros().entrySet()) {
						EditText campoErro = (EditText) findViewById(erro.getKey());
						Drawable drawable = getResources().getDrawable(R.drawable.ic_erro);
						drawable.setBounds(0, 0, 60, 60);
						campoErro.setError(getString(erro.getValue()), drawable);
					}
					excecao.printStackTrace();
				}

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

	@Override
	public String getActivityName() {
		return this.getClass().getSimpleName();
	}

	private void carregarContato() {
		contato.setNome(txtNome.getText().toString());
		contato.setEndereco(txtEndereco.getText().toString());
		contato.setSite(txtSite.getText().toString());
		contato.setTelefone(txtTelefone.getText().toString());
		contato.setRelevancia(ratingBarRelevancia.getRating());
	}

}
