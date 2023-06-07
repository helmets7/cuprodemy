package net.ausiasmarch.cuprodemy.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import net.ausiasmarch.cuprodemy.bean.UsuarioBean;
import net.ausiasmarch.cuprodemy.entity.TokenResponse;
import net.ausiasmarch.cuprodemy.entity.UserEntity;
import net.ausiasmarch.cuprodemy.helper.JwtHelper;
import net.ausiasmarch.cuprodemy.exception.UnauthorizedException;
import net.ausiasmarch.cuprodemy.helper.TipoUsuarioHelper;
import net.ausiasmarch.cuprodemy.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    HttpSession oHttpSession;

    @Autowired
    UserRepository oUserRepository;

    @Autowired
    private HttpServletRequest oRequest;

    public String login(@RequestBody UsuarioBean oUserBean) {
        if (oUserBean.getPass() != null) {
            UserEntity oUserEntity = oUserRepository.findByNicknameAndPass(oUserBean.getNickname(),
                    oUserBean.getPass());
            if (oUserEntity != null) {
                return JwtHelper.generateJWT(oUserBean.getNickname(), oUserEntity.getTipousuario().getId());
            } else {
                throw new UnauthorizedException("usuario or contraseña incorrect");
            }
        } else {
            throw new UnauthorizedException("wrong Contraseña");
        }
    }

    public String logout(@RequestBody String token) {

        if (token != null) {
            TokenResponse resp = new TokenResponse();
            resp.setMessage("Se ha cerrado la  sesion");

            ObjectMapper objectMapper = new ObjectMapper();
            String json = "";
            try {
                json = objectMapper.writeValueAsString(resp);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return json;
        } else {
            throw new UnauthorizedException("Algo salio mal");
        }

    }

    public UserEntity check() {
        String strUsuarioName = (String) oRequest.getAttribute("usuario");
        if (strUsuarioName != null) {
            UserEntity oUsuarioEntity = oUserRepository.findByNickname(strUsuarioName);
            return oUsuarioEntity;
        } else {
            throw new UnauthorizedException("No active session");
        }
    }

    public boolean isLoggedIn() {
        UserEntity oUserSessionEntity = (UserEntity) oHttpSession.getAttribute("usuario");
        if (oUserSessionEntity == null) {
            return false;
        } else {
            return true;
        }
    }

    public UserEntity getUser() {
        UserEntity oUserSessionEntity = (UserEntity) oHttpSession.getAttribute("usuario");
        if (oUserSessionEntity != null) {
            return oUserSessionEntity;
        } else {
            throw new UnauthorizedException("this request is only allowed to auth users");
        }
    }

    public Long getID() {
        UserEntity oUserSessionEntity = oUserRepository.findByNickname((String) oRequest.getAttribute("usuario"));
        if (oUserSessionEntity != null) {
            return oUserSessionEntity.getId();
        } else {
            throw new UnauthorizedException("this request is only allowed to auth users");
        }
    }

    public boolean isAdmin() {
        UserEntity oUserSessionEntity = (UserEntity) oHttpSession.getAttribute("usuario");
        if (oUserSessionEntity != null) {
            if (oUserSessionEntity.getTipousuario().getId().equals(TipoUsuarioHelper.ADMIN)) {
                return true;
            }
        }
        return false;
    }

    public boolean isUser() {
        UserEntity oUserSessionEntity = (UserEntity) oHttpSession.getAttribute("usuario");
        if (oUserSessionEntity != null) {
            if (oUserSessionEntity.getTipousuario().getId().equals(TipoUsuarioHelper.USER)) {
                return true;
            }
        }
        return false;
    }

    public void OnlyAdmins() {
        UserEntity oUserSessionEntity = oUserRepository.findByNickname((String) oRequest.getAttribute("usuario"));
        if (oUserSessionEntity == null) {
            throw new UnauthorizedException("no session active");
        } else {
            if (!oUserSessionEntity.getTipousuario().getId().equals(TipoUsuarioHelper.ADMIN)) {
                throw new UnauthorizedException("this request is only allowed to admin role");
            }
        }
    }

    public void OnlyUsers() {
        UserEntity oUserSessionEntity = (UserEntity) oHttpSession.getAttribute("usuario");
        if (oUserSessionEntity == null) {
            throw new UnauthorizedException("this request is only allowed to user role");
        } else {
            if (!oUserSessionEntity.getTipousuario().getId().equals(TipoUsuarioHelper.USER)) {
                throw new UnauthorizedException("this request is only allowed to user role");
            }
        }
    }

    public void OnlyAdminsOrUsers() {
        UserEntity oUserSessionEntity = (UserEntity) oHttpSession.getAttribute("usuario");
        if (oUserSessionEntity == null) {
            throw new UnauthorizedException("this request is only allowed to user or admin role");
        } else {

        }
    }

    public void OnlyAdminsOrOwnUsersData(Long id) {
        UserEntity oUsuarioSessionEntity = oUserRepository.findByNickname((String) oRequest.getAttribute("usuario"));
        if (oUsuarioSessionEntity == null) {
            throw new UnauthorizedException("no session active");
        } else {
            if (oUsuarioSessionEntity.getTipousuario().getId().equals(TipoUsuarioHelper.ADMIN)) {

            } else if (!oUsuarioSessionEntity.getId().equals(id)) {
                throw new UnauthorizedException("this request is only allowed for your own data");
            }
        }
    }

}
