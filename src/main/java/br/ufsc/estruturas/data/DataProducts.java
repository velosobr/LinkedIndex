package br.ufsc.estruturas.data;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.estruturas.indexation.DirInvertedIndex;
import br.ufsc.estruturas.model.Product;

public class DataProducts {
	private Product[] products;
	private DirInvertedIndex dirByLabel;
	private DirInvertedIndex dirByType;
	private int index = 0;

	
	/** 
	 * @param products
	 * @param dirByLabel
	 * @param dirByType
	 * 
	 */
	public DataProducts(Product[] products, DirInvertedIndex dirByLabel, DirInvertedIndex dirByType) {
		this.products = products;
		this.dirByLabel = dirByLabel;
		this.dirByType = dirByType;
	}

	
	/** 
	 * @return Product[]
	 */
	public Product[] getProducts() {
		return products;
	}

	
	/** 
	 * @param products
	 */
	public void setProducts(Product[] products) {
		this.products = products;
	}

	
	/** 
	 * @return DirInvertedIndex
	 */
	public DirInvertedIndex getDirByLabel() {
		return dirByLabel;
	}

	
	/** 
	 * @param dirByLabel
	 */
	public void setDirByLabel(DirInvertedIndex dirByLabel) {
		this.dirByLabel = dirByLabel;
	}

	
	/** 
	 * @return DirInvertedIndex
	 */
	public DirInvertedIndex getDirByType() {
		return dirByType;
	}

	
	/** 
	 * @param dirByType
	 */
	public void setDirByType(DirInvertedIndex dirByType) {
		this.dirByType = dirByType;
	}

	
	/** 
	 * @param product
	 */
	public void insertProduct(Product product) {
		products[getIndex()] = product;
		dirByLabel.incluir(product.getLabel(), getIndex());
		dirByType.incluir(product.getType(), getIndex());
		incrementIndex();
	}

	
	/** 
	 * @param label
	 * @return List<Product>
	 */
	public List<Product> getByLabel(String label) {
		List<Product> filtred = new ArrayList<Product>();
		List<Integer> indexs = dirByLabel.consulta(label);
		for (int i : indexs) {
			filtred.add(products[i]);
		}
		return filtred;
	}

	
	/** 
	 * @return int
	 */
	private int getIndex() {
		return index;
	}

	
	/** 
	 * @throws IndexOutOfBoundsException
	 */
	private void incrementIndex() throws IndexOutOfBoundsException {
		index++;
		if(index > products.length) {
			index = index -1;
			throw new IndexOutOfBoundsException();
		}
	}

}