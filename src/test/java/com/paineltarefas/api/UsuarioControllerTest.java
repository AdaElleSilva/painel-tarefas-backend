package com.paineltarefas.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paineltarefas.api.model.Usuario;
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
public class UsuarioControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void listarUsuarios_RetornarStatusCode200() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/usuarios")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

    }

    @Test
    public void listarUnicoUsuario_RetornarStatusCode200() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/usuarios/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void salvarUsuario_RetornarStatusCode201() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setNome("Teste");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/usuarios")
                .content(new ObjectMapper().writeValueAsString(usuario))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());
    }

    @Test
    public void atualizarUsuario_RetornarStatusCode200() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNome("Teste");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/usuarios/{id}", 1)
                .content(new ObjectMapper().writeValueAsString(usuario))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void deletarUsuario_RetornarStatusCode200() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/usuarios/{id}", 1))
                .andExpect(status().isOk());
    }




}
