package uel.br.Livraria.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uel.br.Livraria.DAO.PgEditoraDAO;
import uel.br.Livraria.Model.Editora;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/editoras")
public class EditoraController {
    private final PgEditoraDAO pgEditoraDAO;

    public EditoraController(PgEditoraDAO pgEditoraDAO) { this.pgEditoraDAO = pgEditoraDAO; }

    @PostMapping
    public ResponseEntity<String> criarEditora(@RequestBody Editora editora) {
        try {
            pgEditoraDAO.create(editora);
            return ResponseEntity.ok("Editora criada com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar editora.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Editora> obterEditora(@PathVariable int id) {
        try {
            Editora editora = pgEditoraDAO.read(id);
            if (editora != null) {
                return ResponseEntity.ok(editora);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarEditora(@PathVariable int id, @RequestBody Editora editora) {
        try {
            editora.setID(id);
            pgEditoraDAO.update(editora);
            return ResponseEntity.ok("Editora atualizada com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar editora.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarEditora(@PathVariable int id) {
        try {
            pgEditoraDAO.delete(id);
            return ResponseEntity.ok("Editora deletada com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar editora.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Editora>> listarEditoras() {
        try {
            List<Editora> editoras = pgEditoraDAO.all();
            return ResponseEntity.ok(editoras);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
