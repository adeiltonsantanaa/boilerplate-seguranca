package br.com.adeiltonsantana.seguranca.filtro;

import br.com.adeiltonsantana.seguranca.excecoes.TokenExpiradoExcecao;
import br.com.adeiltonsantana.seguranca.modelo.ErroRVO;
import br.com.adeiltonsantana.seguranca.servico.TokenServico;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FiltroAutenticacaoJwt extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(FiltroAutenticacaoJwt.class);

    private final TokenServico tokenServico;
    private final UserDetailsService userDetailsService;

    public FiltroAutenticacaoJwt(TokenServico tokenServico, UserDetailsService userDetailsService) {
        this.tokenServico = tokenServico;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            String token = tokenServico.decodificarToken(request);
            if (token != null && tokenServico.validarToken(token)) {
                String username = tokenServico.obterUsername(token);
                UserDetails usuarioDetalhes = userDetailsService.loadUserByUsername(username);
                var autenticacao = new UsernamePasswordAuthenticationToken(usuarioDetalhes, null, usuarioDetalhes.getAuthorities());
                autenticacao.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(autenticacao);
            }
            filterChain.doFilter(request, response);
        } catch (TokenExpiradoExcecao e) {
            logger.error("Erro de autenticação: {}", e.getMessage());
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, e);
        } catch (Exception e) {
            logger.error("Erro interno no filtro de autenticação", e);
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, e);
        }
    }

    private void setErrorResponse(HttpStatus status, HttpServletResponse response, Exception e) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        ErroRVO erro = new ErroRVO(e.getClass().getSimpleName(), e.getMessage());
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), erro);
    }
}
