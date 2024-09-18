package br.com.adeiltonsantana.seguranca.entidade;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {

    public Usuario(String nomeUsuario, String senha, String primeiroNome, String ultimoNome) {
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.primeiroNome = primeiroNome;
        this.ultimoNome = ultimoNome;
    }
    
    public Usuario() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String nomeUsuario;
    @Column(nullable = false)
    private String senha;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_papel", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "papel_id"))
    private Set<Papel> papeis;
    private String primeiroNome;
    private String ultimoNome;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.addAll(papeis);
        authorities.addAll(papeis.stream()
                .flatMap(papel -> papel.getPermissoes().stream())
                .collect(Collectors.toSet()));
        return authorities;
    }
    
    

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.nomeUsuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
