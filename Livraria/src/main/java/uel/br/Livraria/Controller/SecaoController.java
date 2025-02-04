package uel.br.Livraria.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import uel.br.Livraria.DAO.PgSecaoDAO;
import uel.br.Livraria.Model.Secao;

import java.sql.SQLException;

@RestController
@RequestMapping("/secoes")
public class SecaoController {
    private final PgSecaoDAO pgSecaoDAO;

    public SecaoController(PgSecaoDAO pgSecaoDAO) {
        this.pgSecaoDAO = pgSecaoDAO;
    }

    @PostMapping
    public ResponseEntity<String> criarSecao(@RequestBody Secao secao) {
        try {
            pgSecaoDAO.create(secao);
            return ResponseEntity.ok("Seção criada com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar seção.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Secao> obterSecao(@PathVariable int id) {
        try {
            Secao secao = pgSecaoDAO.read(id);
            if (secao != null) {
                return ResponseEntity.ok(secao);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping
    public ResponseEntity<String> atualizarSecao(@RequestBody Secao secao) {
        try {
            pgSecaoDAO.update(secao);
            return ResponseEntity.ok("Seção atualizada com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar seção.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarSecao(@PathVariable int id) {
        try {
            pgSecaoDAO.delete(id);
            return ResponseEntity.ok("Seção deletada com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar seção.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Secao>> listarSecoes() {
        try {
            List<Secao> secoes = pgSecaoDAO.all();
            return ResponseEntity.ok(secoes);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Secao> obterSecaoPorNome(@PathVariable String nome) {
        try {
            Secao secao = pgSecaoDAO.getByNome(nome);
            if (secao != null) {
                return ResponseEntity.ok(secao);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

