package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.usuarios.DadosAutenticacao;
import med.voll.api.domain.usuarios.Usuario;
import med.voll.api.infra.security.DadosTokenJWT;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired //Essa notação resulta em uma injeção automatica pelo framework da funcionalidade seguinte
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        // Recebe os dados de autenticação do usuário (login e senha) no corpo da requisição e valida os dados
        var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        // Cria um objeto UsernamePasswordAuthenticationToken com o login e senha para autenticar o usuário
        var authentication = manager.authenticate(token);
        // Usa o AuthenticationManager para autenticar o usuário com o token criado
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
        // Retorna uma resposta HTTP 200 (OK) contendo o token gerado para o usuário autenticado
        // 1. "authentication.getPrincipal()" retorna o objeto principal da autenticação, que representa o usuário autenticado.
        // 2. Faz um cast para "Usuario", assumindo que o objeto principal é uma instância de Usuario (a classe que representa o usuário autenticado).
        // 3. Chama o metodo "gerarToken" do tokenService, passando o objeto Usuario autenticado como argumento.
        // 4. "gerarToken" cria um token JWT (ou similar) para o usuário, que será usado para autenticação em futuras requisições.
        // 5. Retorna o token no corpo da resposta, permitindo que o cliente o use para acessar endpoints protegidos.
    }
}
