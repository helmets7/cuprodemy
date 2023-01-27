package net.ausiasmarch.cuprodemy.api;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.cuprodemy.entity.ComentarioEntity;
import net.ausiasmarch.cuprodemy.service.ComentarioService;

@RestController
@RequestMapping("/comentario")
public class ComentarioController {
    
    @Autowired
    ComentarioService oComentarioService;
    
    @GetMapping("/{id}")
    public ResponseEntity<ComentarioEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<ComentarioEntity>(oComentarioService.get(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oComentarioService.count(), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Page<ComentarioEntity>> getPage(
            @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter,
            @RequestParam(value = "usuario", required = false) Long id_usuario,
            @RequestParam(value = "tipocomentario", required = false) Long id_tipocomentario,
            @RequestParam(value = "curso", required = false) Long id_curso) {
        return new ResponseEntity<Page<ComentarioEntity>>(oComentarioService.getPage(oPageable, strFilter, id_usuario, id_tipocomentario, id_curso), HttpStatus.OK);
    }  

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody ComentarioEntity oNewComentarioEntity) {
        return new ResponseEntity<Long>(oComentarioService.create(oNewComentarioEntity), HttpStatus.OK);
    }

    @PostMapping({"/generate/amount"})
    public ResponseEntity<Long> generate(@PathVariable(value="cantidad") Long cantidad){
        return new ResponseEntity<Long>(oComentarioService.generateSome(cantidad), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Long> update(@RequestBody ComentarioEntity oComentarioEntity) {
        return new ResponseEntity<Long>(oComentarioService.update(oComentarioEntity), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(oComentarioService.delete(id), HttpStatus.OK);
    }

    @PostMapping("/generate")
    public ResponseEntity<ComentarioEntity> generate() {
        return new ResponseEntity<ComentarioEntity>(oComentarioService.generateOne(), HttpStatus.OK);
    }
    
}
