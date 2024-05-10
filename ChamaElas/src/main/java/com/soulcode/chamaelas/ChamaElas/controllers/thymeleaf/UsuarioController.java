package com.soulcode.chamaelas.ChamaElas.controllers.thymeleaf;

import com.soulcode.chamaelas.ChamaElas.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/cadastro-usuario")
    public String mostrarPaginaCadastroUsuario() {

        return "cadastro-usuario";
    }

    @PostMapping("/cadastro-usuario")
    public String cadastrarNovoUsuario(@RequestParam("nome") String nome,
                                       @RequestParam("email") String email,
                                       @RequestParam("senha") String senha,
                                       @RequestParam("confirmaSenha") String confirmacaoSenha,
                                       @RequestParam("funcao") String funcao,
                                       @RequestParam(value = "endereco", required = false) String endereco,
                                       @RequestParam(value = "telefone", required = false) String telefone,
                                       @RequestParam(value = "setor", required = false) String setor,
                                       Model model) {
        try {

            String token = usuarioService.gerarToken();

            // Adicionar o token ao modelo
            model.addAttribute("tokenRecebido", token);

            // Chamar o serviço para cadastrar o novo usuário
            usuarioService.cadastrarNovoUsuario(nome, email, senha, confirmacaoSenha, funcao, endereco, telefone, setor, token, model);

            // Retornar para a página de login
            return "login-usuario";
        } catch (DataIntegrityViolationException | IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "cadastro-usuario";
        } catch (Exception e) {
            model.addAttribute("error", "Ocorreu um erro ao processar o cadastro do usuário.");
            return "cadastro-usuario";
        }
    }
}
