/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.userDAO;
import DTO.userDTO;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
@WebServlet(name = "userController", urlPatterns = {"/userController"})
public class userController extends HttpServlet {

    /**
     * Xử lý logic đăng nhập
     */
    private void processLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String txtUsername = request.getParameter("txtUsername");
        String txtPassword = request.getParameter("txtPassword");

        userDAO userDAO = new userDAO();
        boolean checkLogin = userDAO.login(txtUsername, txtPassword);
        String url = "login.jsp"; // Mặc định về trang login nếu thất bại
        String msg = "";

        if (txtUsername == null || txtPassword == null || txtUsername.isEmpty() || txtPassword.isEmpty()) {
            // Nếu chưa nhập gì, giữ nguyên trang login
            request.getRequestDispatcher(url).forward(request, response);
            return;
        }

        userDTO user = null;
        if (!checkLogin) {
            msg = "Username or password incorrect!";
        } else {
            user = userDAO.getUserById(txtUsername);
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(600);
            session.setAttribute("user", user);
            request.setAttribute("user", user);

            // Chuyển hướng đến Home Controller sau khi đăng nhập thành công
            request.getRequestDispatcher("homeController").forward(request, response);
            return;
        }

        request.setAttribute("msg", msg);
        request.getRequestDispatcher(url).forward(request, response);
    }

    /**
     * Xử lý logic đăng xuất
     */
    private void processLogout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Lấy session hiện tại (nếu có)
        if (session != null) {
            session.removeAttribute("user"); // Xóa thuộc tính user
            session.invalidate(); // Hủy toàn bộ session
        }
        response.sendRedirect("homeController"); // Chuyển hướng về trang chủ
    }

    /**
     * Xử lý logic đăng ký (Đã giữ nguyên phần fix lỗi của bạn)
     */
// Trong userController.java
    /**
     * Xử lý logic đăng ký
     */
    private void processRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // --- Nhận dữ liệu từ form ---
        String txtFullname = request.getParameter("txtFullname");
        String txtEmail = request.getParameter("txtEmail");
        String txtUsername = request.getParameter("txtUsername");
        String txtPassword = request.getParameter("txtPassword");
        String txtDob = request.getParameter("dateOfBirth");
        String txtRePassword = request.getParameter("txtRePassword");

        Map<String, String> errors = new HashMap<>();
        userDAO dao = new userDAO();

        // --- 1️⃣ Full name ---
        if (txtFullname == null || txtFullname.trim().isEmpty()) {
            errors.put("fullnameError", "Full name cannot be empty");
        }

        // --- 2️⃣ Username ---
        if (txtUsername == null || txtUsername.trim().isEmpty()) {
            errors.put("usernameError", "Username cannot be empty");
        } else if (dao.checkAccountExist(txtUsername) != null) {
            errors.put("usernameError", "Username is already taken");
            errors.put("mainError", "❌ Registration failed. Username is already taken.");
        }

        // --- 3️⃣ Password ---
        if (txtPassword == null || txtPassword.length() < 6) {
            errors.put("passwordError", "Password must be at least 6 characters");
        }

        // --- 4️⃣ Confirm Password ---
        if (txtRePassword == null || !txtRePassword.equals(txtPassword)) {
            errors.put("repassError", "Password confirmation does not match");
        }

        // --- 5️⃣ Email ---
        String EMAIL_REGEX = "^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,6}$";
        if (txtEmail == null || txtEmail.trim().isEmpty()) {
            errors.put("emailError", "Email cannot be empty");
        } else if (!txtEmail.matches(EMAIL_REGEX)) {
            errors.put("emailError", "Invalid email format");
        } else if (dao.checkEmailExist(txtEmail)) {
            errors.put("emailError", "Email is already registered");
            errors.put("mainError", "❌ Registration failed. Email is already registered.");
        }

        // --- 6️⃣ Ngày sinh ---
        if (txtDob == null || txtDob.isEmpty()) {
            errors.put("dobError", "Please select your date of birth");
        } else {
            try {
                java.sql.Date dob = java.sql.Date.valueOf(txtDob);
                java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
                if (dob.after(currentDate)) {
                    errors.put("dobError", "Date of birth cannot be in the future");
                }
            } catch (IllegalArgumentException e) {
                errors.put("dobError", "Invalid date format");
            }
        }

        // --- 7️⃣ Kiểm tra trùng chéo giữa Email và Username ---
        if (errors.isEmpty()) { // chỉ kiểm tra nếu chưa có lỗi cơ bản nào
            // Email trùng với username của người khác
            if (dao.checkEmailSameAsUsername(txtEmail)) {
                errors.put("emailError", "Email matches an existing username. Please use another email.");
                errors.put("mainError", "❌ Registration failed. Email matches an existing username.");
            } // Username trùng với email người khác
            else if (dao.checkUsernameSameAsEmail(txtUsername)) {
                errors.put("usernameError", "Username matches an existing email. Please choose another username.");
                errors.put("mainError", "❌ Registration failed. Username matches an existing email.");
            }
        }

        // --- 8️⃣ Nếu có lỗi, quay lại form ---
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // --- ✅ 9️⃣ Nếu không có lỗi: tiến hành đăng ký ---
        try {
            java.sql.Date dateOfBirth = java.sql.Date.valueOf(txtDob);
            userDTO user = new userDTO(txtUsername, txtEmail, txtFullname, txtPassword, dateOfBirth);

            boolean check = dao.insertUser(user);

            if (check) {
                // Thông báo thành công -> chuyển sang trang login
                request.getSession().setAttribute("msg", "Registration successful! Please login.");
                response.sendRedirect("login.jsp");
            } else {
                errors.put("mainError", "❌ Registration failed due to a system error.");
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        } catch (Exception e) {
            errors.put("mainError", "An unexpected error occurred: " + e.getMessage());
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("txtAction");

        if (action == null || action.isEmpty()) {
            response.sendRedirect("homeController");
            return;
        }

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
            default:
                response.sendRedirect("homeController");
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("txtAction");

        if (action != null && action.equals("register")) {
            // Hiển thị trang đăng ký sạch sẽ (không có lỗi)
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } else {
            // Xử lý các GET request khác
            processRequest(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
