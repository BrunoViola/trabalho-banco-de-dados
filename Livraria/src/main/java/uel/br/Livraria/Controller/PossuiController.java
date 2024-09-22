package uel.br.Livraria.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import uel.br.Livraria.DAO.*;

import uel.br.Livraria.Model.Possui;

import java.sql.SQLException;

@RestController
@RequestMapping("/possui")
public class PossuiController {
    private final PgPossuiDAO pgPossuiDAO;

    public PossuiController(PgPossuiDAO pgPossuiDAO) {
        this.pgPossuiDAO = pgPossuiDAO;
    }

    @PostMapping
    public ResponseEntity<String> criarPossui(@RequestBody Possui possui) {
        try {
            pgPossuiDAO.create(possui);
            return ResponseEntity.ok("Relacionamento 'possui' criado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar relacionamento 'possui'.");
        }
    }

    @GetMapping("/{numNotaFiscal}/{ISBN}")
    public ResponseEntity<Possui> obterPossui(@PathVariable("numNotaFiscal") Long numNotaFiscal, @PathVariable("ISBN") Long ISBN) {

        try {
            Possui possui = pgPossuiDAO.read(numNotaFiscal, ISBN);
            if (possui != null) {
                return ResponseEntity.ok(possui);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @PutMapping
    public ResponseEntity<String> atualizarPossui(@RequestBody Possui possui) {
      try {
          pgPossuiDAO.update(possui);
          return ResponseEntity.ok("Atualizado com sucesso");
      } catch (SQLException e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar");
      } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado");
      }
    }

    @DeleteMapping("/{numNotaFiscal}/{ISBN}")
    public ResponseEntity<String> deletarPossui(@PathVariable("numNotaFiscal") Long numNotaFiscal, @PathVariable("ISBN") Long ISBN) {
        try {
            pgPossuiDAO.delete(numNotaFiscal, ISBN);
            return ResponseEntity.ok("Relacionamento 'possui' deletado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar relacionamento 'possui'.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Possui>> listarPossui() {
        try {
            List<Possui> possui = pgPossuiDAO.all();
            return ResponseEntity.ok(possui);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

