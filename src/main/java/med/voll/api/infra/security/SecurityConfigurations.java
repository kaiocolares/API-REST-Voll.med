package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Marca a classe como uma classe de configuração Spring, que define beans e configurações
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        // Define a cadeia de filtros de segurança para as requisições HTTP
        return http.csrf(csrf -> csrf.disable()) //Desabilita a proteção csrf
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Define a política de sessão como STATELESS, ou seja, sem estado, pois a autenticação será baseada em tokens (JWT)
                .authorizeHttpRequests(req -> {
                 req.requestMatchers("/login").permitAll();
                 // Permite o acesso irrestrito ao endpoint "/login" para que os usuários possam autenticar-se sem estar logados
                 req.anyRequest().authenticated();
                 // Requer autenticação para todas as outras requisições, garantindo que apenas usuários autenticados tenham acesso aos demais recursos da API
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                // Adiciona o filtro de segurança personalizado (securityFilter) antes do filtro padrão de autenticação UsernamePasswordAuthenticationFilter
                // Este filtro verifica a validade dos tokens JWT em todas as requisições autenticadas
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        // Configura o AuthenticationManager, que gerencia a autenticação dos usuários,
        // obtendo a configuração padrão do Spring Security
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Define o encoder de senhas como BCryptPasswordEncoder,
        // que usa o algoritmo BCrypt para criptografar senhas de forma segura
        return new BCryptPasswordEncoder();
    }
}
