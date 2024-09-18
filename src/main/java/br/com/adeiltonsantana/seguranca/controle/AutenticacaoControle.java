package br.com.adeiltonsantana.seguranca.controle;

import br.com.adeiltonsantana.seguranca.modelo.JWTRVO;
import br.com.adeiltonsantana.seguranca.modelo.LoginRVO;
import br.com.adeiltonsantana.seguranca.modelo.SigninRVO;
import br.com.adeiltonsantana.seguranca.servico.AutenticacaoServico;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/autenticacao")
public class AutenticacaoControle {

    private final AutenticacaoServico autenticacaoServico;

    public AutenticacaoControle(AutenticacaoServico autenticacaoServico) {
        this.autenticacaoServico = autenticacaoServico;
    }

    @PreAuthorize("hasAuthority('PERMISSAO_TESTE')")
    @GetMapping("/teste")
    public ResponseEntity<String> teste() {
        return ResponseEntity.ok().body("Online");
    }

    @PostMapping("/login")
    public ResponseEntity<JWTRVO> login(@RequestBody LoginRVO loginRVO) {
        return ResponseEntity.ok().body(autenticacaoServico.login(loginRVO));
    }
    
    @PostMapping("/signin")
    public ResponseEntity<String> signin(@Valid @RequestBody SigninRVO signinRVO) {
        autenticacaoServico.salvarUsuario(signinRVO);
        return ResponseEntity.ok().body("Usuário cadastrado com sucesso");
    }
}
