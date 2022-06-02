package br.dev.codelabs.search;

import org.json.JSONArray;
import org.json.JSONException;
import br.dev.codelabs.Main;

public class Informacoes {
	
	//COLETA AS HABILIDADES DO POKEMON SELECIONADO
	public static void coletaHabilidades(JSONArray arrayName) throws JSONException {
		Main.informacoesPokemon = Main.informacoesPokemon + "--- HABILIDADES ---\n";
		if(arrayName.length() == 0) {
			Main.informacoesPokemon = Main.informacoesPokemon + "Nenhuma habilidade foi encontrada\n";
		}
		for(var i = 0; i < arrayName.length(); i++) {
			String habilidade = arrayName.getJSONObject(i).getJSONObject("ability").getString("name");
			Main.informacoesPokemon = Main.informacoesPokemon + "-> " + habilidade + "\n";
		}
	}
	
	//COLETA OS TIPOS DO POKEMON SELECIONADO
	public static void coletaTipos(JSONArray arrayName) throws JSONException {
		Main.informacoesPokemon = Main.informacoesPokemon + "\n--- TIPO ---\n";
		if(arrayName.length() == 0) {
			Main.informacoesPokemon = Main.informacoesPokemon + "Nenhum tipo foi encontrado\n";
		}
		for(var i = 0; i < arrayName.length(); i++) {
			String tipo = arrayName.getJSONObject(i).getJSONObject("type").getString("name");
			Main.informacoesPokemon = Main.informacoesPokemon + "-> " + tipo + "\n";
		}
	}
	
	//COLETA AS ESTATISTICAS DO POKEMON SELECIONADO
	public static void coletaEstatisticas(JSONArray arrayName) throws JSONException {
		Main.informacoesPokemon = Main.informacoesPokemon + "\n--- ESTATISTICAS ---\n";
		if(arrayName.length() == 0) {
			Main.informacoesPokemon = Main.informacoesPokemon + "Nenhuma estatistica foi encontrada\n";
		}
		for(var i = 0; i < arrayName.length(); i++) {
			int estatisticaValor = arrayName.getJSONObject(i).getInt("base_stat");
			String estatisticaDescricao = arrayName.getJSONObject(i).getJSONObject("stat").getString("name");
			Main.informacoesPokemon = Main.informacoesPokemon + "-> " + estatisticaDescricao + ": " + estatisticaValor + "\n";
		}
	}
	
	//COLETA OS MOVIMENTOS DO POKEMON SELECIONADO
	public static void coletaMovimentos(JSONArray arrayName) throws JSONException {
		Main.informacoesPokemon = Main.informacoesPokemon + "\n--- MOVIMENTOS ---\n";
		if(arrayName.length() == 0) {
			Main.informacoesPokemon = Main.informacoesPokemon + "Nenhum movimento foi encontrado\n";
		}
		for(var i = 0; i < arrayName.length(); i++) {
			String estatisticaDescricao = arrayName.getJSONObject(i).getJSONObject("move").getString("name");
			Main.informacoesPokemon = Main.informacoesPokemon + "-> " + estatisticaDescricao + "\n";
		}
	}
	
}
