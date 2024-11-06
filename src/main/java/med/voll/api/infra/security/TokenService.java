package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import med.voll.api.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            // Define o algoritmo de assinatura HMAC256 e a chave secreta ("senha") para assinar o token.
            // A chave deve ser protegida em produção, usando variáveis de ambiente ou serviços seguros.
            return JWT.create()
                    .withIssuer("API Voll.med")  // Define o emissor do token, indicando a origem ("API Voll.med")
                    .withSubject(usuario.getLogin()) // Define o assunto do token como o login do usuário autenticado
                    .withClaim("id", usuario.getId()) // Adiciona uma claim personalizada, incluindo o ID do usuário no token
                    .withExpiresAt(dataExpiracao()) // Define a data e hora de expiração do token (aqui, 2 horas após a geração)
                    .sign(algoritmo); // Assina o token com o algoritmo HMAC256 e a chave secreta
        }
        catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT ", exception);
        }
    }

    public String getSubject(String tokenJWT) { // Metodo para extrair o subject do token, ou seja o login
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API Voll.med") // Especifica o emissor esperado do token
                    .build()
                    .verify(tokenJWT) // Verifica a assinatura e a validade do token
                    .getSubject(); //remove o subject da token
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token JWT inválido ou expirado");
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
        // Retorna a data de expiração no fuso horário GMT-3, configurando uma validade específica para o token (2horas)
    }
}
