package com.soulcode.chamaelas.ChamaElas.services;

import com.soulcode.chamaelas.ChamaElas.models.ChamadoModel;
import com.soulcode.chamaelas.ChamaElas.models.UsuarioModel;
import com.soulcode.chamaelas.ChamaElas.repositories.ChamadoRepository;
import com.soulcode.chamaelas.ChamaElas.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private ChamadoService chamadoService;

    @Autowired
    private ChamadoRepository chamadoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Exibe a quantidade dos chamados EM ABERTO na página do admin
    public int getQuantidadeChamadosEmAberto() {
        return chamadoService.getChamadosEmAberto().size();
    }

    // Exibe a quantidade dos chamados EM ANDAMENTO na página do admin
    public int getQuantidadeChamadosPorStatus(ChamadoModel.TicketStatus status) {
        return chamadoRepository.findByStatus(status).size();
    }

    // Lista chamados por prioridade
    public List<ChamadoModel> getChamadosPorPrioridade(ChamadoModel.Prioridade prioridade) {
        return chamadoRepository.findByPrioridade(prioridade);
    }

    // Lista chamados por setores
    public List<ChamadoModel> getChamadosPorSetor(String setor) {
        return chamadoRepository.findBySetor(setor);
    }

    // Desativar usuário pelo ID
    public void inativarUsuarioPorID(Long id) {
        Optional<UsuarioModel> optionalUsuario = usuarioRepository.findById(id);
        optionalUsuario.ifPresent(usuario -> {
            usuario.desativarUsuario();
            usuarioRepository.save(usuario);
        });
    }

}