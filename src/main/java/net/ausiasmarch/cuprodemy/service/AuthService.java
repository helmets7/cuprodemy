package net.ausiasmarch.cuprodemy.service;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ausiasmarch.cuprodemy.bean.UsuarioBean;
import net.ausiasmarch.cuprodemy.entity.UserEntity;
import net.ausiasmarch.cuprodemy.exception.UnauthorizedException;
import net.ausiasmarch.cuprodemy.helper.TipoUsuarioHelper;
import net.ausiasmarch.cuprodemy.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    HttpSession oHttpSession;

    @Autowired
    UserRepository oUserRepository;

    public UserEntity login(UsuarioBean oUserBean) {
        if (oUserBean.getPass() != null) {
            UserEntity oUserEntity = oUserRepository.findByNicknameAndPass(oUserBean.getNickname(), oUserBean.getPass());
            if (oUserEntity != null) {
                oHttpSession.setAttribute("nickname", oUserEntity);
                return oUserEntity;
            } else {
                throw new UnauthorizedException("login or password incorrect");
            }
        } else {
            throw new UnauthorizedException("wrong password");
        }
    }

    public void logout() {
        oHttpSession.invalidate();
    }

    public UserEntity check() {
        UserEntity oUserSessionEntity = (UserEntity) oHttpSession.getAttribute("nickname");
        if (oUserSessionEntity != null) {
            return oUserSessionEntity;
        } else {
            throw new UnauthorizedException("no active session");
        }
    }

    public boolean isLoggedIn() {
        UserEntity oUserSessionEntity = (UserEntity) oHttpSession.getAttribute("nickname");
        if (oUserSessionEntity == null) {
            return false;
        } else {
            return true;
        }
    }

    public UserEntity getUser() {
        UserEntity oUserSessionEntity = (UserEntity) oHttpSession.getAttribute("nickname");
        if (oUserSessionEntity != null) {
            return oUserSessionEntity;
        } else {
            throw new UnauthorizedException("this request is only allowed to auth users");
        }
    }

    public Long getUserID() {
        UserEntity oUserSessionEntity = (UserEntity) oHttpSession.getAttribute("nickname");
        if (oUserSessionEntity != null) {
            return oUserSessionEntity.getId();
        } else {
            throw new UnauthorizedException("this request is only allowed to auth users");
        }
    }

    public boolean isAdmin() {
        UserEntity oUserSessionEntity = (UserEntity) oHttpSession.getAttribute("nickname");
        if (oUserSessionEntity != null) {
            if (oUserSessionEntity.getTipousuario().getId().equals(TipoUsuarioHelper.ADMIN)) {
                return true;
            }
        }
        return false;
    }

    public boolean isUser() {
        UserEntity oUserSessionEntity = (UserEntity) oHttpSession.getAttribute("nickname");
        if (oUserSessionEntity != null) {
            if (oUserSessionEntity.getTipousuario().getId().equals(TipoUsuarioHelper.USER)) {
                return true;
            }
        }
        return false;
    }

    public void OnlyAdmins() {
        UserEntity oUserSessionEntity = (UserEntity) oHttpSession.getAttribute("nickname");
        if (oUserSessionEntity == null) {
            throw new UnauthorizedException("no session"+oHttpSession.getAttribute("nickname"));
        } else {
            if (!oUserSessionEntity.getTipousuario().getId().equals(TipoUsuarioHelper.ADMIN)) {
                throw new UnauthorizedException("this request is only allowed to admin role");
            }
        }
    }

    public void OnlyUsers() {
        UserEntity oUserSessionEntity = (UserEntity) oHttpSession.getAttribute("nickname");
        if (oUserSessionEntity == null) {
            throw new UnauthorizedException("this request is only allowed to user role");
        } else {
            if (!oUserSessionEntity.getTipousuario().getId().equals(TipoUsuarioHelper.USER)) {
                throw new UnauthorizedException("this request is only allowed to user role");
            }
        }
    }

    public void OnlyAdminsOrUsers() {
        UserEntity oUserSessionEntity = (UserEntity) oHttpSession.getAttribute("nickname");
        if (oUserSessionEntity == null) {
            throw new UnauthorizedException("this request is only allowed to user or admin role");
        } else {

        }
    }

    public void OnlyAdminsOrOwnUsersData(Long id) {
        UserEntity oUserSessionEntity = (UserEntity) oHttpSession.getAttribute("nickname");
        if (oUserSessionEntity != null) {
            if (oUserSessionEntity.getTipousuario().getId().equals(TipoUsuarioHelper.USER)) {
                if (!oUserSessionEntity.getId().equals(id)) {
                    throw new UnauthorizedException("this request is only allowed for your own data");
                }
            }
        } else {
            throw new UnauthorizedException("this request is only allowed to user or admin role");
        }
    }

    

}
