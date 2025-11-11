// Dán đè toàn bộ code này vào file productDAO.java
package DAO;

import DTO.productDTO;
import DTO.userDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DbUtils;

public class productDAO {

    public void insertProduct(productDTO product) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO tblProduct (productId, productName, price, size, imageBase64, color, quantity, description, categoryID)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        try {
            conn = DbUtils.getConnection();
            conn.setAutoCommit(false); // Bắt đầu Transaction

            // BƯỚC A: Thêm sản phẩm chính vào tblProduct
            try ( PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, product.getProductId());
                ps.setString(2, product.getProductName());
                ps.setFloat(3, product.getPrice());
                ps.setString(4, product.getSize());
                ps.setString(5, product.getImage()); // Ảnh đại diện
                ps.setString(6, product.getColor());
                ps.setInt(7, product.getQuantity());
                ps.setString(8, product.getDescription());
                ps.setString(9, product.getCategoryId());
                ps.executeUpdate();
            }

            // BƯỚC B: Thêm các ảnh gallery vào tblProductImages
            if (product.getGalleryImages() != null) {
                for (String imageBase64 : product.getGalleryImages()) {
                    insertGalleryImage(conn, product.getProductId(), imageBase64);
                }
            }

            conn.commit(); // Hoàn tất Transaction

        } catch (Exception e) {
            if (conn != null) {
                conn.rollback(); // Hoàn tác nếu có lỗi
            }
            e.printStackTrace();
            throw new SQLException(e.getMessage()); // Ném lỗi ra
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true); // Trả lại autoCommit
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // (Các biến conn, ps, rs cũ không cần thiết nếu dùng biến cục bộ)
    // ĐÃ SỬA: Thêm "WHERE status = 1"
    public List<productDTO> getALlProduct() {
        List<productDTO> list = new ArrayList<>();
        String qr = "select * from tblProduct WHERE status = 1"; // Sửa ở đây
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(qr);
            rs = ps.executeQuery();
            while (rs.next()) {
                productDTO p = new productDTO(
                        rs.getString("productId"),
                        rs.getString("productName"),
                        rs.getFloat("price"),
                        rs.getString("size"),
                        rs.getString("imageBase64"),
                        rs.getString("color"),
                        rs.getInt("quantity"),
                        rs.getString("description"),
                        rs.getString("categoryID")
                );
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    // ĐÃ SỬA: Thêm "AND status = 1"
    public productDTO getProductById(String id) {
        productDTO p = null;
        String sqlProduct = "SELECT * FROM tblProduct WHERE productId = ? AND status = 1"; // Sửa ở đây
        String sqlGallery = "SELECT ImageBase64 FROM tblProductImages WHERE ProductID = ?";
        Connection conn = null;
        PreparedStatement psProduct = null;
        ResultSet rsProduct = null;
        PreparedStatement psGallery = null;
        ResultSet rsGallery = null;
        try {
            conn = DbUtils.getConnection();
            psProduct = conn.prepareStatement(sqlProduct);
            psProduct.setString(1, id);
            rsProduct = psProduct.executeQuery();
            if (rsProduct.next()) {
                p = new productDTO(
                        rsProduct.getString("productId"),
                        rsProduct.getString("productName"),
                        rsProduct.getFloat("price"),
                        rsProduct.getString("size"),
                        rsProduct.getString("imageBase64"),
                        rsProduct.getString("color"),
                        rsProduct.getInt("quantity"),
                        rsProduct.getString("description"),
                        rsProduct.getString("categoryID")
                );
                List<String> gallery = new ArrayList<>();
                psGallery = conn.prepareStatement(sqlGallery);
                psGallery.setString(1, id);
                rsGallery = psGallery.executeQuery();
                while (rsGallery.next()) {
                    gallery.add(rsGallery.getString("ImageBase64"));
                }
                p.setGalleryImages(gallery);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rsProduct != null) {
                    rsProduct.close();
                }
                if (psProduct != null) {
                    psProduct.close();
                }
                if (rsGallery != null) {
                    rsGallery.close();
                }
                if (psGallery != null) {
                    psGallery.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return p;
    }

    // ĐÃ SỬA: Thêm "AND status = 1"
    public List<productDTO> searchByName(String txtSearch) {
        List<productDTO> list = new ArrayList<>();
        String qr = "select * from tblProduct where [productName] like ? AND status = 1"; // Sửa ở đây
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(qr);
            ps.setString(1, "%" + txtSearch + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new productDTO(
                        rs.getString("productId"),
                        rs.getString("productName"),
                        rs.getFloat("price"),
                        rs.getString("size"),
                        rs.getString("imageBase64"),
                        rs.getString("color"),
                        rs.getInt("quantity"),
                        rs.getString("description"),
                        rs.getString("categoryID")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    // HÀM private insertGalleryImage (Giữ nguyên)
    // Hàm insertProduct (Giữ nguyên)
    // HÀM HỖ TRỢ MỚI (private)
    private void insertGalleryImage(Connection conn, String productId, String imageBase64) throws SQLException {
        String sql = "INSERT INTO tblProductImages (ProductID, ImageBase64) VALUES (?, ?)";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, productId);
            ps.setString(2, imageBase64);
            ps.executeUpdate();
        }
        // Không đóng Connection ở đây!
    }

    // --- CÁC HÀM MỚI ---
    // HÀM MỚI 1: XÓA MỀM (SOFT DELETE)
    public void softDeleteProduct(String id) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE tblProduct SET status = 0 WHERE productId = ?";
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }

    // HÀM MỚI 2: XÓA ẢNH GALLERY CŨ (Dùng cho việc Sửa)
    public void deleteGalleryImages(String productId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM tblProductImages WHERE ProductID = ?";
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, productId);
            ps.executeUpdate();
        }
    }

    // HÀM MỚI 3: CẬP NHẬT SẢN PHẨM (UPDATE)
    public void updateProduct(productDTO product) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE tblProduct SET "
                + "productName = ?, price = ?, size = ?, "
                + "color = ?, quantity = ?, description = ?, categoryID = ? "
                + (product.getImage() != null ? ", imageBase64 = ? " : "") // Chỉ update ảnh nếu có ảnh mới
                + "WHERE productId = ?";
        Connection conn = null;

        try {
            conn = DbUtils.getConnection();
            conn.setAutoCommit(false); // Bắt đầu Transaction

            // BƯỚC A: Cập nhật bảng tblProduct
            try ( PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, product.getProductName());
                ps.setFloat(2, product.getPrice());
                ps.setString(3, product.getSize());
                ps.setString(4, product.getColor());
                ps.setInt(5, product.getQuantity());
                ps.setString(6, product.getDescription());
                ps.setString(7, product.getCategoryId());

                int paramIndex = 8;
                if (product.getImage() != null) {
                    ps.setString(paramIndex++, product.getImage());
                }
                ps.setString(paramIndex, product.getProductId());
                ps.executeUpdate();
            }

            // BƯỚC B: Cập nhật ảnh gallery (Xóa cũ, Thêm mới)
            if (product.getGalleryImages() != null && !product.getGalleryImages().isEmpty()) {
                deleteGalleryImages(product.getProductId());
                for (String imageBase64 : product.getGalleryImages()) {
                    insertGalleryImage(conn, product.getProductId(), imageBase64);
                }
            }

            conn.commit(); // Hoàn tất Transaction

        } catch (Exception e) {
            if (conn != null) {
                conn.rollback(); // Hoàn tác
            }
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    public static void main(String[] args) {
        productDAO dao = new productDAO();
        productDTO u = dao.getProductById("P01");
        System.out.println(u);
    }

}
