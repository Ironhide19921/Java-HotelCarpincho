package dto;

import java.util.ArrayList;

public class RespuestaEncuestaDTO {
	
	private String idPregunta;
	private ArrayList<String> listaRespuestas;
	private String detallePregunta;

	
	public RespuestaEncuestaDTO(String idPregunta,ArrayList<String> listaRespuestas) {
		
		this.setIdPregunta(idPregunta);
		this.setListaRespuestas(listaRespuestas);
		this.detallePregunta="";
		
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


	public String getDetallePregunta() {
		return detallePregunta;
	}


	public void setDetallePregunta(String detallePregunta) {
		this.detallePregunta = detallePregunta;
	}		

}
