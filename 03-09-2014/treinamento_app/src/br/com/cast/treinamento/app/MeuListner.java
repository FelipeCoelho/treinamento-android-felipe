package br.com.cast.treinamento.app;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class MeuListner implements OnClickListener {

	@Override
	public void onClick(View arg0) {
		Log.d("MEU DEBUG", "Clicou para caramba");
	}

}
