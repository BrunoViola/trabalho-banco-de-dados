package uel.br.Livraria.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uel.br.Livraria.DAO.PgClienteDAO;
import uel.br.Livraria.Model.Cliente;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final PgClienteDAO pgClienteDAO;

    public ClienteController(PgClienteDAO pgClienteDAO) { this.pgClienteDAO = pgClienteDAO; }

    @PostMapping
    public ResponseEntity<String> criarCliente(@RequestBody Cliente cliente) {
        try {
            pgClienteDAO.create(cliente);
            return ResponseEntity.ok("Cliente criado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar cliente.");
        }
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Cliente> obterCliente(@PathVariable String cpf) {
        try {
            Cliente cliente = pgClienteDAO.read(cpf);
            if (cliente != null) {
                return ResponseEntity.ok(cliente);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<String> atualizarCliente(@PathVariable String cpf, @RequestBody Cliente cliente) {
        try {
            cliente.setCPF(cpf);
            pgClienteDAO.update(cliente);
            return ResponseEntity.ok("Cliente atualizado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar cliente.");
        }
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<String> deletarCliente(@PathVariable String cpf) {
        try {
            pgClienteDAO.delete(cpf);
            return ResponseEntity.ok("Cliente deletado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar cliente.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        try {
            List<Cliente> clientes = pgClienteDAO.all();
            return ResponseEntity.ok(clientes);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Cliente> obterClientePorEmail(@PathVariable String email) {
        try {
            Cliente cliente = pgClienteDAO.getByEmail(email);
            if (cliente != null) {
                return ResponseEntity.ok(cliente);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
