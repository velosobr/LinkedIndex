package br.ufsc.estruturas.model;

public class Product {
	private String name;
	private String label;
	private String type;
	private String value;
	private int id;

	public Product(){
		
	}
	
	/** 
	 * @param name
	 * @param label
	 * @param type
	 * @param value
	 */
	public Product(String name, String label, String type, String value) {
		super();
		this.name = name;
		this.label = label;
		this.type = type;
		this.value = value;
	}

	/** 
	 * @return int
	 */
	public int getId() {
		return id;
	}

	/** 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}	
	
	/** 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	
	/** 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	/** 
	 * @return String
	 */
	public String getLabel() {
		return label;
	}

	
	/** 
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	
	/** 
	 * @return String
	 */
	public String getType() {
		return type;
	}

	
	/** 
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	
	/** 
	 * @return String
	 */
	public String getValue() {
		return value;
	}

	
	/** 
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	
	/** 
	 * @return String
	 */
	@Override
	public String toString() {
		return "Produto [name=" + name + ", label=" + label + ", type=" + type + ", value=" + value + "]";
	}
}
