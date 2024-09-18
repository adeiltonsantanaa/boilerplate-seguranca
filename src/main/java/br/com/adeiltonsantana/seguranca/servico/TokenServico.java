package br.com.adeiltonsantana.seguranca.servico;

import br.com.adeiltonsantana.seguranca.excecoes.TokenExpiradoExcecao;
import br.com.adeiltonsantana.seguranca.modelo.JWTRVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;

@Service
public class TokenServico {

    private final static String BEARER = "Bearer ";

    @Value("${jwt.chave}")
    private String CHAVE;

    @Value("${jwt.expiracao}")
    private String EXPIRACAO;
    
    public JWTRVO codificarToken(String nomeUsuario) {
        final Date dataCriacao = new Date(System.currentTimeMillis());
        final Date dataExpiracao = new Date(System.currentTimeMillis() + Long.parseLong(EXPIRACAO));
        String token = Jwts.builder()
                .setClaims(null)
                .setSubject(nomeUsuario)
                .setIssuedAt(dataCriacao)
                .setExpiration(dataExpiracao)
                .signWith(hmacShaKeyFor(CHAVE.getBytes()), SignatureAlgorithm.HS256)
                .compact();
        return new JWTRVO(token, BEARER, dataCriacao, dataExpiracao);
    }
    
    public String decodificarToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(7);
        }
        return null;
    }
    
    public boolean validarToken(String token) {
        try {
            return !isTokenExpirado(token);
        } catch (JwtException | IllegalArgumentException e) {
            throw new TokenExpiradoExcecao("Token inválido ou expirado");
        }
    }
    
    public String obterUsername(String token) {
        return getUsuario(token);
    }

    private boolean isTokenExpirado(String token) {
        return getClaim(token, Claims.EXPIRATION, Date.class).before(new Date());
    }

    private String getUsuario(String token) {
        return getClaim(token, Claims.SUBJECT, String.class);
    }

    private <T> T getClaim(String token, String claim, Class<T> tipo) {
        return Jwts.parserBuilder().setSigningKey(hmacShaKeyFor(CHAVE.getBytes()))
                .build().parseClaimsJws(token).getBody().get(claim, tipo);
    }
}