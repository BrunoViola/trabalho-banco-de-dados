package uel.br.Livraria.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uel.br.Livraria.DAO.PgGraficoDAO;
import uel.br.Livraria.Model.Grafico;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/dados")
@CrossOrigin(origins = "http://localhost:3000")
public class GraficoController {
    private final PgGraficoDAO pgGraficoDAO;

    public GraficoController(PgGraficoDAO pgGraficoDAO){
        this.pgGraficoDAO = pgGraficoDAO;
    }

    @GetMapping("/grafico1")
    public ResponseEntity<List<Grafico>> obterGrafico1() {
        try {
            List<Grafico> grafico = pgGraficoDAO.getGrafico1();
            if (grafico != null) {
                return ResponseEntity.ok(grafico);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/grafico2")
    public ResponseEntity<List<Grafico>> obterGrafico2() {
        try {
            List<Grafico> grafico = pgGraficoDAO.getGrafico2();
            if (grafico != null) {
                return ResponseEntity.ok(grafico);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/grafico3")
    public ResponseEntity<List<Grafico>> obterGrafico3() {
        try {
            List<Grafico> grafico = pgGraficoDAO.getGrafico3();
            if (grafico != null) {
                return ResponseEntity.ok(grafico);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/grafico4")
    public ResponseEntity<List<Grafico>> obterGrafico4() {
        try {
            List<Grafico> grafico = pgGraficoDAO.getGrafico4();
            if (grafico != null) {
                return ResponseEntity.ok(grafico);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/grafico5")
    public ResponseEntity<List<Grafico>> obterGrafico5() {
        try {
            List<Grafico> grafico = pgGraficoDAO.getGrafico5();
            if (grafico != null) {
                return ResponseEntity.ok(grafico);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/grafico6")
    public ResponseEntity<List<Grafico>> obterGrafico6() {
        try {
            List<Grafico> grafico = pgGraficoDAO.getGrafico6();
            if (grafico != null) {
                return ResponseEntity.ok(grafico);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/grafico7")
    public ResponseEntity<List<Grafico>> obterGrafico7() {
        try {
            List<Grafico> grafico = pgGraficoDAO.getGrafico7();
            if (grafico != null) {
                return ResponseEntity.ok(grafico);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
