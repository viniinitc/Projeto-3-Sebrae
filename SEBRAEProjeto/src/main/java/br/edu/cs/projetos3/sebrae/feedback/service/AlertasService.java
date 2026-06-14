package br.edu.cs.projetos3.sebrae.feedback.service;

import br.edu.cs.projetos3.sebrae.feedback.entidades.AlertasResponseDTO;
import br.edu.cs.projetos3.sebrae.feedback.repository.AlertasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AlertasService {

    @Autowired
    private AlertasRepository repository;

    @SuppressWarnings("unchecked")
    public AlertasResponseDTO listarAlertas() {
        List<Map<String, Object>> dadosBrutos = repository.lerDadosAlertas();

        List<Map<String, Object>> tiposDeAlertas = new ArrayList<>();
        List<Map<String, Object>> listaDeAlertas = new ArrayList<>();
        List<Map<String, Object>> tendencia = new ArrayList<>();

        if (dadosBrutos != null) {
            for (Map<String, Object> bloco : dadosBrutos) {
                if (bloco.containsKey("tiposDeAlertas"))
                    tiposDeAlertas = (List<Map<String, Object>>) bloco.get("tiposDeAlertas");

                if (bloco.containsKey("listaDeAlertas"))
                    listaDeAlertas = (List<Map<String, Object>>) bloco.get("listaDeAlertas");

                if (bloco.containsKey("tendencia"))
                    tendencia = (List<Map<String, Object>>) bloco.get("tendencia");
            }
        }

        return new AlertasResponseDTO(tiposDeAlertas, listaDeAlertas, tendencia);
    }
}