package br.com.cast.treinamento.app;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import br.com.cast.treinamento.app.domain.Contato;
import br.com.cast.treinamento.app.service.ContatoService;
import br.com.cast.treinamento.app.widget.ContatoAdapter;
import br.com.cast.treinamento.app.widget.ContatoAdapterClickListener;

public class ListaContatosActivity extends LifeCicleActivit {

	private ListView listViewContatos;

	private Contato contatoSelecionado;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_lista_contatos);
		listViewContatos = (ListView) super.findViewById(R.id.listViewContatos);

		super.registerForContextMenu(listViewContatos);

		ContatoAdapterClickListener listener = new ContatoAdapterClickListener(this);

		listViewContatos.setOnItemClickListener(listener);

		getSupportActionBar().setSubtitle(R.string.lista_contatos);

		listViewContatos.setOnItemLongClickListener(listener);

	}

	public void recuperarContatoSelecionado(AdapterView<?> adapter, int posicao) {
		contatoSelecionado = (Contato) adapter.getItemAtPosition(posicao);
	}

	protected void onResume() {
		super.onResume();
		carregarListView();
	}

	private void carregarListView() {
		List<Contato> contatos = ContatoService.getInstancia(this).listarTodos();
		ContatoAdapter adapter = new ContatoAdapter(this, contatos);
		listViewContatos.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.lista_contatos, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		criarMenuItem(item);
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.lista_contatos, menu);
		MenuItem menuNovo = menu.findItem(R.id.action_novo);
		menuNovo.setVisible(false);
		super.onCreateContextMenu(menu, v, menuInfo);

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		criarMenuItem(item);
		return super.onContextItemSelected(item);
	}

	private void criarMenuItem(MenuItem item) {
		int id = item.getItemId();
		Intent intent = new Intent(this, ContatoActivity.class);
		switch (id) {
		case R.id.action_editar:
			intent.putExtra(ContatoActivity.CHAVE_CONTATO, contatoSelecionado);
			super.startActivity(intent);
			break;
		case R.id.action_excluir:
			new AlertDialog.Builder(this).setTitle(R.string.dialog_confirmacao).setMessage(getString(R.string.dialog_mensagem, contatoSelecionado.getNome()))
					.setPositiveButton(R.string.dialog_sim, new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							ContatoService.getInstancia(ListaContatosActivity.this).excluir(contatoSelecionado);
							carregarListView();
						}
					}).setNeutralButton(R.string.dialog_nao, null).create().show();
			break;
		case R.id.action_novo:
			super.startActivity(intent);
			break;
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem menuEditar = menu.findItem(R.id.action_editar);
		menuEditar.setVisible(contatoSelecionado != null);
		MenuItem menuExcluir = menu.findItem(R.id.action_excluir);
		menuExcluir.setVisible(contatoSelecionado != null);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public String getActivityName() {
		return this.getClass().getSimpleName();
	}
}
