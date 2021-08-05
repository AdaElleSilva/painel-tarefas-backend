package com.paineltarefas.api.controller;

import com.paineltarefas.api.repository.ProjetoRepository;
import com.paineltarefas.api.model.Projeto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value="/api")
@Api(value="API Projeto")
@CrossOrigin(origins="*")
public class ProjetoController {

    @Autowired
    ProjetoRepository projetoRepository;

    @GetMapping("/projetos")
    @ApiOperation(value="Retorna uma lista de Projetos")
    public List<Projeto> listaProjetos(){
        return projetoRepository.findAll();
    }

    @GetMapping("/projetos/{id}")
    @ApiOperation(value="Retorna um único projeto")
    public Projeto listaUnicoProjeto(@PathVariable(value="id") Integer id) {
        return projetoRepository.findById(id).get();
    }

    @PostMapping("/projetos")
    @ApiOperation(value="Salva um Projeto")
    public ResponseEntity<Projeto> salvarProjeto(@Valid @RequestBody Projeto projeto) {

        Projeto projetoSalvo = projetoRepository.save(projeto);
        return ResponseEntity.created(null).body(projetoSalvo);
    }

    @DeleteMapping("/projetos/{id}")
    @ApiOperation(value="Deleta um Projeto")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarProjeto(@PathVariable(value="id") Integer id) {
        projetoRepository.deleteById(id);
    }

    @PutMapping("/projetos/{id}")
    @ApiOperation(value="Atualiza um Projeto")
    public ResponseEntity<Projeto> atualizarProjeto(@PathVariable Integer id, @Valid @RequestBody Projeto projeto) {
        Projeto projetoSalvo = projetoRepository.findById(id).get();
        BeanUtils.copyProperties(projeto, projetoSalvo, "id");
        projetoRepository.save(projetoSalvo);
        return ResponseEntity.ok(projetoSalvo);
    }

}
