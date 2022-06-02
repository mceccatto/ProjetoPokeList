package br.dev.codelabs.modelos;

public class PokemonLista {
	
	private String nome,url;

	public PokemonLista(String nome,String url) {
		this.nome = nome;
		this.url = url;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return nome;
	}
	
}
