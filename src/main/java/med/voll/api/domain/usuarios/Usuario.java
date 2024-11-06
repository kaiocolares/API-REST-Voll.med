package med.voll.api.domain.usuarios;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity(name = "Usuario") // Define a classe como uma entidade JPA com o nome "Usuario"
@Table(name = "usuarios") // Especifica o nome da tabela correspondente no banco de dados como "usuarios"
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id") //Gera os métodos equals e hashCode, considerando apenas o atributo "id" para comparação
/* O metodo hashCode retorna um valor numérico (um inteiro) que representa o objeto e é usado por coleções
que se baseiam em hashing para armazenar e recuperar objetos rapidamente, como HashSet e HashMap. Quando dois objetos
são considerados iguais pelo equals, eles devem retornar o mesmo valor de hashCode, para garantir o funcionamento
correto das coleções */
public class Usuario implements UserDetails { // Implementa UserDetails para integrar com a segurança do Spring Security

    @Id //marca campo como chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) //geração automática do id pelo banco de dados
    private Long id;
    private String login;
    private String senha;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Define as permissões de acesso para o usuário, neste caso, um papel fixo de "ROLE_USER"
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
