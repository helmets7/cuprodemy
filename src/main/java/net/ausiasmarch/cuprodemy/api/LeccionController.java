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

import net.ausiasmarch.cuprodemy.entity.LeccionEntity;
import net.ausiasmarch.cuprodemy.service.LeccionService;

@RestController
@RequestMapping("/leccion")
public class LeccionController {

    @Autowired
    LeccionService oLeccionService;


    @GetMapping("/{id}")
    public ResponseEntity<LeccionEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<LeccionEntity>(oLeccionService.get(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oLeccionService.count(), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Page<LeccionEntity>> getPage(
            @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter,
            @RequestParam(name = "curso", required = false) Long id_curso) {
        return new ResponseEntity<Page<LeccionEntity>>(oLeccionService.getPage(oPageable, strFilter, id_curso), HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Long> create(@RequestBody LeccionEntity oNewLeccionEntity) {
        return new ResponseEntity<Long>(oLeccionService.create(oNewLeccionEntity), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Long> update(@RequestBody LeccionEntity oLeccionEntity) {
        return new ResponseEntity<Long>(oLeccionService.update(oLeccionEntity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(oLeccionService.delete(id), HttpStatus.OK);
    }

/*     @PostMapping("/generate")
    public ResponseEntity<LeccionEntity> generate() {
        return new ResponseEntity<LeccionEntity>(oLeccionService.generateOne(), HttpStatus.OK);
    } */
}
