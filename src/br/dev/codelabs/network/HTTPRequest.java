package br.dev.codelabs.network;

import java.awt.Image;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class HTTPRequest {
	
	public JSONObject requestGetMethod(String stringUrl) {
        try {
        	//RECEBE A URL PARA CONEXAO
        	URL url = new URL(stringUrl);
        	//TESTA CONEXAO COM A URL
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            //FECHA CONEXAO COM A URL
            connection.connect();
            //COLETA AS INFORMACOES FORNECIDAS PELA URL NO FORMATO STRING
            String response = IOUtils.toString(connection.getInputStream(), "UTF-8");
            //CONVERTE AS INFORMACOES RECEBIDAS NO FORMATO STRING PARA JSON
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public Image requestPokemonImagem(String stringUrl) {
		try {
			//RECEBE A URL PARA CONEXAO
			URL url = new URL(stringUrl);
			//CONVERTE A URL DA IMAGEM EM UMA IMAGEM
			Image imagemPokemon = ImageIO.read(url);
			//RETORNA A IMAGEM
			return imagemPokemon;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
