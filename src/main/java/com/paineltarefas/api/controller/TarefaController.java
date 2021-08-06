package com.paineltarefas.api.controller;

import com.paineltarefas.api.repository.TarefaRepository;
import com.paineltarefas.api.model.Erro;
import com.paineltarefas.api.model.Tarefa;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value="/api")
@Api(value="API Tarefa")
public class TarefaController {

    @Autowired
    TarefaRepository tarefaRepository;

    @GetMapping("/tarefas")
    @ApiOperation(value="Retorna uma lista de Tarefas")
    public List<Tarefa> listaTarefas(){
        return tarefaRepository.findAll();
    }

    @GetMapping("/tarefas/{id}")
    @ApiOperation(value="Retorna uma única tarefa")
    public Tarefa listaUnicaTarefa(@PathVariable(value="id") Integer id) {
        return tarefaRepository.findById(id).get();
    }

    @PostMapping("/tarefas")
    @ApiOperation(value="Salva uma Tarefa")
    public ResponseEntity<?> salvarTarefa(@Valid @RequestBody Tarefa tarefa) throws URISyntaxException {


        if(tarefa.getDataInicial().after(new Date())) {
            Tarefa tarefaSalva = tarefaRepository.save(tarefa);
            return ResponseEntity.created(new URI("/tarefas/" + tarefaSalva.getId())).body(tarefaSalva);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Erro("A data inicial deve ser maior do que a data atual"));

        }
    }

    @DeleteMapping("/tarefas/{id}")
    @ApiOperation(value="Deleta uma Tarefa")
    public void deletarTarefa(@PathVariable(value="id") Integer id) {
        tarefaRepository.deleteById(id);
    }

    @PutMapping("/tarefas/{id}")
    @ApiOperation(value="Atualiza uma Tarefa")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Integer id, @Valid @RequestBody Tarefa tarefa) {
        Tarefa tarefaSalva = tarefaRepository.findById(id).get();
        BeanUtils.copyProperties(tarefa, tarefaSalva, "id");
        tarefaRepository.save(tarefaSalva);
        return ResponseEntity.ok(tarefaSalva);
    }
}
