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

import net.ausiasmarch.cuprodemy.entity.TipocomentarioEntity;
import net.ausiasmarch.cuprodemy.service.TipocomentarioService;

@RestController
@RequestMapping("/tipocomentario")
public class TipocomentarioController {
    
    @Autowired
    TipocomentarioService oTipocomentarioService;

    @GetMapping("/{id}")
    public ResponseEntity<TipocomentarioEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<TipocomentarioEntity>(oTipocomentarioService.get(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oTipocomentarioService.count(), HttpStatus.OK);
    }


    @GetMapping("")
    public ResponseEntity<Page<TipocomentarioEntity>> getPage(
            @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter) {
        return new ResponseEntity<Page<TipocomentarioEntity>>(oTipocomentarioService.getPage(oPageable, strFilter), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody TipocomentarioEntity oNewTipocomentarioEntity) {
        return new ResponseEntity<Long>(oTipocomentarioService.create(oNewTipocomentarioEntity), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Long> update(@RequestBody TipocomentarioEntity oTipocomentarioEntity) {
        return new ResponseEntity<Long>(oTipocomentarioService.update(oTipocomentarioEntity), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(oTipocomentarioService.delete(id), HttpStatus.OK);
    }

/*     @PostMapping("/generate")
    public ResponseEntity<TipocomentarioEntity> generate() {
        return new ResponseEntity<TipocomentarioEntity>(oTipocomentarioService.generateOne(), HttpStatus.OK);
    } */

}
