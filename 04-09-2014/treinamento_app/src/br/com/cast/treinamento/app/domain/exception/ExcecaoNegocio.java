package br.com.cast.treinamento.app.domain.exception;

import java.util.LinkedHashMap;
import java.util.Map;

public class ExcecaoNegocio extends Exception {
	private static final long serialVersionUID = -1448514740365899972L;

	private final Map<Integer, Integer> mapaErros = new LinkedHashMap<>();

	public Map<Integer, Integer> getMapaErros() {
		return mapaErros;
	}
}
