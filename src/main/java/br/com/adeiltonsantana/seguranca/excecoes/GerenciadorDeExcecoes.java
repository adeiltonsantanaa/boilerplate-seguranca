package br.com.adeiltonsantana.seguranca.excecoes;

import br.com.adeiltonsantana.seguranca.modelo.ErroRVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GerenciadorDeExcecoes {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErroRVO> gerenciarExcecaoGlobal(Throwable e) {
        return ResponseEntity.internalServerError().body(new ErroRVO(e.getClass().getSimpleName(), e.getMessage()));
    }

    @ExceptionHandler(UsuarioNaoEncontradoExcecao.class)
    public ResponseEntity<ErroRVO> usuarioNaoEncontradoExcecao(UsuarioNaoEncontradoExcecao e) {
        return ResponseEntity.internalServerError().body(new ErroRVO(e.getClass().getSimpleName(), e.getMessage()));
    }

    @ExceptionHandler(TokenExpiradoExcecao.class)
    public ResponseEntity<ErroRVO> tokenExpiradoExcecao(TokenExpiradoExcecao e) {
        return new ResponseEntity<>(new ErroRVO(e.getClass().getSimpleName(), e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(UsuarioSenhaInvalidoExcecao.class)
    public ResponseEntity<ErroRVO> usuarioSenhaInvalidoExcecao(UsuarioSenhaInvalidoExcecao e) {
        return new ResponseEntity<>(new ErroRVO(e.getClass().getSimpleName(), e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroRVO> usuarioSenhaInvalidoExcecao(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(new ErroRVO(e.getClass().getSimpleName(), e.getFieldError().getDefaultMessage()), HttpStatus.BAD_REQUEST);
    }

}
