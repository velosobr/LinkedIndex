package br.ufsc.estruturas.indexation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DirInvertedIndex {
	private HashMap<String, List<Integer>> dir;

	public DirInvertedIndex(HashMap<String, List<Integer>> dir) {
		this.dir = dir;
	}

	public void incluir(String key, int index) {
		if (dir.containsKey(key)) {
			dir.get(key).add(index);
		} else {
			dir.put(key, new ArrayList<Integer>());
			dir.get(key).add(index);
		}
	}

	public List<Integer> consulta(String key) {
		return dir.get(key);
	}

}