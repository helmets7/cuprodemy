package net.ausiasmarch.cuprodemy.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.cuprodemy.entity.UsuarioCursoEntity;
import net.ausiasmarch.cuprodemy.exception.ResourceNotFoundException;
import net.ausiasmarch.cuprodemy.exception.ResourceNotModifiedException;
import net.ausiasmarch.cuprodemy.helper.ValidationHelper;
import net.ausiasmarch.cuprodemy.repository.UsuarioCursoRepository;

@Service
public class UsuarioCursoService {
    
    @Autowired
    UsuarioCursoRepository oUsuarioCursoRepository;

    @Autowired
    AuthService oAuthService;

    public void validate(Long id) {
        if (!oUsuarioCursoRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public UsuarioCursoEntity get(Long id) {
        try {
            return oUsuarioCursoRepository.findById(id).get();
        } catch (Exception ex) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public Long count() {
        return oUsuarioCursoRepository.count();
    }


    public Page<UsuarioCursoEntity> getPage(Pageable oPageable, String strFilter, Long id_usuario, Long id_curso) {
        ValidationHelper.validateRPP(oPageable.getPageSize());

        if (id_curso == null && id_usuario == null) {
            return oUsuarioCursoRepository.findAll(oPageable);
        } else if (id_usuario == null) {
            return oUsuarioCursoRepository.findByCurso(oPageable, id_curso);
        } else if (id_curso == null) {
            return oUsuarioCursoRepository.findByUsuario(oPageable, id_usuario);
        } else {
            return oUsuarioCursoRepository.findByUsuarioAndCurso(oPageable, id_usuario, id_curso);
        }
    }

    public Long create(UsuarioCursoEntity oNewUsuarioCursoEntity) {
        oNewUsuarioCursoEntity.setId(0L);
        return oUsuarioCursoRepository.save(oNewUsuarioCursoEntity).getId();
    }

    @Transactional
    public Long update(UsuarioCursoEntity oUsuarioCursoEntity) {
        validate(oUsuarioCursoEntity.getId());
        //oAuthService.OnlyAdminsOrOwnUsersData(oUsuarioCursoEntity.getId());
        return oUsuarioCursoRepository.save(oUsuarioCursoEntity).getId();
    }

    public Long delete(Long id) {
        if (oUsuarioCursoRepository.existsById(id)) {
            oUsuarioCursoRepository.deleteById(id);
            if (oUsuarioCursoRepository.existsById(id)) {
                throw new ResourceNotModifiedException("can't remove register " + id);
            } else {
                return id;
            }
        } else {
            throw new ResourceNotModifiedException("id " + id + " not exist");
        }
    }
/* 
    public UsuarioCursoEntity generateUsuarioCurso() {
        oAuthService.OnlyAdmins();
        UsuarioCursoEntity oCursoEntity = new UsuarioCursoEntity();
        //oUsuarioCursoEntity.set();
        return oCursoEntity;
    } */

/*     public UsuarioCursoEntity generateOne() {
        oAuthService.OnlyAdmins();
        return oUsuarioCursoRepository.save(generateUsuarioCurso());
    }

    public Long generateSome(Long amount) {
        oAuthService.OnlyAdmins();
        List<UsuarioCursoEntity> CursoToSave = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            CursoToSave.add(generateUsuarioCurso());
        }
        oUsuarioCursoRepository.saveAll(CursoToSave);
        return oUsuarioCursoRepository.count();
    }
 */

    
}
