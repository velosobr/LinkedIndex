package br.ufsc.estruturas.indexation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DirInvertedIndex {
	private HashMap<String, List<Integer>> dir;
	
	/** 
	 * @param dir
	 * 
	 */
	public DirInvertedIndex(HashMap<String, List<Integer>> dir) {
		this.dir = dir;
	}

	
	/**
	 * Metodo para incluir uma chave do tipo String e um int referente a posição
	 * do objeto no array Products da classe DataProducts 
	 * @param key
	 * @param index 
	 */
	public void incluir(String key, int index) {
		if (dir.containsKey(key)) {
			dir.get(key).add(index);
		} else {
			dir.put(key, new ArrayList<Integer>());
			dir.get(key).add(index);
		}
	}
	
	/** 
	 * Metodo que retorna uma Lista de index, do array de Products da classe DataProducts,
	 * que está relacionado com a chave do hashMap passado no parametro
	 * @param key
	 * @return List<Integer>
	 */
	public List<Integer> consulta(String key) {
		return dir.get(key);
	}

}