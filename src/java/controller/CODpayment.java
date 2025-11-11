/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.cartItemDAO;
import DAO.orderDAO;
import DTO.cartItemDTO;
import DTO.orderDTO;
import DTO.userDTO;
import DTO.userInformationDTO;
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
@WebServlet(name = "CODpayment", urlPatterns = {"/CODpayment"})
public class CODpayment extends HttpServlet {

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
       

        HttpSession session = request.getSession();
        userDTO user = (userDTO) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        userInformationDTO selectedAddress = (userInformationDTO) session.getAttribute("selectedAddress");
        if (selectedAddress == null) {
            request.setAttribute("msg", "⚠️ Bạn chưa chọn địa chỉ giao hàng!");
            request.getRequestDispatcher("payment.jsp").forward(request, response);
            return;
        }
        
        String action = request.getParameter("action");
        if ("done".equals(action)) {
            try {
                orderDAO orderDao = new orderDAO();
                cartItemDAO cartDao = new cartItemDAO();

                // Lấy đơn hàng hiện tại để tính tổng
                orderDTO currentOrder = orderDao.getCurrentOrder(user.getUserId());
                orderDao.calculateAmountPrice(currentOrder.getOrderId());
                orderDao.updateAmountPrice(currentOrder.getOrderId());
                double totalAmount = currentOrder.getAmountPrice();

                // Tạo order mới
                orderDTO order = new orderDTO();
                order.setUserId(user.getUserId());
                order.setInforId(selectedAddress.getInforId());
                order.setStatus("Unpaid");
                order.setAmountPrice(totalAmount);
                order.setPaymentMethod("COD");

                int orderId = orderDao.createOrder(order); // trả về orderId mới tạo
                if (orderId < 1) {
                    request.setAttribute("msg", "❌ Tạo đơn hàng thất bại! orderId trả về: " + orderId);
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    return;
                }

                // Lấy sản phẩm trong giỏ để hiển thị (nếu muốn)
                List<cartItemDTO> list = cartDao.getCartItemsByOrder(currentOrder.getOrderId());
                request.setAttribute("listP", list);
                request.setAttribute("amountPrice", totalAmount);
                request.setAttribute("selectedAddress", selectedAddress);

              

                // Forward sang paymentResult.jsp
                request.setAttribute("transResult", true); // đặt biến hiển thị thanh toán thành công
                request.getRequestDispatcher("paymentResult.jsp").forward(request, response);

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("msg", "❌ Lỗi hệ thống: " + e.getMessage());
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
            return; // tránh thực thi phần code bên dưới
        }
        try {

            orderDAO orderDao = new orderDAO();
            cartItemDAO cartDao = new cartItemDAO();
            orderDTO currentOrder = orderDao.getCurrentOrder(user.getUserId());
            orderDao.calculateAmountPrice(currentOrder.getOrderId());
            orderDao.updateAmountPrice(currentOrder.getOrderId());
            double totalAmount = currentOrder.getAmountPrice();
            // Tạo order mới với trạng thái COD
            orderDTO order = new orderDTO();
            order.setUserId(user.getUserId());
            order.setInforId(selectedAddress.getInforId());// lưu inforId
            order.setStatus("Unpaid");
            order.setAmountPrice(totalAmount);
            order.setPaymentMethod("COD");

            orderDAO dao = new orderDAO();
            int orderId = dao.createOrder(order); // trả về orderId mới tạo
            if (orderId < 1) {
                // Thiết lập thông báo lỗi
                request.setAttribute("msg", "❌ Tạo đơn hàng thất bại! orderId trả về: " + orderId);
                // Forward đến trang hiển thị lỗi (có thể là cartController hoặc error.jsp)
                request.getRequestDispatcher("error.jsp").forward(request, response);
                return;
            }
            // Lấy sản phẩm trong giỏ để hiển thị

            List<cartItemDTO> list = cartDao.getCartItemsByOrder(currentOrder.getOrderId());
            request.setAttribute("listP", list);
            request.setAttribute("amountPrice", currentOrder.getAmountPrice());
            request.setAttribute("selectedAddress", selectedAddress);
            request.getRequestDispatcher("confirmCODpayment.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "❌ Lỗi hệ thống: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
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
