package com.oidc.oidc.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Objects;

/**
 * @author 晋晨曦
 */
@TableName("authorization_code_info")
public class AuthorizationCode {

    @TableField("id")
    private int id;

    @TableField("code_value")
    private String codeWord;

    @TableField("code_scope")
    private String codeScope;

    // 无参构造函数
    public AuthorizationCode() {
    }

    // 有参构造函数
    public AuthorizationCode(int id, String codeWord, String codeScope) {
        this.id = id;
        this.codeWord = codeWord;
        this.codeScope = codeScope;
    }

    // Getter 和 Setter 方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodeWord() {
        return codeWord;
    }

    public void setCodeWord(String codeWord) {
        this.codeWord = codeWord;
    }

    public String getCodeScope() {
        return codeScope;
    }

    public void setCodeScope(String codeScope) {
        this.codeScope = codeScope;
    }

    // hashCode 方法
    @Override
    public int hashCode() {
        return Objects.hash(id, codeWord, codeScope);
    }

    // equals 方法
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AuthorizationCode that = (AuthorizationCode) obj;
        return id == that.id &&
                Objects.equals(codeWord, that.codeWord) &&
                Objects.equals(codeScope, that.codeScope);
    }

    // toString 方法
    @Override
    public String toString() {
        return "AuthorizationCode{" +
                "id=" + id +
                ", codeWord='" + codeWord + '\'' +
                ", codeScope='" + codeScope + '\'' +
                '}';
    }
}
