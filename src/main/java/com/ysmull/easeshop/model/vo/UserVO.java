package com.ysmull.easeshop.model.vo;

/**
 * @author maoyusu
 */
public class UserVO {

    private Long userId;
    private String username;
    private Integer role;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", role=" + role +
                '}';
    }
}
