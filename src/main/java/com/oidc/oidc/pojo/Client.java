package com.oidc.oidc.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.util.Objects;

/**
 * @author 晋晨曦
 */
@TableName("client_info")
public class Client {
    @TableField("id")
    private Integer id;
    @TableField("client_name")
    private String clientName;
    @TableField("client_password")
    private String clientPassword;
    @TableField("client_redirection_URL")
    private String clientRedirectionUrl;
    @TableField("client_email")
    private String clientEmail;
    @TableField("client_is_active")
    private Boolean clientIsActive;
    @TableField("client_confirmation_token")
    private String clientConfirmationToken;
    @TableField("client_last_email_sent_time")
    private Date clientLastEmailSentTime;

    // 无参构造函数
    public Client() {
    }

    // 有参构造函数
    public Client(Integer id, String clientName, String clientPassword, String clientRedirectionUrl, String clientEmail, Boolean clientIsActive, String clientConfirmationToken, Date clientLastEmailSentTime) {
        this.id = id;
        this.clientName = clientName;
        this.clientPassword = clientPassword;
        this.clientRedirectionUrl = clientRedirectionUrl;
        this.clientEmail = clientEmail;
        this.clientIsActive = clientIsActive;
        this.clientConfirmationToken = clientConfirmationToken;
        this.clientLastEmailSentTime = clientLastEmailSentTime;
    }

    // Getter和Setter方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPassword() {
        return clientPassword;
    }

    public void setClientPassword(String clientPassword) {
        this.clientPassword = clientPassword;
    }

    public String getClientRedirectionUrl() {
        return clientRedirectionUrl;
    }

    public void setClientRedirectionUrl(String clientRedirectionUrl) {
        this.clientRedirectionUrl = clientRedirectionUrl;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public Boolean isClientIsActive() {
        return clientIsActive;
    }

    public void setClientIsActive(Boolean clientIsActive) {
        this.clientIsActive = clientIsActive;
    }

    public String getClientConfirmationToken() {
        return clientConfirmationToken;
    }

    public void setClientConfirmationToken(String clientConfirmationToken) {
        this.clientConfirmationToken = clientConfirmationToken;
    }

    public Date getClientLastEmailSentTime() {
        return clientLastEmailSentTime;
    }

    public void setClientLastEmailSentTime(Date clientLastEmailSentTime) {
        this.clientLastEmailSentTime = clientLastEmailSentTime;
    }

    // equals方法
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Client client = (Client) o;
        return Objects.equals(id, client.id) &&
                Objects.equals(clientName, client.clientName) &&
                Objects.equals(clientPassword, client.clientPassword) &&
                Objects.equals(clientRedirectionUrl, client.clientRedirectionUrl) &&
                Objects.equals(clientEmail, client.clientEmail) &&
                Objects.equals(clientIsActive, client.clientIsActive) &&
                Objects.equals(clientConfirmationToken, client.clientConfirmationToken) &&
                Objects.equals(clientLastEmailSentTime, client.clientLastEmailSentTime);
    }

    // hashCode方法
    @Override
    public int hashCode() {
        return Objects.hash(id, clientName, clientPassword, clientRedirectionUrl, clientEmail, clientIsActive,
                clientConfirmationToken, clientLastEmailSentTime);
    }
}
