package net.ausiasmarch.cuprodemy.api;


import org.springframework.data.domain.Sort;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import net.ausiasmarch.cuprodemy.entity.UsertypeEntity;
import net.ausiasmarch.cuprodemy.service.UsertypeService;



@RestController
@RequestMapping("/tipousuario")
public class UsertypeController {

    @Autowired
    UsertypeService oTipousuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<UsertypeEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<UsertypeEntity>(oTipousuarioService.get(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oTipousuarioService.count(), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Page<UsertypeEntity>> getPage(
            @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter) {
        return new ResponseEntity<Page<UsertypeEntity>>(oTipousuarioService.getPage(oPageable, strFilter), HttpStatus.OK);
    }
  
    @PostMapping
    public ResponseEntity<Long> create(@RequestBody UsertypeEntity oNewTipousuarioEntity) {
        return new ResponseEntity<Long>(oTipousuarioService.create(oNewTipousuarioEntity), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Long> update(@RequestBody UsertypeEntity oTipousuarioEntity) {
        return new ResponseEntity<Long>(oTipousuarioService.update(oTipousuarioEntity), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(oTipousuarioService.delete(id), HttpStatus.OK);
    }



}
