/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import models.categoryDAO;
import DTO.categoryDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author btkha
 */
@WebServlet(name = "managerCategoryController", urlPatterns = {"/managerCategoryController"})
public class managerCategoryController extends HttpServlet {

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
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet managerCategoryController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet managerCategoryController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        categoryDAO dao = new categoryDAO();
        String url = "managerCategory.jsp";

        try {
            switch (action) {
                case "delete":
                    String deleteId = request.getParameter("id");
                    dao.softDeleteCategory(deleteId);
                    response.sendRedirect("managerCategoryController?action=list&deleteSuccess=true");
                    return;

                case "restore":
                    String restoreId = request.getParameter("id");
                    dao.restoreCategory(restoreId);
                    response.sendRedirect("managerCategoryController?action=trash&restoreSuccess=true");
                    return;

                case "trash":
                    List<categoryDTO> trashList = dao.getDeletedCategories();
                    request.setAttribute("trashList", trashList);
                    url = "trashCategory.jsp";
                    break;

                // === CASE MỚI ĐỂ SỬA ===
                case "edit":
                    String editId = request.getParameter("id");
                    // 1. Lấy thông tin của category cần sửa
                    categoryDTO categoryToEdit = dao.getCategoryById(editId);
                    request.setAttribute("categoryToEdit", categoryToEdit);
                // 2. *Fall-through* (chạy tiếp) xuống case 'list' 
                //    để lấy cả danh sách

                case "list":
                default:
                    // Luôn luôn lấy danh sách (kể cả khi đang Sửa)
                    List<categoryDTO> categoryList = dao.getAllCategories();
                    request.setAttribute("categoryList", categoryList);
                    url = "managerCategory.jsp";
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Đã xảy ra lỗi: " + e.getMessage());
        }

        request.getRequestDispatcher(url).forward(request, response);
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
        categoryDAO dao = new categoryDAO();

        try {
            if (action != null && action.equals("insert")) {
                // === LOGIC THÊM MỚI (đã có) ===
                String id = request.getParameter("categoryId");
                String name = request.getParameter("categoryName");
                categoryDTO newCategory = new categoryDTO(id, name);
                dao.insertCategory(newCategory);

            } else if (action != null && action.equals("updateProcess")) {
                // === LOGIC CẬP NHẬT (mới) ===
                String id = request.getParameter("categoryId"); // Lấy ID
                String name = request.getParameter("categoryName"); // Lấy tên mới
                dao.updateCategory(id, name); // Gọi hàm update
            }
        } catch (Exception e) {
            // Nếu có lỗi (ví dụ trùng ID), báo lỗi
            request.setAttribute("errorMessage", "Thao tác thất bại: " + e.getMessage());
            e.printStackTrace();
            // Nếu lỗi, forward lại trang (kèm báo lỗi)
            doGet(request, response);
            return;
        }

        // Nếu thành công (Insert hoặc Update), redirect về trang list
        response.sendRedirect("managerCategoryController?action=list");
    }

    @Override
    public String getServletInfo() {
        return "Category Controller";
    }
}
