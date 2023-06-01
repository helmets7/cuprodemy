package net.ausiasmarch.cuprodemy.api;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;

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

import net.ausiasmarch.cuprodemy.entity.UsuarioCursoEntity;
import net.ausiasmarch.cuprodemy.service.UsuarioCursoService;

@RestController
@RequestMapping("/usuario_curso")
public class UsuarioCursoController {
    
    @Autowired
    UsuarioCursoService oUsuarioCursoService;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioCursoEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<UsuarioCursoEntity>(oUsuarioCursoService.get(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oUsuarioCursoService.count(), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Page<UsuarioCursoEntity>>getPage(
        @ParameterObject @PageableDefault(page =0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
        @RequestParam(name = "filter", required = false) String strFilter,
        @RequestParam(value = "usuario", required = false) Long id_usuario,
        @RequestParam(value = "curso", required = false) Long id_curso){
            return new ResponseEntity<Page<UsuarioCursoEntity>>(oUsuarioCursoService.getPage(oPageable, strFilter, id_usuario, id_curso), HttpStatus.OK);
        }
    

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody UsuarioCursoEntity oNewCursoEntity) {
        return new ResponseEntity<Long>(oUsuarioCursoService.create(oNewCursoEntity), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Long> update(@RequestBody UsuarioCursoEntity oCursoEntity) {
        return new ResponseEntity<Long>(oUsuarioCursoService.update(oCursoEntity), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(oUsuarioCursoService.delete(id), HttpStatus.OK);
    }

    
    
}
