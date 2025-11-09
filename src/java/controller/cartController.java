/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import models.cartDAO;
import models.cartItemDAO;
import DTO.cartDTO;
import DTO.cartItemDTO;
import DTO.userDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
@WebServlet(name = "cartController", urlPatterns = {"/cartController"})
public class cartController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void loadCartByUserId(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String userId = request.getParameter("userId");
        cartItemDAO dao = new cartItemDAO();
        List<cartItemDTO> list = dao.getAllItemsByUserId(userId);
        request.setAttribute("listP", list);
        request.getRequestDispatcher("cartDetail.jsp").forward(request, response);

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
//        loadCartByUserId(request, response);
        HttpSession session = request.getSession();
        userDTO user = (userDTO) session.getAttribute("user");

        if (user == null) {
            // Nếu chưa đăng nhập -> chuyển về trang login
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            cartItemDAO dao = new cartItemDAO();
            List<cartItemDTO> list = dao.getAllItemsByUserId(user.getUserId());
            request.setAttribute("listP", list);
            request.getRequestDispatcher("cartDetail.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "Lỗi khi tải giỏ hàng: " + e.getMessage());
            request.getRequestDispatcher("cartDetail.jsp").forward(request, response);
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
