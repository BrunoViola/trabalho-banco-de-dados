package uel.br.Livraria.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import uel.br.Livraria.DAO.PgLivroDAO;
import uel.br.Livraria.Model.Livro;

import java.sql.SQLException;

@RestController
@RequestMapping("/livros")
public class LivroController {
    private final PgLivroDAO pgLivroDAO;

    public LivroController(PgLivroDAO pgLivroDAO) {
        this.pgLivroDAO = pgLivroDAO;
    }

    @PostMapping
    public ResponseEntity<String> criarLivro(@RequestBody Livro livro) {
        try {
            pgLivroDAO.create(livro);
            return ResponseEntity.ok("Livro criado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar livro.");
        }
    }

    @GetMapping("/{ISBN}")
    public ResponseEntity<Livro> obterLivro(@PathVariable Long ISBN) {
        try {
            Livro livro = pgLivroDAO.read(ISBN);
            if (livro != null) {
                return ResponseEntity.ok(livro);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{ISBN}")
    public ResponseEntity<String> atualizarLivro(@PathVariable Long ISBN, @RequestBody Livro livro) {
        try {
            livro.setISBN(ISBN);
            pgLivroDAO.update(livro);
            return ResponseEntity.ok("Livro atualizado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar Livro.");
        }
    }

    @DeleteMapping("/{ISBN}")
    public ResponseEntity<String> deletarLivro(@PathVariable Long ISBN) {
        try {
            pgLivroDAO.delete(ISBN);
            return ResponseEntity.ok("Livro deletado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar Livro.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Livro>> listarLivros() {
        try {
            List<Livro> livros = pgLivroDAO.all();
            return ResponseEntity.ok(livros);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

