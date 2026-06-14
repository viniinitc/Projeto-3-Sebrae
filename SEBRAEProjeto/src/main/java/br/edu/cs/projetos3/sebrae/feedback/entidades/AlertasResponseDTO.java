package br.edu.cs.projetos3.sebrae.feedback.entidades;

import java.util.List;
import java.util.Map;

public class AlertasResponseDTO {

    private List<Map<String, Object>> tiposDeAlertas;
    private List<Map<String, Object>> listaDeAlertas;
    private List<Map<String, Object>> tendencia;

    public AlertasResponseDTO(
            List<Map<String, Object>> tiposDeAlertas,
            List<Map<String, Object>> listaDeAlertas,
            List<Map<String, Object>> tendencia) {
        this.tiposDeAlertas = tiposDeAlertas;
        this.listaDeAlertas = listaDeAlertas;
        this.tendencia = tendencia;
    }

    public List<Map<String, Object>> getTiposDeAlertas() { return tiposDeAlertas; }
    public List<Map<String, Object>> getListaDeAlertas() { return listaDeAlertas; }
    public List<Map<String, Object>> getTendencia() { return tendencia; }
}