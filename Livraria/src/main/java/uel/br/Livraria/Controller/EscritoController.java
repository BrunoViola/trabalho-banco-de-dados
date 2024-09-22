package uel.br.Livraria.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uel.br.Livraria.DAO.PgEscritoDAO;
import uel.br.Livraria.Model.Escrito;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/escrito")
public class EscritoController {
    private final PgEscritoDAO pgEscritoDAO;

    public EscritoController(PgEscritoDAO pgEscritoDAO) { this.pgEscritoDAO = pgEscritoDAO; }

    @PostMapping
    public ResponseEntity<String> criarEscrito(@RequestBody Escrito escrito) {
        try {
            pgEscritoDAO.create(escrito);
            return ResponseEntity.ok("Escrito criado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar escrito.");
        }
    }

    @GetMapping("/{idAutor}/{ISBN}")
    public ResponseEntity<Escrito> obterEscrito(@PathVariable("idAutor") Integer idAutor, @PathVariable("ISBN") Long ISBN) {

        try {
            Escrito escrito = pgEscritoDAO.read(idAutor, ISBN);
            if (escrito != null) {
                return ResponseEntity.ok(escrito);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{ID_Autor}/{ISBN_Livro}")
    public ResponseEntity<String> deletarEscrito(@PathVariable Integer ID_Autor, @PathVariable Long ISBN_Livro) {
        try {
            pgEscritoDAO.delete(ID_Autor, ISBN_Livro);
            return ResponseEntity.ok("Relacionamento 'escrito' deletado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar relacionamento 'escrito'.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Escrito>> listarEscritos() {
        try {
            List<Escrito> escritos = pgEscritoDAO.all();
            return ResponseEntity.ok(escritos);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
