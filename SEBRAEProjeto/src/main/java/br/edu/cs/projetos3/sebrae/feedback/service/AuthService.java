package br.edu.cs.projetos3.sebrae.feedback.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AuthService {

    private final ObjectMapper mapper = new ObjectMapper();
    private final String PATH_ARQUIVO = "src/main/resources/data/usuarios.json";
    private final List<ObjectNode> usuarios = new ArrayList<>();

    public AuthService() {
        carregarUsuarios();
    }

    private void carregarUsuarios() {
        try {
            File arquivo = new File(PATH_ARQUIVO);
            JsonNode root = null;

            if (arquivo.exists()) {
                root = mapper.readTree(arquivo);
            } else {
                InputStream is = getClass().getResourceAsStream("/data/usuarios.json");
                if (is != null) {
                    root = mapper.readTree(is);
                }
            }

            if (root != null) {
                JsonNode lista = root.get("usuarios");
                if (lista != null && lista.isArray()) {
                    for (JsonNode u : lista) {
                        if (u.isObject()) {
                            usuarios.add((ObjectNode) u);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar usuarios.json: " + e.getMessage());
        }
    }

    private void salvarUsuarios() {
        try {
            ObjectNode root = mapper.createObjectNode();
            ArrayNode usuariosArray = root.putArray("usuarios");
            for (ObjectNode usuario : usuarios) {
                usuariosArray.add(usuario);
            }
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(PATH_ARQUIVO), root);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar usuarios.json", e);
        }
    }

    public ResponseEntity<Map<String, String>> login(String emailOuCpf, String senha) {
        for (JsonNode usuario : usuarios) {
            String email = usuario.path("email").asText();
            String cpf = usuario.path("cpf").asText();
            if ((emailOuCpf.equals(email) || emailOuCpf.equals(cpf)) && senha.equals(usuario.path("senha").asText())) {
                String nome = usuario.path("nome").asText();
                return ResponseEntity.ok(Map.of("mensagem", "Login realizado!", "nome", nome));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("erro", "Email, CPF ou senha inválidos!"));
    }

    public ResponseEntity<Map<String, String>> cadastro(Map<String, String> body) {
        String email = body.get("email");
        String cpf = body.get("cpf");

        for (JsonNode usuario : usuarios) {
            if (email != null && email.equals(usuario.path("email").asText())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("erro", "Email já cadastrado!"));
            }
            if (cpf != null && cpf.equals(usuario.path("cpf").asText())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("erro", "CPF já cadastrado!"));
            }
        }

        ObjectNode novoUsuario = mapper.createObjectNode();
        novoUsuario.put("cpf", cpf);
        novoUsuario.put("nome", body.get("nome"));
        novoUsuario.put("telefone", body.get("telefone"));
        novoUsuario.put("cep", body.get("cep"));
        novoUsuario.put("email", email);
        novoUsuario.put("senha", body.get("senha"));
        usuarios.add(novoUsuario);
        salvarUsuarios();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("mensagem", "Cadastro realizado com sucesso!"));
    }
}