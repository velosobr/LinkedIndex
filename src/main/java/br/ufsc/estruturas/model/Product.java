package br.ufsc.estruturas.model;

public class Product {
	private String name;
	private String label;
	private String type;
	private float value;

	public Product(String name, String label, String type, float value) {
		super();
		this.name = name;
		this.label = label;
		this.type = type;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Produto [name=" + name + ", label=" + label + ", type=" + type + ", value=" + value + "]";
	}
}
