package DTO;

public class UserError {
    
    // Các thuộc tính phải khớp với lỗi mà userDAO.validateRegisterInput đặt
    private String fullnameError;
    private String emailError;
    private String usernameError;
    private String passwordError;
    private String repassError;
    private String dobError;
    private String mainError; // Dùng cho lỗi chung (ví dụ: đăng ký thất bại)

    // Constructor rỗng là cần thiết để có thể tạo đối tượng
    public UserError() {
    }

    /**
     * Phương thức kiểm tra xem đối tượng lỗi có chứa lỗi nào không.
     * Controller sẽ dùng phương thức này.
     */
    public boolean hasErrors() {
        return fullnameError != null || emailError != null || 
               usernameError != null || passwordError != null || 
               repassError != null || dobError != null || 
               mainError != null;
    }

    // --- GETTERS VÀ SETTERS (Bắt buộc phải có để JSP truy cập bằng EL) ---

    public String getFullnameError() {
        return fullnameError;
    }

    public void setFullnameError(String fullnameError) {
        this.fullnameError = fullnameError;
    }

    public String getEmailError() {
        return emailError;
    }

    public void setEmailError(String emailError) {
        this.emailError = emailError;
    }

    public String getUsernameError() {
        return usernameError;
    }

    public void setUsernameError(String usernameError) {
        this.usernameError = usernameError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getRepassError() {
        return repassError;
    }

    public void setRepassError(String repassError) {
        this.repassError = repassError;
    }

    public String getDobError() {
        return dobError;
    }

    public void setDobError(String dobError) {
        this.dobError = dobError;
    }

    public String getMainError() {
        return mainError;
    }

    public void setMainError(String mainError) {
        this.mainError = mainError;
    }
}