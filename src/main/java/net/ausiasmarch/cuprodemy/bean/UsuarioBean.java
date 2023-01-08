package net.ausiasmarch.cuprodemy.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public class UsuarioBean {

    @Schema(example = "admin")
    private String nickname = "";
    

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String pass = "";


    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }

   
}
