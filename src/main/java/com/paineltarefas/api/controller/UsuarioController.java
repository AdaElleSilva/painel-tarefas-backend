package com.paineltarefas.api.controller;

import com.paineltarefas.api.repository.UsuarioRepository;
import com.paineltarefas.api.model.Usuario;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(value="/api")
@Api(value="API Usuário")
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("/usuarios")
    @ApiOperation(value="Retorna uma lista de Usuários")
    public List<Usuario> listaUsuarios(){
        return usuarioRepository.findAll();
    }

    @GetMapping("/usuarios/{id}")
    @ApiOperation(value="Retorna um único usuário")
    public Usuario listaUnicoUsuario(@PathVariable(value="id") Integer id) {
        return usuarioRepository.findById(id).get();
    }

    @PostMapping("/usuarios")
    @ApiOperation(value="Salva um Usuário")
    public ResponseEntity<Usuario> salvarUsuario(@Valid @RequestBody Usuario usuario) throws URISyntaxException {

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return ResponseEntity.created(new URI("/usuarios/" + usuarioSalvo.getId())).body(usuarioSalvo);

    }

    @DeleteMapping("/usuarios/{id}")
    @ApiOperation(value="Deleta um Usuário")
    public void deletarUsuario(@PathVariable(value="id") Integer id) {

        usuarioRepository.deleteById(id);

    }

    @PutMapping("/usuarios/{id}")
    @ApiOperation(value="Atualiza um Usuário")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Integer id, @Valid @RequestBody Usuario usuario) {

        Usuario usuarioSalvo = usuarioRepository.findById(id).get();
        BeanUtils.copyProperties(usuario, usuarioSalvo, "id");
        usuarioRepository.save(usuarioSalvo);
        return ResponseEntity.ok(usuarioSalvo);

    }
}
