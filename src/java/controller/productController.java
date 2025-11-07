/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.productDAO;
import DTO.productDTO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Admin
 */
@MultipartConfig
@WebServlet(name = "productController", urlPatterns = {"/productController"})
public class productController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void loadAllProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        productDAO dao = new productDAO();
        List<productDTO> ListP = dao.getALlProduct();

        request.setAttribute("listP", ListP);
        request.getRequestDispatcher("managerProduct.jsp").forward(request, response);

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        loadAllProduct(request, response);
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
        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "insert":
                request.getRequestDispatcher("insertproduct.jsp").forward(request, response);
                break;

            case "list":
            default:
                loadAllProduct(request, response);
                break;
        }
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
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if (action != null && action.equals("insertProcess")) {

            try {
                // 1. Lấy các trường text như cũ
                String id = request.getParameter("productId");
                String name = request.getParameter("productName");
                float price = Float.parseFloat(request.getParameter("price"));
                String size = request.getParameter("size");
                // Bỏ dòng 'String image = ...' cũ
                String color = request.getParameter("color");
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                String description = request.getParameter("description");
                String categoryId = request.getParameter("categoryId");

                // 2. Xử lý file upload
                Part filePart = request.getPart("imageFile"); // "imageFile" là 'name' từ form
                InputStream fileContent = filePart.getInputStream();

                // Chuyển InputStream (file) sang byte[]
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096]; // Buffer 4KB
                int bytesRead;
                while ((bytesRead = fileContent.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
                byte[] imageBytes = output.toByteArray();

                // 3. Mã hóa byte[] sang String Base64
                String imageBase64 = Base64.getEncoder().encodeToString(imageBytes);

                // 4. Đóng streams
                fileContent.close();
                output.close();

                // 5. Tạo DTO với chuỗi Base64
                productDTO newProduct = new productDTO(id, name, price, size,
                        imageBase64, // Dùng biến Base64
                        color, quantity, description, categoryId);

                productDAO dao = new productDAO();
                dao.insertProduct(newProduct);

                response.sendRedirect("productController");

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Thêm mới thất bại: " + e.getMessage());
                request.getRequestDispatcher("insert-product.jsp").forward(request, response);
            }
        } else {
            loadAllProduct(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}// </editor-fold>

