/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.userInformationDAO;
import DTO.userDTO;
import DTO.userInformationDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
@WebServlet(name = "addressController", urlPatterns = {"/addressController"})
public class addressController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        HttpSession session = request.getSession();

        userDTO user = (userDTO) session.getAttribute("user");

        userInformationDTO uInfo = new userInformationDTO(address, name, phone, user.getUserId());

        userInformationDAO udao = new userInformationDAO();

        try {
            boolean check = udao.addUserInformation(uInfo);
            if (check == false) {
                request.setAttribute("msg", "them dia chi that bai");
                request.getRequestDispatcher("managerAddress.jsp").forward(request, response);
            } else {
                request.setAttribute("msg", "them dia chi thanh cong");
                request.getRequestDispatcher("managerAddress.jsp").forward(request, response);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(addressController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(addressController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void processUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String infoId = request.getParameter("infoId");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        HttpSession session = request.getSession();

        userDTO user = (userDTO) session.getAttribute("user");

        userInformationDTO uInfo = new userInformationDTO(name, phone, address, user.getUserId());
        userInformationDAO udao = new userInformationDAO();
        try {
            boolean check = udao.updateUserInformation(uInfo);
            if (check == false) {
                request.setAttribute("msg", "cap nhat dia chi that bai");
                request.getRequestDispatcher("managerAddress.jsp").forward(request, response);
            } else {
                request.setAttribute("msg", "cap nhat dia chi thanh cong");
                request.getRequestDispatcher("managerAddress.jsp").forward(request, response);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(addressController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(addressController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void processDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String infoId = request.getParameter("infoId");
        HttpSession session = request.getSession();

        userDTO user = (userDTO) session.getAttribute("user");

        userInformationDAO udao = new userInformationDAO();
        try {
            boolean check = udao.deleteUserInformation(infoId);
            if (check == false) {
                request.setAttribute("msg", "Xoa khong thanh cong");
                request.getRequestDispatcher("managerAddress.jsp").forward(request, response);
            } else {           
                request.setAttribute("msg", "Xoa thanh cong");
                request.getRequestDispatcher("managerAddress.jsp").forward(request, response);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(addressController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("msg", "Lỗi hệ thống: " + ex.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(addressController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("msg", "Lỗi hệ thống: " + ex.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    protected void loadAddressList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        userDTO user = (userDTO) session.getAttribute("user");

        userInformationDAO udao = new userInformationDAO();

        List<userInformationDTO> list = udao.getUserInformationsByUser(user.getUserId());
        
        request.setAttribute("listAddress", list);
        request.getRequestDispatcher("managerAddress.jsp").forward(request, response);

    }

    protected void loadAddressListForPayment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        userDTO user = (userDTO) session.getAttribute("user");

        userInformationDAO udao = new userInformationDAO();

        List<userInformationDTO> list = udao.getUserInformationsByUser(user.getUserId());
        request.setAttribute("listAddress", list);
        request.getRequestDispatcher("payment.jsp").forward(request, response);

    }

    protected void processUseSelectedAddress(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        userDTO user = (userDTO) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String selectedInfoId = request.getParameter("selectedAddress");

        // Lấy thông tin userAddress từ database
        userInformationDAO udao = new userInformationDAO();
        userInformationDTO selectedAddress = null;
        try {
            selectedAddress = udao.getUserInformationById(selectedInfoId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (selectedAddress != null) {
            // Lưu vào session để hiển thị trên JSP
            session.setAttribute("selectedAddress", selectedAddress);
            // Thêm cờ thông báo
            request.setAttribute("msg", "✅ Đã chọn địa chỉ thành công!");
        } else {
            session.setAttribute("selectedAddress", selectedAddress);
            // Thêm cờ thông báo
            request.setAttribute("msg", "chọn địa khong thành công!");
        }

        // Load lại listAddress để dropdown vẫn đầy đủ
        List<userInformationDTO> listAddress = udao.getUserInformationsByUser(user.getUserId());
        request.setAttribute("listAddress", listAddress);

        // Quay lại payment.jsp
        request.getRequestDispatcher("paymentController").forward(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        userDTO user = (userDTO) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }


        if (action.equals("add")) {
            processAdd(request, response);
        } else if (action.equals("update")) {
            processUpdate(request, response);
        } else if (action.equals("delete")) {
            processDelete(request, response);
        } else if (action.equals("view")) {
            loadAddressList(request, response);
        } else if (action.equals("useSelectedAddress")) {
            processUseSelectedAddress(request, response);
        } else {
            loadAddressList(request, response);
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
