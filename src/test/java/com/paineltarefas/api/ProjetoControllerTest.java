package com.paineltarefas.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paineltarefas.api.model.Projeto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjetoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void listarProjetos_RetornarStatusCode200() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/projetos")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

    }

    @Test
    public void listarUnicoProjeto_RetornarStatusCode200() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/projetos/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void salvarProjeto_RetornarStatusCode201() throws Exception {

        Projeto projeto = new Projeto();
        projeto.setNome("Novo");
        projeto.setDescricao("Nova Descrição");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/projetos")
                .content(new ObjectMapper().writeValueAsString(projeto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());
    }

    @Test
    public void atualizarProjeto_RetornarStatusCode200() throws Exception {

        Projeto projeto = new Projeto();
        projeto.setId(1);
        projeto.setNome("Teste");
        projeto.setDescricao("Nova Descrição Teste");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/projetos/{id}", 1)
                .content(new ObjectMapper().writeValueAsString(projeto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void deletarProjeto_RetornarStatusCode200() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/projetos/{id}", 1))
                .andExpect(status().isOk());
    }
}
