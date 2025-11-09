/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;


import DAO.cartItemDAO;
import DAO.orderDAO;
import DAO.productDAO;
import DTO.cartItemDTO;
import DTO.orderDTO;
import DTO.productDTO;
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
@WebServlet(name = "addToCartController", urlPatterns = {"/addToCartController"})
public class addToCartController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String productId = request.getParameter("productId");
        String qStr = request.getParameter("quantity");
        String size = request.getParameter("size");
        String color = request.getParameter("color");

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        userDTO user = (userDTO) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        if (productId == null || qStr == null) {
            request.setAttribute("msg", "Thiếu dữ liệu sản phẩm hoặc số lượng.");
            request.getRequestDispatcher("detail.jsp").forward(request, response);
            return;
        }

        int quantity = 1;
        try {
            quantity = Integer.parseInt(qStr);
            if (quantity <= 0) {
                quantity = 1;
            }
        } catch (NumberFormatException ex) {
            quantity = 1;
        }

        // đảm bảo size / color không null (nếu bạn muốn mặc định)
        if (size == null) {
            size = "";
        }
        if (color == null) {
            color = "";
        }

        try {
            orderDAO orderDAO = new orderDAO();
            cartItemDAO cartItemDAO = new cartItemDAO();
            productDAO productDAO = new productDAO();

            // kiểm tra product tồn tại
            productDTO product = productDAO.getProductById(productId);
            if (product == null) {
                request.setAttribute("msg", "Sản phẩm không tồn tại.");
                request.getRequestDispatcher("detail.jsp").forward(request, response);
                return;
            }

            // Lấy hoặc tạo cart (ACTIVE)
            orderDTO orderID = orderDAO.getCurrentOrder(user.getUserId());

            // Lấy toàn bộ cartItem hiện tại của order
            List<cartItemDTO> cartItems = cartItemDAO.getCartItemsByOrder(orderID.getOrderId());

// Tìm xem sản phẩm đã có trong giỏ (theo productId + size + color)
            cartItemDTO existingItem = null;
            for (cartItemDTO item : cartItems) {
                // Giả sử cartItemDTO có getSize() và getColor()
                if (item.getProductId().equals(productId)
                        && item.getSize().equals(size)
                        && item.getColor().equals(color)) {
                    existingItem = item;
                    break;
                }
            }

            if (existingItem != null) {
                // Nếu đã có -> cộng dồn số lượng
                int newQty = existingItem.getQuantity() + quantity;
                cartItemDAO.updateCartItem(orderID.getOrderId(), productId, quantity);
            } else {
                // Nếu chưa có -> thêm mới
                cartItemDAO.addCartItem(orderID.getOrderId(), productId, quantity);
            }

            // Thêm msg và forward về detail (hoặc redirect về viewCartController nếu muốn)
            request.setAttribute("msg", "Thêm vào giỏ hàng thành công!");
            // Cập nhật lại detail để hiển thị (nếu detail.jsp dựa vào attribute detail)
            request.setAttribute("cCart", orderID);
            request.setAttribute("detail", productDAO.getProductById(productId));
            request.getRequestDispatcher("detail.jsp").forward(request, response);

        } catch (Exception ex) {
            ex.printStackTrace(); // **rất quan trọng**: xem stacktrace trong logs
            request.setAttribute("msg", "Lỗi server: " + ex.getMessage());
            request.getRequestDispatcher("detail.jsp").forward(request, response);
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
