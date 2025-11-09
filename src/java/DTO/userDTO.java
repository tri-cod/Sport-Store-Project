package DTO;

import DAO.orderDAO;
import java.sql.Date;
import java.time.LocalDate;

public class userDTO {
    private String userId;
    private String userName;
    private String email;
    private String fullName;
    private String password;
    private java.sql.Date dateOfBirth;
    private boolean isAdmin;

    public userDTO(String userId, String userName, String email, String fullName, String password, Date dateOfBirth, boolean isAdmin) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.isAdmin = isAdmin;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public userDTO() {
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public userDTO(String userId, String userName, String email, String fullName, String password, Date dateOfBirth) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }

    public userDTO(String userName, String email, String fullName, String password, Date dateOfBirth) {
        this.userName = userName;
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }


  

    public String getUserName() {
        return userName;
    }

    // Getters & Setters
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "userDTO{" + "userId=" + userId + ", userName=" + userName + ", email=" + email + ", fullName=" + fullName + ", password=" + password + ", dateOfBirth=" + dateOfBirth + ", isAdmin=" + isAdmin + '}';
    }
    
}
