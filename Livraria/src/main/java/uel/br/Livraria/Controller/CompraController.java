package uel.br.Livraria.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uel.br.Livraria.DAO.PgCompraDAO;
import uel.br.Livraria.Model.Autor;
import uel.br.Livraria.Model.Compra;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/compras")
public class CompraController {

    private final PgCompraDAO pgCompraDAO;

    public CompraController(PgCompraDAO pgCompraDAO) { this.pgCompraDAO = pgCompraDAO; }

    @PostMapping
    public ResponseEntity<String> criarCompra(@RequestBody Compra compra) {
        try {
            pgCompraDAO.create(compra);
            return ResponseEntity.ok("Compra criada com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar compra.");
        }
    }

    @GetMapping("/{notaNum}")
    public ResponseEntity<Compra> obterCompra(@PathVariable long notaNum) {
        try {
            Compra compra = pgCompraDAO.read(notaNum);
            if (compra != null) {
                return ResponseEntity.ok(compra);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{notaNum}")
    public ResponseEntity<String> atualizarCompra(@PathVariable long notaNum, @RequestBody Compra compra) {
        try {
            compra.setNum_Nota_Fiscal(notaNum);
            pgCompraDAO.update(compra);
            return ResponseEntity.ok("Compra atualizada com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar compra.");
        }
    }

    @DeleteMapping("/{notaNum}")
    public ResponseEntity<String> deletarCompra(@PathVariable long notaNum) {
        try {
            pgCompraDAO.delete(notaNum);
            return ResponseEntity.ok("Compra deletada com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar compra.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Compra>> listarCompras() {
        try {
            List<Compra> compras = pgCompraDAO.all();
            return ResponseEntity.ok(compras);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
