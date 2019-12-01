package application.enums;

public enum FlagStatusProduto {

	PENDENTE("Pendente"),
	RESERVADO("Reservado"),
	ENVIADO("Enviado");

	public final String label;

	private FlagStatusProduto(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
