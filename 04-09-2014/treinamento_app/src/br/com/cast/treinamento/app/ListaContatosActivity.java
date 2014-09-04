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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.cast.treinamento.app.domain.Contato;
import br.com.cast.treinamento.app.service.ContatoService;
import br.com.cast.treinamento.app.widget.ContatoAdapter;

public class ListaContatosActivity extends LifeCicleActivit {

	private ListView listViewContatos;

	private Contato contatoSelecionado;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_lista_contatos);
		listViewContatos = (ListView) super.findViewById(R.id.listViewContatos);

		super.registerForContextMenu(listViewContatos);
		/*
		 * listViewContatos.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> adapter, View view, int posicao, long id) { String mensagem =
		 * getString(R.string.voc_clicou_na_posicao, posicao); Toast.makeText(view.getContext(), mensagem, Toast.LENGTH_SHORT).show(); } });
		 */

		listViewContatos.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view, int posicao, long id) {
				contatoSelecionado = (Contato) adapter.getItemAtPosition(posicao);
				return false;
			}
		});

	}

	protected void onResume() {
		super.onResume();
		carregarListView();
	}

	private void carregarListView() {
		List<Contato> contatos = ContatoService.getInstancia(this).listarTodos();
		// ArrayAdapter<Contato> adapter = new ArrayAdapter<Contato>(this, android.R.layout.simple_list_item_1, contatos);
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
		int id = item.getItemId();
		switch (id) {
		case R.id.action_novo:
			Intent intent = new Intent(this, ContatoActivity.class);
			super.startActivity(intent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.lista_contatos_context, menu);
		super.onCreateContextMenu(menu, v, menuInfo);

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		int id = item.getItemId();
		switch (id) {
		case R.id.action_editar:
			Intent intent = new Intent(this, ContatoActivity.class);
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
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public String getActivityName() {
		return this.getClass().getSimpleName();
	}
}
