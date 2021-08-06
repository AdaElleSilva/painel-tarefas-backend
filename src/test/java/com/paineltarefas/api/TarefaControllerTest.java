package com.paineltarefas.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paineltarefas.api.model.Projeto;
import com.paineltarefas.api.model.Tarefa;
import com.paineltarefas.api.model.Usuario;
import com.paineltarefas.api.repository.ProjetoRepository;
import com.paineltarefas.api.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Calendar;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TarefaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProjetoRepository projetoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Test
    public void listarTarefas_RetornarStatusCode200() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tarefas")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

    }

    @Test
    public void listarUnicaTarefa_RetornarStatusCode200() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tarefas/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void salvarTarefa_RetornarStatusCode201() throws Exception {

        Usuario usuario = usuarioRepository.findById(1).get();
        Projeto projeto = projetoRepository.findById(1).get();

        Tarefa tarefa = new Tarefa();
        tarefa.setNome("Nova Tarefa");
        tarefa.setDescricao("Nova Descrição");
        tarefa.setUsuario(usuario);
        tarefa.setProjeto(projeto);

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, 1); //adicionando 1 dia a data atual

        tarefa.setDataInicial(c.getTime());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/tarefas")
                .content(new ObjectMapper().writeValueAsString(tarefa))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());
    }

    //Validação da Data que deve ser maior que a data atual
    @Test
    public void salvaTarefaDataAtual_RetornarStatusCode400() throws Exception {

        Usuario usuario = usuarioRepository.findById(1).get();
        Projeto projeto = projetoRepository.findById(1).get();

        Tarefa tarefa = new Tarefa();
        tarefa.setNome("Nova Tarefa");
        tarefa.setDescricao("Nova Descrição");
        tarefa.setDataInicial(new Date());
        tarefa.setUsuario(usuario);
        tarefa.setProjeto(projeto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/tarefas")
                .content(new ObjectMapper().writeValueAsString(tarefa))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void atualizarTarefa_RetornarStatusCode200() throws Exception {

        Usuario usuario = usuarioRepository.findById(1).get();
        Projeto projeto = projetoRepository.findById(1).get();

        Tarefa tarefa = new Tarefa();
        tarefa.setId(1);
        tarefa.setNome("Tarefa Teste");
        tarefa.setDescricao("Nova Descrição Tarefa");
        tarefa.setDataInicial(new Date());
        tarefa.setUsuario(usuario);
        tarefa.setProjeto(projeto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/tarefas/{id}", 1)
                .content(new ObjectMapper().writeValueAsString(tarefa))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void deletarTarefa_RetornarStatusCode200() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/tarefas/{id}", 1))
                .andExpect(status().isOk());
    }
}
