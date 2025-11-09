package controller;

import models.userDAO; 
import DTO.userDTO;
import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.UUID;     
import java.sql.Timestamp;  
import utils.EmailUtils;


@WebServlet(name = "userController", urlPatterns = {"/userController"})
public class userController extends HttpServlet {

    // === CÁC HÀM MÃ HÓA (PBKDF2) ===
    // (Phần này giữ nguyên, đã đúng)
    private String getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private String hashPassword(String password, String saltStr)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = Base64.getDecoder().decode(saltStr);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hash);
    }
    // ===================================

    
    // === CÁC HÀM XỬ LÝ CƠ BẢN (Giữ nguyên) ===

    /**
     * XỬ LÝ ĐĂNG NHẬP (AN TOÀN)
     */
    private void processLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // (Giữ nguyên code processLogin của bạn, nó đã đúng)
        String txtUsername = request.getParameter("txtUsername");
        String txtPassword = request.getParameter("txtPassword");
        String url = "login.jsp";
        userDAO dao = new userDAO();
        try {
            userDTO user = dao.getUserByUsername(txtUsername);
            if (user != null && user.getSalt() != null) {
                String savedSalt = user.getSalt();
                String savedHash = user.getPassword();
                String hashFromForm = this.hashPassword(txtPassword, savedSalt);
                if (hashFromForm.equals(savedHash)) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    response.sendRedirect("homeController");
                    return;
                }
            }
            request.setAttribute("msg", "Sai tên đăng nhập hoặc mật khẩu");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "Lỗi hệ thống");
        }
        request.getRequestDispatcher(url).forward(request, response);
    }

    /**
     * XỬ LÝ ĐĂNG XUẤT
     */
    private void processLogout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // (Giữ nguyên code processLogout của bạn, nó đã đúng)
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect("homeController");
    }

    /**
     * XỬ LÝ ĐĂNG KÝ (AN TOÀN)
     */
    private void processRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // (Giữ nguyên code processRegister của bạn, nó đã đúng)
        request.setCharacterEncoding("UTF-8");
        String url = "register.jsp";
        try {
            String txtFullname = request.getParameter("txtFullname");
            String txtEmail = request.getParameter("txtEmail");
            String txtUsername = request.getParameter("txtUsername");
            String txtPassword = request.getParameter("txtPassword");
            String txtRePassword = request.getParameter("txtRePassword");
            String txtDob = request.getParameter("dateOfBirth");

            if (!txtPassword.equals(txtRePassword)) {
                request.setAttribute("msg", "Xác nhận mật khẩu sai");
                request.getRequestDispatcher(url).forward(request, response);
                return;
            }
            userDAO dao = new userDAO();
            if (dao.checkAccountExist(txtUsername) != null) {
                request.setAttribute("msg", "Tài khoản đã tồn tại");
                request.getRequestDispatcher(url).forward(request, response);
                return;
            }
            String salt = this.getSalt();
            String hashedPassword = this.hashPassword(txtPassword, salt);
            userDTO newUser = new userDTO();
            newUser.setUserName(txtUsername);
            newUser.setPassword(hashedPassword);
            newUser.setSalt(salt);
            newUser.setFullName(txtFullname);
            newUser.setEmail(txtEmail);
            newUser.setDateOfBirth(Date.valueOf(txtDob));
            newUser.setIsAdmin(false);
            boolean check = dao.insertUser(newUser);
            if (check) {
                request.setAttribute("msg", "✅ Đăng ký thành công, mời đăng nhập.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                request.setAttribute("msg", "❌ Đăng ký thất bại! Vui lòng thử lại.");
                request.getRequestDispatcher(url).forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "Lỗi không xác định: " + e.getMessage());
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    

    // === CÁC HÀM MỚI CHO "QUÊN MẬT KHẨU" ===

    /**
     * BƯỚC 1: Xử lý khi người dùng nhập email vào resetPass.jsp
     */
    private void processRequestReset(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("txtEmail");
        String url = "resetPass.jsp"; // Trang nhập email
        userDAO dao = new userDAO();
        
        try {
            // Dùng hàm DAO đã hoàn thiện
            userDTO user = dao.getUserByEmail(email); 
            if (user == null) {
                request.setAttribute("msg", "Lỗi: Email không tồn tại trong hệ thống.");
                request.getRequestDispatcher(url).forward(request, response);
                return;
            }

            // 1. Tạo Token
            String token = UUID.randomUUID().toString();
            
            // 2. Set thời gian hết hạn (Ví dụ: 1 phut = 60000 ms)
            long expiryTime = System.currentTimeMillis() + 60000; 
            Timestamp expiryTimestamp = new Timestamp(expiryTime);

            // 3. Lưu token/expiry vào DB (Dùng hàm DAO đã hoàn thiện)
            boolean checkUpdate = dao.updateResetToken(email, token, expiryTimestamp);

            if (checkUpdate) {
                // 4. Tạo link
                // (Chỉnh lại link cho đúng với môi trường của bạn)
                String resetLink = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() 
                                 + request.getContextPath() + "/mainController?txtAction=validateToken&token=" + token;
                
                // 5. Gửi email (Dùng class EmailService)
                boolean emailSent = EmailUtils.sendResetPasswordEmail(email, resetLink);
                
                if (emailSent) {
                    request.setAttribute("msg", "Thành công! Vui lòng kiểm tra email (trong 1 phút) để đặt lại mật khẩu.");
                } else {
                    request.setAttribute("msg", "Lỗi: Không thể gửi email. Vui lòng thử lại sau.");
                }
            } else {
                request.setAttribute("msg", "Lỗi: Không thể tạo token. Vui lòng thử lại.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "Lỗi hệ thống: " + e.getMessage());
        }
        
        request.getRequestDispatcher(url).forward(request, response);
    }


    /**
     * BƯỚC 2: Xử lý khi người dùng bấm vào link trong email
     */
    private void processValidateToken(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String token = request.getParameter("token");
        userDAO dao = new userDAO();
        
        try {
            // Kiểm tra token có hợp lệ và còn hạn không (Dùng hàm DAO đã hoàn thiện)
            userDTO user = dao.getUserByToken(token);
            
            if (user != null) {
                // Token hợp lệ, chuyển đến trang nhập pass mới
                request.setAttribute("token", token); // Gửi token sang JSP
                request.getRequestDispatcher("changePass.jsp").forward(request, response);
            } else {
                // Token không hợp lệ hoặc hết hạn
                request.setAttribute("msg", "Lỗi: Link đặt lại mật khẩu không hợp lệ hoặc đã hết hạn.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "Lỗi hệ thống: " + e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    /**
     * BƯỚC 3: Xử lý khi người dùng nhập pass mới vào changePass.jsp
     */
    private void processPerformReset(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        String token = request.getParameter("token");
        String newPass = request.getParameter("txtNewPassword");
        String confirmPass = request.getParameter("txtConfirmPassword");
        String url = "changePass.jsp";
        
        try {
            if (!newPass.equals(confirmPass)) {
                request.setAttribute("msg", "Lỗi: Mật khẩu xác nhận không khớp.");
                request.setAttribute("token", token); // Phải gửi lại token
                request.getRequestDispatcher(url).forward(request, response);
                return;
            }

            userDAO dao = new userDAO();
            
            // Lấy lại user bằng token (để biết update cho ai)
            userDTO user = dao.getUserByToken(token); 
            
            if (user == null) {
                request.setAttribute("msg", "Lỗi: Token không hợp lệ hoặc hết hạn. Vui lòng thử lại từ đầu.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }

            // --- Mã Hóa Mật Khẩu Mới ---
            String newSalt = this.getSalt();
            String newHashedPassword = this.hashPassword(newPass, newSalt);

            // Cập nhật pass/salt mới
            boolean checkUpdate = dao.updatePasswordAndSalt(user.getUserName(), newHashedPassword, newSalt);
            
            if (checkUpdate) {
                // Xóa token đi để không dùng lại được (Dùng hàm DAO đã hoàn thiện)
                dao.clearResetToken(user.getUserName()); 
                
                request.setAttribute("msg", "✅ Đặt lại mật khẩu thành công! Mời đăng nhập.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                request.setAttribute("msg", "Lỗi: Không thể cập nhật mật khẩu.");
                request.setAttribute("token", token); // Gửi lại token
                request.getRequestDispatcher(url).forward(request, response);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "Lỗi hệ thống: " + e.getMessage());
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    
    // === BỘ ĐIỀU KHIỂN CHÍNH ===
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8"); 

        String action = request.getParameter("txtAction");

        if (action == null) {
            response.sendRedirect("homeController");
            return;
        }

        // Dùng switch-case cho sạch sẽ
        switch (action) {
            case "login":
                processLogin(request, response);
                break;
            case "register":
                processRegister(request, response);
                break;
            case "logout":
                processLogout(request, response);
                break;
                
            // === ❗ THAY ĐỔI LUỒNG RESET ===
            case "requestReset": // BƯỚC 1: Người dùng nhập email
                processRequestReset(request, response);
                break;
            case "validateToken": // BƯỚC 2: Người dùng bấm link từ email
                processValidateToken(request, response);
                break;
            case "performReset": // BƯỚC 3: Người dùng nhập pass mới
                processPerformReset(request, response);
                break;
            // (Đã xóa "resetPassword" cũ)

            default:
                // Nếu action lạ, về trang chủ
                response.sendRedirect("homeController");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "User Controller (Secure Version)";
    }// </editor-fold>
}