package uel.br.Livraria.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import uel.br.Livraria.DAO.DAO;
import uel.br.Livraria.DAO.DAOFactory;
import uel.br.Livraria.DAO.PgPossuiDAO;

import uel.br.Livraria.Model.Possui;
import uel.br.Livraria.Model.Compra;
import uel.br.Livraria.Model.Livro;

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
            Compra compra = new Compra();
            compra.setNum_Nota_Fiscal(possui.getCompra().getNum_Nota_Fiscal()); // Supondo que essa seja a chave no JSON
            possui.setCompra(compra);
            
            Livro livro = new Livro();
            livro.setISBN(possui.getLivro().getISBN());
            possui.setLivro(livro);
            pgPossuiDAO.create(possui);
            return ResponseEntity.ok("Relacionamento 'possui' criado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar relacionamento 'possui'.");
        }
    }

    @GetMapping("/compra/{numNotaFiscal}/livro/{ISBN}")
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
    
      @PutMapping("/compra/{numNotaFiscal}/livro/{ISBN}")
      public ResponseEntity<String> atualizarPossui(
              @PathVariable("numNotaFiscal") Long numNotaFiscal,
              @PathVariable("ISBN") Long ISBN,
              @RequestBody Possui possui) {
          try (DAOFactory daoFactory = DAOFactory.getInstance()) {
              DAO<Compra, Long> compraDAO = daoFactory.getCompraDAO();
              Compra compra = compraDAO.read(numNotaFiscal);
              
              if (compra == null) {
                  return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Compra não encontrada.");
              }
      
              DAO<Livro, Long> livroDAO = daoFactory.getLivroDAO();
              Livro livro = livroDAO.read(ISBN);
              
              if (livro == null) {
                  return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado.");
              }
      
              possui.setCompra(compra);
              possui.setLivro(livro);
      
              pgPossuiDAO.update(possui);
              return ResponseEntity.ok("Atualizado com sucesso");
          } catch (SQLException e) {
              return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar");
          } catch (Exception e) {
              return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado");
          }
      }
    

    @DeleteMapping("/compra/{numNotaFiscal}/livro/{ISBN}")
    public ResponseEntity<String> deletarSecao(@PathVariable("numNotaFiscal") Long numNotaFiscal, @PathVariable("ISBN") Long ISBN) {
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

