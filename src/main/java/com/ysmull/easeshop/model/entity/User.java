package com.ysmull.easeshop.model.entity;


/**
 * @author maoyusu
 */
public class User {

    public enum ROLE {
        /**
         * 买家
         */
        BUYER,
        /**
         * 卖家
         */
        SELLER

    }

    private Long id;
    private String username;
    private String password;
    private ROLE role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ROLE getRole() {
        return role;
    }

    public void setRole(ROLE role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
