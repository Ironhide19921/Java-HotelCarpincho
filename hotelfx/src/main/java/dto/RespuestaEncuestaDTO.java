package dto;

import java.util.ArrayList;

public class RespuestaEncuestaDTO {
	
	private String idPregunta;
	private ArrayList<String> listaRespuestas;
	
	public RespuestaEncuestaDTO(String idPregunta,ArrayList<String> listaRespuestas) {
		
		this.setIdPregunta(idPregunta);
		this.setListaRespuestas(listaRespuestas);
		
	}


	public String getIdPregunta() {
		return idPregunta;
	}


	public void setIdPregunta(String idPregunta) {
		this.idPregunta = idPregunta;
	}


	public ArrayList<String> getListaRespuestas() {
		return listaRespuestas;
	}


	public void setListaRespuestas(ArrayList<String> listaRespuestas) {
		this.listaRespuestas = listaRespuestas;
	}
		

}
