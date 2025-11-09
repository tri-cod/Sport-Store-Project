/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.categoryDAO;
import DAO.productDAO;
import DAO.userDAO;
import DAO.userInformationDAO;
import DTO.categoryDTO;
import DTO.productDTO;
import DTO.userDTO;
import DTO.userInformationDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void processLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String txtUsername = request.getParameter("txtUsername");
        String txtPassword = request.getParameter("txtPassword");

        userDAO userDAO = new userDAO();
        boolean checkLogin = userDAO.login(txtUsername, txtPassword);
        String url = checkLogin ? "home.jsp" : "login.jsp";
        String msg = "";

        if (txtUsername == null || txtPassword == null || txtUsername.isEmpty() || txtPassword.isEmpty()) {
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
            request.getRequestDispatcher("homeController").forward(request, response);
            return;
        }

        request.setAttribute("msg", msg);

        request.getRequestDispatcher(url).forward(request, response);
    }

    private void processLogout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("user"); // Huy tat ca nhung cai dang co trong session
        response.sendRedirect("homeController");
    }

    private void processRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // Lấy dữ liệu từ form
        String txtFullname = request.getParameter("txtFullname");
        String txtEmail = request.getParameter("txtEmail");
        String txtUsername = request.getParameter("txtUsername");
        String txtPassword = request.getParameter("txtPassword");
        String txtDob = request.getParameter("dateOfBirth"); // lấy giá trị ngày sinh
        String txtRePassword = request.getParameter("txtRePassword");
        if (txtUsername == null || txtFullname == null || txtEmail == null || txtPassword == null) {
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        java.sql.Date dateOfBirth = null;
        dateOfBirth = java.sql.Date.valueOf(txtDob);

        if (!txtPassword.equals(txtRePassword)) {
            request.setAttribute("msg", "xac nhan mat khau sai vui long nhap lai");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }

        userDTO user = new userDTO(txtUsername, txtEmail, txtFullname, txtPassword, dateOfBirth);

        userDAO dao = new userDAO();
        boolean check = dao.insertUser(user);

        if (check) {
            userDTO u = dao.checkAccountExist(txtUsername);
            if (u == null) {
                request.setAttribute("msg", "✅ Register successful, login.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                request.setAttribute("msg", "tai khoan da ton tai");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("msg", "❌ register fail! please try again.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }

    }

   

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("txtAction");
        if (action.equals("login")) {
            processLogin(request, response);
        } else if (action.equals("register")) {
            processRegister(request, response);
        } else if (action.equals("logout")) {
            processLogout(request, response);
        } 
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
