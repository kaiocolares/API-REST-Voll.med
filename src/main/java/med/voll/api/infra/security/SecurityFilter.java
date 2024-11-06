package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Filtro interno que intercepta a requisição HTTP para verificar e autenticar o token JWT

        var tokenJWT = recuperarToken(request);
        // Extrai o token JWT do cabeçalho "Authorization" da requisição (caso esteja presente)

        if (tokenJWT != null) {
            var subject = tokenService.getSubject(tokenJWT);
            var usuario = repository.findByLogin(subject); // Com base no subject(login) extraído, busca se ele existe no banco de dados

            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            // Cria um objeto de autenticação do Spring com o usuário autenticado, sem credenciais adicionais, e com as permissões do usuário
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // Armazena o objeto de autenticação no contexto de segurança do Spring para autorizar o acesso à requisição
        }

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        // Metodo auxiliar para extrair o token JWT do cabeçalho "Authorization" da requisição
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader!=null) {
            return authorizationHeader.replace("Bearer ", "");
        }

        return null;
    }
}
