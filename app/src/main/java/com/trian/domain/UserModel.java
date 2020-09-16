package com.triandamai.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel{
    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user_username")
    @Expose
    private String userUsername;
    @SerializedName("user_password")
    @Expose
    private String userPassword;
    @SerializedName("user_level")
    @Expose
    private String userLevel;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("user_reg_method")
    @Expose
    private String userRegMethod;
    @SerializedName("user_uid_social")
    @Expose
    private String userUidSocial;
    @SerializedName("user_created_at")
    @Expose
    private String userCreatedAt;
    @SerializedName("user_updated_at")
    @Expose
    private String userUpdatedAt;

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserRegMethod() {
        return userRegMethod;
    }

    public void setUserRegMethod(String userRegMethod) {
        this.userRegMethod = userRegMethod;
    }

    public String getUserUidSocial() {
        return userUidSocial;
    }

    public void setUserUidSocial(String userUidSocial) {
        this.userUidSocial = userUidSocial;
    }

    public String getUserCreatedAt() {
        return userCreatedAt;
    }

    public void setUserCreatedAt(String userCreatedAt) {
        this.userCreatedAt = userCreatedAt;
    }

    public String getUserUpdatedAt() {
        return userUpdatedAt;
    }

    public void setUserUpdatedAt(String userUpdatedAt) {
        this.userUpdatedAt = userUpdatedAt;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "idUser='" + idUser + '\'' +
                ", userName='" + userName + '\'' +
                ", userUsername='" + userUsername + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userLevel='" + userLevel + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userRegMethod='" + userRegMethod + '\'' +
                ", userUidSocial='" + userUidSocial + '\'' +
                ", userCreatedAt='" + userCreatedAt + '\'' +
                ", userUpdatedAt='" + userUpdatedAt + '\'' +
                '}';
    }
}
