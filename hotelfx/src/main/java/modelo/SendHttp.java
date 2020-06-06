package modelo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

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
}

