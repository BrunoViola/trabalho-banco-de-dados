package uel.br.Livraria.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import uel.br.Livraria.DAO.PgPertenceDAO;
import uel.br.Livraria.Model.Pertence;

import java.sql.SQLException;

@RestController
@RequestMapping("/pertence")
public class PertenceController {
    private final PgPertenceDAO pgPertenceDAO;

    public PertenceController(PgPertenceDAO pgPertenceDAO) {
        this.pgPertenceDAO = pgPertenceDAO;
    }

    @PostMapping
    public ResponseEntity<String> criarPertence(@RequestBody Pertence pertence) {
        try {
            pgPertenceDAO.create(pertence);
            return ResponseEntity.ok("Relacionamento 'pertence' criado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar relacionamento 'pertence'.");
        }
    }

    @GetMapping("/{idGenero}/{ISBN}")
    public ResponseEntity<Pertence> obterPertence(@PathVariable("idGenero") Integer idGenero, @PathVariable("ISBN") Long ISBN) {

        try {
            Pertence pertence = pgPertenceDAO.read(idGenero, ISBN);
            if (pertence != null) {
                return ResponseEntity.ok(pertence);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @PutMapping("/{idGenero}/{ISBN}")
    public ResponseEntity<String> atualizarPertence(
      @PathVariable("idGenero") Integer idGenero,
      @PathVariable("ISBN") Long ISBN,
      @RequestBody Pertence pertence) {
      try {
         pgPertenceDAO.update(pertence);
         return ResponseEntity.ok("Atualizado com sucesso");
      } catch (SQLException e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar");
      } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado");
      }
    }

    @DeleteMapping("/{idGenero}/{ISBN}")
    public ResponseEntity<String> deletar(@PathVariable("idGenero") Integer idGenero, @PathVariable("ISBN") Long ISBN) {
        try {
            pgPertenceDAO.delete(idGenero, ISBN);
            return ResponseEntity.ok("Relacionamento 'pertence' deletado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar relacionamento 'pertence'.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Pertence>> listarPertence() {
        try {
            List<Pertence> pertence = pgPertenceDAO.all();
            return ResponseEntity.ok(pertence);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

