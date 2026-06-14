package br.edu.cs.projetos3.sebrae.feedback.controller;

import br.edu.cs.projetos3.sebrae.feedback.entidades.AlertasResponseDTO;
import br.edu.cs.projetos3.sebrae.feedback.service.AlertasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alertas-comportamento")
@CrossOrigin(originPatterns = "*")
public class AlertasController {

    @Autowired
    private AlertasService service;

    @GetMapping
    public AlertasResponseDTO listarAlertas() {
        return service.listarAlertas();
    }
}