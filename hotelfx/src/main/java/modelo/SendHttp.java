package modelo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import dto.ClienteDTO;
import dto.EncuestaDTO;
import dto.RespuestaEncuestaDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;


public class SendHttp {
	//	public static void main(String[] args) {
	//		try {
	//			SendHttp.getConversion("ARS", "EUR");
	//			// SendHttp.getValorEuro();
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//	}

	// getConversion("ARS","EUR") nos da el valor dl euro frente al peso argentino,
	// para el dolar cambiar por USD
	public static Double getConversion(String divisaLocal, String divisaExtranjera) throws Exception {
		String url = "https://api.cambio.today/v1/quotes/" + divisaExtranjera + "/" + divisaLocal
				+ "/json?quantity=1&key=4487|vPCBh_AxLWD9C1HgQc3Y65tFeJgU^Bwu";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");
		// add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		JSONObject myResponse = new JSONObject(response.toString());

		return (Double) myResponse.getJSONObject("result").get("amount");

	}

	public static String getCantidadRespuestas() throws Exception {
		String url = "https://api.surveymonkey.net/v3/collectors/262094767";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");
		String basicAuth = "Bearer IsDKHHgBqYrQWHP2UNbiIpb3EChcPcg6DtuGkuA2izwrUv.D85L0J4EY0KdmmJOy3dAyqY9QrX7Jq4gmzWUqiuwCdw4vSKi1ls2V.fc-1BbWjsOPF9p6V6VOG-UZxppO";
		con.setRequestProperty ("Authorization", basicAuth);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		JSONObject myResponse = new JSONObject(response.toString());

		return (String) myResponse.get("response_count");

	}

	public static String crearCollector() throws Exception {
		String url = "https://api.surveymonkey.net/v3/surveys/286034049/collectors";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// optional default is GET
		con.setDoOutput(true);
		con.setRequestMethod("POST");
		String basicAuth = "Bearer IsDKHHgBqYrQWHP2UNbiIpb3EChcPcg6DtuGkuA2izwrUv.D85L0J4EY0KdmmJOy3dAyqY9QrX7Jq4gmzWUqiuwCdw4vSKi1ls2V.fc-1BbWjsOPF9p6V6VOG-UZxppO";
		con.setRequestProperty ("Authorization", basicAuth);	
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");

		String jsonInputString = "{\"type\": \"email\",\"sender_email\": \"carpinchocorp@gmail.com\"}";

		try(java.io.OutputStream os =  con.getOutputStream()) {		
			byte[] input = jsonInputString.getBytes("utf-8");
			os.write(input, 0, input.length);           
		}


		String idCollector="";

		try(BufferedReader br = new BufferedReader(
				new InputStreamReader(con.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			System.out.println(response.toString());
			JSONObject myResponse = new JSONObject(response.toString());
			System.out.println(myResponse.get("id"));

			idCollector = (String) myResponse.get("id");
		}

		return idCollector;

	}

	public static String crearMensaje(String collectorId) throws Exception {
		String url = "https://api.surveymonkey.net/v3/collectors/"+collectorId+"/messages";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// optional default is GET
		con.setDoOutput(true);
		con.setRequestMethod("POST");
		String basicAuth = "Bearer IsDKHHgBqYrQWHP2UNbiIpb3EChcPcg6DtuGkuA2izwrUv.D85L0J4EY0KdmmJOy3dAyqY9QrX7Jq4gmzWUqiuwCdw4vSKi1ls2V.fc-1BbWjsOPF9p6V6VOG-UZxppO";
		con.setRequestProperty ("Authorization", basicAuth);	
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");

		String jsonInputString = "{\"type\": \"invite\", \"subject\": \"Valoramos tu opinion\"}";


		try(java.io.OutputStream os =  con.getOutputStream()) {		
			byte[] input = jsonInputString.getBytes("utf-8");
			os.write(input, 0, input.length);           
		}


		String idMensaje="";

		try(BufferedReader br = new BufferedReader(
				new InputStreamReader(con.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			System.out.println(response.toString());
			JSONObject myResponse = new JSONObject(response.toString());
			System.out.println(myResponse.get("id"));

			idMensaje = (String) myResponse.get("id");
		}

		return idMensaje;

	}

	public static ArrayList<EncuestaDTO> crearRecipientes(String collectorId, String mensajeId, ObservableList<ClienteDTO> lista) throws Exception {

		String url = "https://api.surveymonkey.net/v3/collectors/"+collectorId+"/messages/"+mensajeId+"/recipients/bulk";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// optional default is GET
		con.setDoOutput(true);
		con.setRequestMethod("POST");
		String basicAuth = "Bearer IsDKHHgBqYrQWHP2UNbiIpb3EChcPcg6DtuGkuA2izwrUv.D85L0J4EY0KdmmJOy3dAyqY9QrX7Jq4gmzWUqiuwCdw4vSKi1ls2V.fc-1BbWjsOPF9p6V6VOG-UZxppO";
		con.setRequestProperty ("Authorization", basicAuth);	
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");

		JSONArray ja = new JSONArray();

		for(ClienteDTO c: lista) {
			JSONObject jo = new JSONObject();
			jo.put("email", c.getEmail());
			jo.put("first_name", c.getTipoDocumento());
			jo.put("last_name", c.getNumeroDocumento());

			ja.put(jo);
		}


		JSONObject mainObj = new JSONObject();
		mainObj.put("contacts", ja);				
		String jsonInputString = mainObj.toString();


		try(java.io.OutputStream os =  con.getOutputStream()) {		
			byte[] input = jsonInputString.getBytes("utf-8");
			os.write(input, 0, input.length);           
		}


		ArrayList<EncuestaDTO> encuestasConRecipiente = new ArrayList<EncuestaDTO>();
		
		String arrayRespuesta="";

		try(BufferedReader br = new BufferedReader(
				new InputStreamReader(con.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			System.out.println(response.toString());
			JSONObject myResponse = new JSONObject(response.toString());
			System.out.println(myResponse.get("succeeded"));

			JSONArray respuesta = new JSONArray(myResponse.get("succeeded").toString());

			for(int i=0; i<respuesta.length();i++) {
				for(int j=0;j<lista.size(); j++){
					if(respuesta.getJSONObject(i).get("email").toString().equals(lista.get(j).getEmail())) {
						EncuestaDTO e = new EncuestaDTO(0, lista.get(j).getIdCliente(), respuesta.getJSONObject(i).get("id").toString(), false);
						encuestasConRecipiente.add(e);
						break;
					}
					
				}
				
				System.out.println(respuesta.getJSONObject(i).get("id"));
				System.out.println(respuesta.getJSONObject(i).get("survey_link"));			
			}

		}
		return encuestasConRecipiente;
	}

	public static String enviarEncuestasMail(String collectorId, String mensajeId) throws Exception {
		String url = "https://api.surveymonkey.net/v3/collectors/"+collectorId+"/messages/"+mensajeId+"/send";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// optional default is GET
		con.setDoOutput(true);
		con.setRequestMethod("POST");
		String basicAuth = "Bearer IsDKHHgBqYrQWHP2UNbiIpb3EChcPcg6DtuGkuA2izwrUv.D85L0J4EY0KdmmJOy3dAyqY9QrX7Jq4gmzWUqiuwCdw4vSKi1ls2V.fc-1BbWjsOPF9p6V6VOG-UZxppO";
		con.setRequestProperty ("Authorization", basicAuth);	
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");

		String jsonInputString = "{}";


		try(java.io.OutputStream os =  con.getOutputStream()) {		
			byte[] input = jsonInputString.getBytes("utf-8");
			os.write(input, 0, input.length);           
		}


		String respuesta="";

		try(BufferedReader br = new BufferedReader(
				new InputStreamReader(con.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			System.out.println(response.toString());
			respuesta = response.toString();

		}
		System.out.println("envio encuesta exitoso");
		return respuesta;

	}

	public static int traerCantidadCollectores() throws Exception {

		String url = "https://api.surveymonkey.net/v3/surveys/286034049/collectors";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// optional default is GET
		con.setDoOutput(true);
		con.setRequestMethod("GET");
		String basicAuth = "Bearer IsDKHHgBqYrQWHP2UNbiIpb3EChcPcg6DtuGkuA2izwrUv.D85L0J4EY0KdmmJOy3dAyqY9QrX7Jq4gmzWUqiuwCdw4vSKi1ls2V.fc-1BbWjsOPF9p6V6VOG-UZxppO";
		con.setRequestProperty ("Authorization", basicAuth);	
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");

		int retorno=0;
		
		try(BufferedReader br = new BufferedReader(
				new InputStreamReader(con.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			System.out.println(response.toString());
			JSONObject myResponse = new JSONObject(response.toString());
			JSONArray data = new JSONArray(myResponse.get("data").toString());
			retorno = data.length();			
		}
		
		return retorno;
	}
	
	public static String traerIdPrimerCollector() throws Exception {

		String url = "https://api.surveymonkey.net/v3/surveys/286034049/collectors";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// optional default is GET
		con.setDoOutput(true);
		con.setRequestMethod("GET");
		String basicAuth = "Bearer IsDKHHgBqYrQWHP2UNbiIpb3EChcPcg6DtuGkuA2izwrUv.D85L0J4EY0KdmmJOy3dAyqY9QrX7Jq4gmzWUqiuwCdw4vSKi1ls2V.fc-1BbWjsOPF9p6V6VOG-UZxppO";
		con.setRequestProperty ("Authorization", basicAuth);	
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");

		String retorno="";
		
		try(BufferedReader br = new BufferedReader(
				new InputStreamReader(con.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			System.out.println(response.toString());
			JSONObject myResponse = new JSONObject(response.toString());
			JSONArray data = new JSONArray(myResponse.get("data").toString());
			JSONObject primero = new JSONObject(data.get(0).toString());
			
			retorno = primero.getString("id");			
		}
		
		return retorno;
	}
	
	public static String traerRespuestas(String idRecipiente) throws Exception {

		String url = "https://api.surveymonkey.net/v3/surveys/286034049/details";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// optional default is GET
		con.setDoOutput(true);
		con.setRequestMethod("GET");
		String basicAuth = "Bearer IsDKHHgBqYrQWHP2UNbiIpb3EChcPcg6DtuGkuA2izwrUv.D85L0J4EY0KdmmJOy3dAyqY9QrX7Jq4gmzWUqiuwCdw4vSKi1ls2V.fc-1BbWjsOPF9p6V6VOG-UZxppO";
		con.setRequestProperty ("Authorization", basicAuth);	
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");

		String retorno="";

		try(BufferedReader br = new BufferedReader(
				new InputStreamReader(con.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			System.out.println(response.toString());
			JSONObject myResponse = new JSONObject(response.toString());
			System.out.println(myResponse.get("pages"));

			ArrayList<RespuestaEncuestaDTO> resultado = SendHttp.consultarRespuestaEncuesta(idRecipiente);

			JSONArray pages = new JSONArray(myResponse.get("pages").toString());
			JSONObject intermedio = new JSONObject(pages.get(0).toString());
			System.out.println(intermedio.toString());
			JSONArray questions = new JSONArray(intermedio.get("questions").toString());
			System.out.println(questions.toString());



			for(int i=0; i<questions.length(); i++) {
				JSONObject pregu = new JSONObject(questions.get(i).toString());

				System.out.println("id pregunta: "+pregu.get("id"));

				for(RespuestaEncuestaDTO re: resultado) {
					if(re.getIdPregunta().equals(pregu.get("id"))){

						JSONArray headings = new JSONArray(pregu.get("headings").toString());

						JSONObject heading = new JSONObject(headings.get(0).toString());
						System.out.println("heading(pregunta): "+ heading.get("heading").toString());

						//LLeno descripcion pregunta
						retorno = retorno+heading.get("heading").toString()+";";

						JSONObject answers = new JSONObject(pregu.get("answers").toString());
						JSONArray choices = new JSONArray(answers.get("choices").toString());


						if(re.getListaRespuestas().size()>1) {
							for(int j=0; j<choices.length(); j++) {
								JSONObject choice = new JSONObject(choices.get(j).toString());
//								String respuestasSepConGuion="";
								for(String s: re.getListaRespuestas()) {
									System.out.println("id choice: "+choice.get("id"));
									System.out.println(choice.get("text"));
									String respuestasSepConGuion="";
													
									if(choice.get("id").toString().equals(s)) {
										//LLeno descripcion respuesta
										respuestasSepConGuion = respuestasSepConGuion + choice.get("text")+"&"; 
									}
																		
									retorno = retorno + respuestasSepConGuion;
								}
							}
							retorno = retorno + "|" ;
						} else {
							for(int j=0; j<choices.length(); j++) {
								JSONObject choice = new JSONObject(choices.get(j).toString());
								for(String s: re.getListaRespuestas()) {
									System.out.println("id choice: "+choice.get("id"));
									System.out.println(choice.get("text"));
									String respuestasSepConGuion="";
													
									if(choice.get("id").toString().equals(s)) {
										//LLeno descripcion respuesta
										respuestasSepConGuion = respuestasSepConGuion + choice.get("text")+"|"; 
									}
									retorno = retorno + respuestasSepConGuion;
								}							
							}
						}
					}
				}
			}
		}

		return retorno;

	}

	public static ArrayList<RespuestaEncuestaDTO> consultarRespuestaEncuesta(String idRecipiente) throws Exception {


		String url = "https://api.surveymonkey.net/v3/surveys/286034049/responses/bulk?per_page=40";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// optional default is GET
		con.setDoOutput(true);
		con.setRequestMethod("GET");
		String basicAuth = "Bearer IsDKHHgBqYrQWHP2UNbiIpb3EChcPcg6DtuGkuA2izwrUv.D85L0J4EY0KdmmJOy3dAyqY9QrX7Jq4gmzWUqiuwCdw4vSKi1ls2V.fc-1BbWjsOPF9p6V6VOG-UZxppO";
		con.setRequestProperty ("Authorization", basicAuth);	
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");

		ArrayList<RespuestaEncuestaDTO> resultado = new ArrayList<RespuestaEncuestaDTO>();

		try(BufferedReader br = new BufferedReader(
				new InputStreamReader(con.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			System.out.println(response.toString());
			JSONObject myResponse = new JSONObject(response.toString());
			System.out.println(myResponse.get("data"));
			
			JSONArray data = new JSONArray(myResponse.get("data").toString());

			for(int i=0; i<data.length(); i++) {
				JSONObject respuesta = new JSONObject(data.get(i).toString());
				if(respuesta.get("recipient_id").toString().equals(idRecipiente)) {
					JSONArray pages = new JSONArray(respuesta.get("pages").toString());
					JSONObject intermedio = new JSONObject(pages.get(0).toString());
					JSONArray questions = new JSONArray(intermedio.get("questions").toString());

					for(int k=0; k<questions.length(); k++) {
						JSONObject resp = new JSONObject(questions.get(k).toString());
						System.out.println("id pregunta elegida: "+resp.get("id"));

						JSONArray answers = new JSONArray(resp.get("answers").toString());

						ArrayList<String> elegidas = new ArrayList<String>();

						for(int j=0; j<answers.length(); j++) {
							JSONObject answer = new JSONObject(answers.get(j).toString());
							System.out.println("respuesta elegida num "+j+" "+answer.get("choice_id"));

							elegidas.add(answer.get("choice_id").toString());							
						}
						RespuestaEncuestaDTO respEncuesta = new RespuestaEncuestaDTO(resp.getString("id"), elegidas);

						resultado.add(respEncuesta);
					}
				}
			}

		}
		return resultado;

	}
	
	public static String actualizarEncuestaRespondida(String idRecipiente) throws Exception {


		String url = "https://api.surveymonkey.net/v3/surveys/286034049/responses/bulk?per_page=40";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// optional default is GET
		con.setDoOutput(true);
		con.setRequestMethod("GET");
		String basicAuth = "Bearer IsDKHHgBqYrQWHP2UNbiIpb3EChcPcg6DtuGkuA2izwrUv.D85L0J4EY0KdmmJOy3dAyqY9QrX7Jq4gmzWUqiuwCdw4vSKi1ls2V.fc-1BbWjsOPF9p6V6VOG-UZxppO";
		con.setRequestProperty ("Authorization", basicAuth);	
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");

		String resultado ="";

		try(BufferedReader br = new BufferedReader(
				new InputStreamReader(con.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			System.out.println(response.toString());
			JSONObject myResponse = new JSONObject(response.toString());
			System.out.println(myResponse.get("data"));
			
			JSONArray data = new JSONArray(myResponse.get("data").toString());

			for(int i=0; i<data.length(); i++) {
				JSONObject respuesta = new JSONObject(data.get(i).toString());
				if(respuesta.get("recipient_id").toString().equals(idRecipiente)) {
				resultado="si";
				break;
				}
			}

		}
		return resultado;

	}


}


