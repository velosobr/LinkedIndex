package br.ufsc.estruturas.data;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.estruturas.indexation.DirInvertedIndex;
import br.ufsc.estruturas.model.Product;

public class DataProdutos {
	private Product[] products;
	private DirInvertedIndex dirByLabel;
	private DirInvertedIndex dirByType;
	private int index = 0;

	public DataProdutos(Product[] produtos, DirInvertedIndex dirByLabel, DirInvertedIndex dirByType) {
		this.products = produtos;
		this.dirByLabel = dirByLabel;
		this.dirByType = dirByType;
	}

	public Product[] getProducts() {
		return products;
	}

	public void setProducts(Product[] products) {
		this.products = products;
	}

	public DirInvertedIndex getDirByLabel() {
		return dirByLabel;
	}

	public void setDirByLabel(DirInvertedIndex dirByLabel) {
		this.dirByLabel = dirByLabel;
	}

	public DirInvertedIndex getDirByType() {
		return dirByType;
	}

	public void setDirByType(DirInvertedIndex dirByType) {
		this.dirByType = dirByType;
	}

	public void insertProduct(Product product) {
		products[getIndex()] = product;
		dirByLabel.incluir(product.getLabel(), getIndex());
		dirByType.incluir(product.getType(), getIndex());
		incrementIndex();
	}

	public List<Product> getByLabel(String label) {
		List<Product> filtred = new ArrayList<Product>();
		List<Integer> indexs = dirByLabel.consulta(label);
		for (int i : indexs) {
			filtred.add(products[i]);
		}
		return filtred;
	}

	private int getIndex() {
		return index;
	}

	private void incrementIndex() throws IndexOutOfBoundsException {
		if (index != 0) {
			index = index + 1;
		}
		if (index > products.length) {
			index = index - 1;
			throw new IndexOutOfBoundsException();
		}
	}

}