package br.dev.codelabs;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import br.dev.codelabs.network.HTTPRequest;
import br.dev.codelabs.search.Informacoes;
import br.dev.codelabs.modelos.PokemonLista;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import java.awt.Color;

public class Main {

	//INICIALIZACAO DO JFRAME PRINCIPAL DA APLICACAO
	JFrame principal;
	//INICIALIZACAO DA LISTA DE POKEMONS PARA CONSULTA
	DefaultListModel<PokemonLista> PokemonsLista = new DefaultListModel<>();
	//INICIALIZACAO DA STRING DE RETORNO QUE CONTERAO TODAS AS INFORMACOES DO POKEMON SELECIONADO
	public static String informacoesPokemon;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.principal.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {
		//EFETUA A CARGA INICIAL DA URL DA POKEAPI
		cargaInicial();
		//EFETUA A CONSTRUCAO INICIAL DO JFRAME
		inicializacao();
		//CARREGA OS ITENS DE APRESENTACAO NA TELA
		itensIniciais();
		//CARREGA O MENU DE BUSCA E RETORNO NA TELA
		buscaPokemon();
	}
	
	public void cargaInicial() {
		//ENVIA A URL PARA A COLETA DE DADOS
		JSONObject pokemonsLista = new HTTPRequest().requestGetMethod("https://pokeapi.co/api/v2/pokemon?offset=0&limit=1126");
		try {
			//COM OS DADOS COLETADOS, PERCORREMOS O ARRAY COLETANDO O NOME DOS POKEMONS E SUA RESPECTIVA URL DE INFORMACOES
			JSONArray pokemon = pokemonsLista.getJSONArray("results");
			for(int i = 0; i < pokemon.length(); i++) {
		        JSONObject pokeObject = pokemon.getJSONObject(i);
		        String nomePokemon = pokeObject.getString("name");
		        String urlPokemon = pokeObject.getString("url");
		        //EFETUA A CRIACAO DE UM NOVO OBJETO POKEMONLISTA CONTENDO O NOME E URL DO POKEMON
		        PokemonLista listaPokemons = new PokemonLista(nomePokemon, urlPokemon);
		        //INSERE O OBJETO NA LISTA DE POKEMONS DO SISTEMA
		        PokemonsLista.addElement(listaPokemons);
		    }
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void inicializacao() {
		principal = new JFrame();
		principal.setResizable(false);
		principal.setBounds(100, 100, 800, 550);
		principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		principal.getContentPane().setLayout(null);
	}
	
	public void itensIniciais() {
		JLabel lbTituloListaNomes = new JLabel("Selecione o Pokemon desejado");
		lbTituloListaNomes.setHorizontalAlignment(SwingConstants.CENTER);
		lbTituloListaNomes.setBounds(10, 11, 180, 14);
		principal.getContentPane().add(lbTituloListaNomes);
		
		JLabel lbResultados = new JLabel("Informa\u00E7\u00F5es encontradas");
		lbResultados.setBounds(211, 11, 195, 14);
		principal.getContentPane().add(lbResultados);
		
		JLabel lbImagemTexto = new JLabel("Representa\u00E7\u00E3o visual");
		lbImagemTexto.setHorizontalAlignment(SwingConstants.CENTER);
		lbImagemTexto.setBounds(10, 324, 180, 14);
		principal.getContentPane().add(lbImagemTexto);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.GRAY);
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(200, 11, 1, 489);
		principal.getContentPane().add(separator);
	}
	
	public void buscaPokemon() {
		JScrollPane scrollPaneInformacoes = new JScrollPane();
		scrollPaneInformacoes.setBounds(211, 30, 563, 470);
		principal.getContentPane().add(scrollPaneInformacoes);
		
		JTextArea resultados = new JTextArea();
		resultados.setEditable(false);
		resultados.setLineWrap(true);
		resultados.setBounds(211, 30, 563, 470);
		scrollPaneInformacoes.setViewportView(resultados);
		
		JLabel lbImagem = new JLabel();
		lbImagem.setHorizontalAlignment(SwingConstants.CENTER);
		lbImagem.setBounds(10, 349, 178, 151);
		principal.getContentPane().add(lbImagem);
		
		JScrollPane scrollPaneNomes = new JScrollPane();
		scrollPaneNomes.setBounds(10, 30, 180, 282);
		principal.getContentPane().add(scrollPaneNomes);
		
		JList<PokemonLista> listaNomes = new JList<PokemonLista>(PokemonsLista);
		listaNomes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				@SuppressWarnings("unchecked")
				JList<String> onClick = (JList<String>) e.getSource();
				if(e.getClickCount() == 2) {
					informacoesPokemon = "";
					int index = onClick.locationToIndex(e.getPoint());
					//ENVIA A URL DO POKEMON SELECIONADO PARA A COLETA DE DADOS
					JSONObject pokemon = new HTTPRequest().requestGetMethod(PokemonsLista.get(index).getUrl());
					try {
						//COLETA A URL DA IMAGEM DO POKEMON
						String pokemonImagemUrl = pokemon.getJSONObject("sprites").getString("front_default");
						//COLETA AS HABILIDADES DO POKEMON
						Informacoes.coletaHabilidades(pokemon.getJSONArray("abilities"));
						//COLETA OS TIPOS DO POKEMON
						Informacoes.coletaTipos(pokemon.getJSONArray("types"));
						//COLETA AS ESTAT�STICAS DO POKEMON
						Informacoes.coletaEstatisticas(pokemon.getJSONArray("stats"));
						//COLETA OS MOVIMENTOS DO POKEMON
						Informacoes.coletaMovimentos(pokemon.getJSONArray("moves"));
						//CRIA UMA NOVA IMAGEM COM A URL DO POKEMON
						Image imagemPokemon = new HTTPRequest().requestPokemonImagem(pokemonImagemUrl);
						lbImagem.setIcon(new ImageIcon(imagemPokemon.getScaledInstance(178, 151, Image.SCALE_DEFAULT)));
						principal.getContentPane().add(lbImagem);
						//INSERE OS DADOS DO POKEMON NA �REA DE RESULTADOS
						resultados.setText(informacoesPokemon);
						resultados.setSelectionStart(0);
						resultados.setSelectionEnd(0);
					} catch (JSONException e1) {
						//CASO O POKEMON NOO SEJA ENCONTRADO, CARREGA UMA IMAGEM PADRAO E INSERE UM RESULTADO PADRAO
						File imagem = new File("img/erro.png");
						try {
							Image imagemPokemon = ImageIO.read(imagem);
							lbImagem.setIcon(new ImageIcon(imagemPokemon.getScaledInstance(178, 151, Image.SCALE_DEFAULT)));
						} catch (IOException e2) {
							e2.printStackTrace();
						}
						resultados.setText("Pokemon nao encontrado!!!");
						e1.printStackTrace();
					}
					//EFETUA A ATUALIZACAO DA INTERFACE VISUAL
					principal.revalidate();
					principal.repaint();
				}
			}
		});
		listaNomes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPaneNomes.setViewportView(listaNomes);
		listaNomes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
}
