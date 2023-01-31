package net.ausiasmarch.cuprodemy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ausiasmarch.cuprodemy.entity.ComentarioEntity;
import net.ausiasmarch.cuprodemy.exception.CannotPerformOperationException;
import net.ausiasmarch.cuprodemy.exception.ResourceNotFoundException;
import net.ausiasmarch.cuprodemy.exception.ResourceNotModifiedException;
import net.ausiasmarch.cuprodemy.helper.RandomHelper;
import net.ausiasmarch.cuprodemy.helper.TipocomentarioHelper;
import net.ausiasmarch.cuprodemy.helper.ValidationHelper;
import net.ausiasmarch.cuprodemy.repository.ComentarioRepository;
import net.ausiasmarch.cuprodemy.repository.TipocomentarioRepository;


@Service
public class ComentarioService {
    
    private final List<String> comentarios = List.of("Buen video", "No me ha ayudado", "Podrías subir más?",
    "Muy bueno, gracias", "Me ha gustado mucho", "No me ha gustado", "Muy interesante", "Muy útil", 
    "Muy bien explicado", "Muy mal explicado", "Muy malo", "Muy bueno", "Muy útil", "Muy interesante",
    "Gracias por el contenido", "Me ha ayudado mucho", "Me ha ayudado poco", "Me ha ayudado nada");

    private final List<String> fechayhora = List.of("2021-01-01 12:00:00", "2020-11-13 3:00:00", "2021-09-03 17:00:00",
    "2021-05-05 12:00:00", "2022-04-13 4:00:00", "2021-03-13 12:00:00", "2021-09-17 15:30:00", "2021-01-06 12:30:00",
    "2020-01-01 12:00:00", "2023-01-13 14:00:00", "2020-09-03 17:00:00", "2022-06-28 11:00:00", "2022-04-13 4:00:00",
    "2021-03-13 12:00:00", "2021-09-17 15:30:00", "2021-01-06 12:30:00", "2020-01-01 12:00:00", "2023-01-13 14:00:00");


    @Autowired
    ComentarioRepository oComentarioRepository;

    @Autowired
    UserService oUsuarioService;

    @Autowired
    AuthService oAuthService;

    @Autowired
    CursoService oCursoService;

    @Autowired
    TipocomentarioRepository oTipocomentarioRepository;

    public void validate(Long id) {
        if (!oComentarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " doesn't exist");
        }
    }

    public ComentarioEntity get(Long id) {
        oAuthService.OnlyAdminsOrOwnUsersData(id);
        try {
            return oComentarioRepository.findById(id).get();
        } catch (Exception ex) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public Long count() {
        oAuthService.OnlyAdmins();
        return oComentarioRepository.count();
    }

     public Page<ComentarioEntity> getPage(Pageable oPageable, String strFilter, Long id_usuario, Long id_tipocomentario, Long id_curso){
        oAuthService.OnlyAdmins();

        ValidationHelper.validateRPP(oPageable.getPageSize());
        Page<ComentarioEntity> oPage = null;

        if (id_usuario == null && id_tipocomentario == null && id_curso == null) {
            if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                oPage = oComentarioRepository.findAll(oPageable);
            } else {
                oPage = oComentarioRepository.findByComentarioIgnoreCaseContaining(strFilter,oPageable);
            }
        } else if (id_usuario == null && id_curso == null) {
            oPage = oComentarioRepository.findByTipocomentario(oPageable, id_tipocomentario);
        } else if (id_tipocomentario == null && id_curso == null) {
            oPage = oComentarioRepository.findByUsuario(oPageable, id_usuario);
        } else if (id_tipocomentario == null && id_usuario == null) {
            oPage = oComentarioRepository.findByCurso(oPageable, id_curso);
        } else if (id_usuario == null) {
            oPage = oComentarioRepository.findByCursoAndTipocomentario(oPageable, id_curso, id_tipocomentario);
        } else if (id_tipocomentario == null) {
            oPage = oComentarioRepository.findByCursoAndUsuario(oPageable, id_curso, id_usuario);
        } else if (id_curso == null) {
            oPage = oComentarioRepository.findByUsuarioAndTipocomentario(oPageable, id_usuario, id_tipocomentario);
        } else {
            oPage = oComentarioRepository.findByCursoAndUsuarioAndTipocomentario(oPageable, id_curso, id_usuario, id_tipocomentario);
        }

        return  oPage ;
    }  

    public Long create(ComentarioEntity oNewComentarioEntity) {
        oAuthService.OnlyAdmins();
        oNewComentarioEntity.setId(0L);
        return oComentarioRepository.save(oNewComentarioEntity).getId();
    }

    
    @Transactional
    public Long update(ComentarioEntity oComentarioEntity) {
        validate(oComentarioEntity.getId());
        oAuthService.OnlyAdminsOrOwnUsersData(oComentarioEntity.getId());
        return oComentarioRepository.save(oComentarioEntity).getId();
    }


    public Long delete(Long id) {
        oAuthService.OnlyAdmins();
        if (oComentarioRepository.existsById(id)) {
            oComentarioRepository.deleteById(id);
            if (oComentarioRepository.existsById(id)) {
                throw new ResourceNotModifiedException("can't remove register " + id);
            } else {
                return id;
            }
        } else {
            throw new ResourceNotModifiedException("id " + id + " not exist");
        }
    }

    public ComentarioEntity generate() {
        oAuthService.OnlyAdmins();
        return oComentarioRepository.save(generateRandomComentario());
    } 


    public Long generateSome(Long amount) {
        oAuthService.OnlyAdmins();
        List<ComentarioEntity> ComentarioList = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            ComentarioEntity oComentarioEntity = generateRandomComentario();
            oComentarioRepository.save(oComentarioEntity);
            ComentarioList.add(oComentarioEntity);
        }

        return oComentarioRepository.count();
    }


    public ComentarioEntity getOneRandom() {
        if (count() > 0) {
            return oComentarioRepository.findById((long)(Math.random() * oComentarioRepository.count() +1)).get();
        } else {
            throw new CannotPerformOperationException("ho hay comentarios en la base de datos");
        }
    }

    private ComentarioEntity generateRandomComentario() {
        ComentarioEntity oComentarioEntity = new ComentarioEntity();
        oComentarioEntity.setComentario(comentarios.get(RandomHelper.getRandomInt(0, comentarios.size() - 1)));
        oComentarioEntity.setFirmatiempo(fechayhora.get(RandomHelper.getRandomInt(0, fechayhora.size() - 1)));
        if(RandomHelper.getRandomInt(0, 10) > 1){
            oComentarioEntity.setTipoComentario(oTipocomentarioRepository.getById(TipocomentarioHelper.PREGUNTAS));
        } else {
            oComentarioEntity.setTipoComentario(oTipocomentarioRepository.getById(TipocomentarioHelper.APORTES));
        }
        oComentarioEntity.setUsuario(oUsuarioService.getOneRandom());
        oComentarioEntity.setCurso(oCursoService.getOneRandom());
        
        return oComentarioEntity;
    }

    public ComentarioEntity generateOne() {
        oAuthService.OnlyAdmins();
        return oComentarioRepository.save(generateRandomComentario());
    }




}
