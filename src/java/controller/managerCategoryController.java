/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.categoryDAO;
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
        action = "list"; // Mặc định là xem danh sách
    }
    
    categoryDAO dao = new categoryDAO();
    String url = "managerCategory.jsp"; // Trang mặc định

    try {
        switch (action) {
            case "delete":
                // Xử lý XÓA MỀM (chuyển sang thùng rác)
                String deleteId = request.getParameter("id");
                dao.softDeleteCategory(deleteId);
                // Dùng sendRedirect để tránh lỗi F5
                response.sendRedirect("managerCategoryController?action=list&deleteSuccess=true"); 
                return; // Dừng lại sau khi redirect

            case "restore":
                // Xử lý KHÔI PHỤC (lấy từ thùng rác)
                String restoreId = request.getParameter("id");
                dao.restoreCategory(restoreId);
                // Tải lại trang thùng rác
                response.sendRedirect("managerCategoryController?action=trash&restoreSuccess=true");
                return; // Dừng lại sau khi redirect

            case "trash":
                // Xử lý XEM THÙNG RÁC (action mới)
                List<categoryDTO> trashList = dao.getDeletedCategories();
                request.setAttribute("trashList", trashList);
                url = "trashCategory.jsp"; // Chuyển đến trang JSP mới
                break;
                
            case "list":
            default:
                // Xử lý XEM DANH SÁCH (mặc định)
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

        if (action != null && action.equals("insert")) {
            try {
                String id = request.getParameter("categoryId");
                String name = request.getParameter("categoryName");

                categoryDTO newCategory = new categoryDTO(id, name);
                categoryDAO dao = new categoryDAO();
                dao.insertCategory(newCategory);

                request.setAttribute("message", "Thêm thành công!");

            } catch (Exception e) {
                request.setAttribute("errorMessage", "Thêm thất bại: " + e.getMessage());
                e.printStackTrace();
            }
        }
        response.sendRedirect("managerCategoryController");
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
