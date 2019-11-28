package constantes;

public enum FlagStatusProduto {
	
	RESERVADO("Reservado", "RE"),
	ENVIADO("Enviado", "EN");

	private String descricao;
	private String value;
	
	 FlagStatusProduto(String bundle , String value) {
		this.descricao=bundle;
		this.value=value;
	}
	 
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String bundle) {
		this.descricao = bundle;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
