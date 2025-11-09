// Dán đè toàn bộ code này vào file productController.java
package controller;

import models.productDAO;
import DTO.productDTO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
@WebServlet(name = "productController", urlPatterns = {"/productController"})
public class productController extends HttpServlet {

    protected void loadAllProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        productDAO dao = new productDAO();
        List<productDTO> ListP = dao.getALlProduct(); // Đã sửa (chỉ lấy status=1)
        request.setAttribute("listP", ListP);
        request.getRequestDispatcher("managerProduct.jsp").forward(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        loadAllProduct(request, response);
    }

    // === NÂNG CẤP HÀM doGet ===
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        productDAO dao = new productDAO();
        String url = "managerProduct.jsp";

        try {
            switch (action) {
                case "delete":
                    String deleteId = request.getParameter("id");
                    dao.softDeleteProduct(deleteId);
                    response.sendRedirect("productController?action=list&deleteSuccess=true");
                    return;

                case "edit":
                    String editId = request.getParameter("id");
                    productDTO productToEdit = dao.getProductById(editId);
                    request.setAttribute("productToEdit", productToEdit);
                    url = "editProduct.jsp"; // Chuyển đến trang 'editProduct.jsp'
                    break;

                case "insert":
                    url = "insertproduct.jsp";
                    break;
                case "search":
                    // 1. Lấy từ khóa tìm kiếm
                    String query = request.getParameter("txtSearch");

                    // 2. Gọi DAO (hàm searchByName đã được sửa)
                    List<productDTO> list = dao.searchByName(query);

                    // 3. Gửi danh sách tìm được về JSP
                    request.setAttribute("listP", list);

                    // 4. Gửi lại từ khóa để hiển thị trên ô tìm kiếm
                    request.setAttribute("searchKey", query);

                    url = "managerProduct.jsp";
                    break;

                case "list":
                default:
                    loadAllProduct(request, response);
                    return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Đã xảy ra lỗi: " + e.getMessage());
            // Nếu có lỗi, vẫn phải tải lại danh sách
            try {
                List<productDTO> ListP = dao.getALlProduct();
                request.setAttribute("listP", ListP);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            request.getRequestDispatcher("managerProduct.jsp").forward(request, response);
        }
        request.getRequestDispatcher(url).forward(request, response);
    }

    // === NÂNG CẤP HÀM doPost ===
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        productDAO dao = new productDAO();

        try {
            if (action != null && action.equals("insertProcess")) {
                // === LOGIC THÊM MỚI (Code cũ của bạn, đã đúng) ===
                String id = request.getParameter("productId");
                String name = request.getParameter("productName");
                float price = Float.parseFloat(request.getParameter("price"));
                String size = request.getParameter("size");
                String color = request.getParameter("color");
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                String description = request.getParameter("description");
                String categoryId = request.getParameter("categoryId");

                Part mainImagePart = request.getPart("mainImage");
                String mainImageBase64 = convertPartToBase64(mainImagePart);

                List<String> galleryBase64List = new ArrayList<>();
                Collection<Part> parts = request.getParts();
                for (Part part : parts) {
                    if ("galleryImages".equals(part.getName()) && part.getSize() > 0) {
                        galleryBase64List.add(convertPartToBase64(part));
                    }
                }
                productDTO newProduct = new productDTO(id, name, price, size,
                        mainImageBase64, color, quantity, description, categoryId);
                newProduct.setGalleryImages(galleryBase64List);
                dao.insertProduct(newProduct);
                response.sendRedirect("productController");

            } else if (action != null && action.equals("updateProcess")) {
                // === LOGIC CẬP NHẬT (MỚI) ===

                String id = request.getParameter("productId");
                String name = request.getParameter("productName");
                float price = Float.parseFloat(request.getParameter("price"));
                String size = request.getParameter("size");
                String color = request.getParameter("color");
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                String description = request.getParameter("description");
                String categoryId = request.getParameter("categoryId");

                Part mainImagePart = request.getPart("mainImage");
                String mainImageBase64 = convertPartToBase64(mainImagePart);

                List<String> galleryBase64List = new ArrayList<>();
                Collection<Part> parts = request.getParts();
                for (Part part : parts) {
                    if ("galleryImages".equals(part.getName()) && part.getSize() > 0) {
                        galleryBase64List.add(convertPartToBase64(part));
                    }
                }

                productDTO updatedProduct = new productDTO(id, name, price, size,
                        mainImageBase64,
                        color, quantity, description, categoryId);

                if (!galleryBase64List.isEmpty()) {
                    updatedProduct.setGalleryImages(galleryBase64List);
                }

                dao.updateProduct(updatedProduct);
                response.sendRedirect("productController");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Thao tác thất bại: " + e.getMessage());

            if ("updateProcess".equals(action)) {
                String id = request.getParameter("productId");
                productDTO productToEdit = dao.getProductById(id);
                request.setAttribute("productToEdit", productToEdit);
                request.getRequestDispatcher("editProduct.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("insertproduct.jsp").forward(request, response);
            }
        }
    }

    // (Hàm convertPartToBase64() của bạn - Giữ nguyên)
    private String convertPartToBase64(Part part) throws IOException {
        if (part == null || part.getSize() == 0) {
            return null;
        }
        InputStream fileContent = part.getInputStream();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = fileContent.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
        byte[] imageBytes = output.toByteArray();
        String imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
        fileContent.close();
        output.close();
        return imageBase64;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
