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


import net.ausiasmarch.cuprodemy.entity.CursoEntity;
import net.ausiasmarch.cuprodemy.service.CursoService;

@RestController
@RequestMapping("/curso")
public class CursoController {
    
    @Autowired
    CursoService oCursoService;

    @GetMapping("/{id}")
    public ResponseEntity<CursoEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<CursoEntity>(oCursoService.get(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oCursoService.count(), HttpStatus.OK);
    }


    @GetMapping("")
    public ResponseEntity<Page<CursoEntity>> getPage(
            @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter,
            @RequestParam(value = "leccion", required = false) Long id_leccion) {
        return new ResponseEntity<Page<CursoEntity>>(oCursoService.getPage(oPageable, strFilter, id_leccion), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody CursoEntity oNewCursoEntity) {
        return new ResponseEntity<Long>(oCursoService.create(oNewCursoEntity), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Long> update(@RequestBody CursoEntity oCursoEntity) {
        return new ResponseEntity<Long>(oCursoService.update(oCursoEntity), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(oCursoService.delete(id), HttpStatus.OK);
    }
    

    

}
