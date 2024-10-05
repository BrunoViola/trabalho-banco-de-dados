package uel.br.Livraria.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import uel.br.Livraria.DAO.PgGeneroDAO;
import uel.br.Livraria.Model.Genero;

import java.sql.SQLException;

@RestController
@RequestMapping("/generos")
public class GeneroController {
    private final PgGeneroDAO pgGeneroDAO;

    public GeneroController(PgGeneroDAO pgGeneroDAO) {
        this.pgGeneroDAO = pgGeneroDAO;
    }

    @PostMapping
    public ResponseEntity<String> criarGenero(@RequestBody Genero genero) {
        try {
            pgGeneroDAO.create(genero);
            return ResponseEntity.ok("Gênero criado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar gênero.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genero> obterGenero(@PathVariable int id) {
        try {
            Genero genero = pgGeneroDAO.read(id);
            if (genero != null) {
                return ResponseEntity.ok(genero);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping
    public ResponseEntity<String> atualizarGenero(@RequestBody Genero genero) {
        try {
            pgGeneroDAO.update(genero);
            return ResponseEntity.ok("Gênero atualizado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar gênero.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarGenero(@PathVariable int id) {
        try {
            pgGeneroDAO.delete(id);
            return ResponseEntity.ok("Gênero deletado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar gênero.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Genero>> listarGeneros() {
        try {
            List<Genero> generos = pgGeneroDAO.all();
            return ResponseEntity.ok(generos);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/nome-secao_id/{nome}/{secao_id}")
    public ResponseEntity<Genero> obterGeneroPorNomeSecaoId(@PathVariable String nome, @PathVariable int secao_id) {
        try {
            Genero genero = pgGeneroDAO.getByNomeIDSecao(nome, secao_id);
            if (genero != null) {
                return ResponseEntity.ok(genero);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}

