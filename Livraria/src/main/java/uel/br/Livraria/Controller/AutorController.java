package uel.br.Livraria.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import uel.br.Livraria.DAO.PgAutorDAO;
import uel.br.Livraria.Model.Autor;

import java.sql.SQLException;

@RestController
@RequestMapping("/autores")
public class AutorController {
    private final PgAutorDAO pgAutorDAO;

    public AutorController(PgAutorDAO pgAutorDAO) {
        this.pgAutorDAO = pgAutorDAO;
    }

    @PostMapping
    public ResponseEntity<String> criarAutor(@RequestBody Autor autor) {
        try {
            pgAutorDAO.create(autor);
            return ResponseEntity.ok("Autor criado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar autor.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Autor> obterAutor(@PathVariable int id) {
        try {
            Autor autor = pgAutorDAO.read(id);
            if (autor != null) {
                return ResponseEntity.ok(autor);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping
    public ResponseEntity<String> atualizarAutor(@RequestBody Autor autor) {
        try {
            pgAutorDAO.update(autor);
            return ResponseEntity.ok("Autor atualizado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar autor.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarAutor(@PathVariable int id) {
        try {
            pgAutorDAO.delete(id);
            return ResponseEntity.ok("Autor deletado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar autor.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Autor>> listarAutores() {
        try {
            List<Autor> autores = pgAutorDAO.all();
            return ResponseEntity.ok(autores);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

