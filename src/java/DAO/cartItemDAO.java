package DAO;

import DTO.cartItemDTO;
import DTO.productDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static utils.DbUtils.getConnection;

public class cartItemDAO {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

  

    private void closeConnection() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 1️⃣ Thêm sản phẩm vào giỏ hàng
    public void addCartItem(int orderId, String productId, int quantity) {
        try {
            conn = getConnection();

            // Kiểm tra sản phẩm đã có trong giỏ chưa
            String checkSql = "SELECT quantity FROM tblCartItem WHERE orderId = ? AND productId = ?";
            ps = conn.prepareStatement(checkSql);
            ps.setInt(1, orderId);
            ps.setString(2, productId);
            rs = ps.executeQuery();
            if (rs.next()) {
                
                int currentQty = rs.getInt("quantity");
                String updateSql = "UPDATE tblCartItem SET quantity = ? WHERE orderId = ? AND productId = ?";
                ps = conn.prepareStatement(updateSql);
                ps.setInt(1, currentQty + quantity);
                ps.setInt(2, orderId);
                ps.setString(3, productId);
                ps.executeUpdate();
            } else {
                // Nếu chưa có, insert mới
                String insertSql = "INSERT INTO tblCartItem(orderId, productId, quantity) VALUES (?, ?, ?)";
                ps = conn.prepareStatement(insertSql);
                ps.setInt(1, orderId);
                ps.setString(2, productId);
                ps.setInt(3, quantity);
                ps.executeUpdate();
            }

            // Cập nhật lại tổng tiền của order
            updateOrderAmount(orderId);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    // 2️⃣ Cập nhật số lượng sản phẩm trong giỏ
    public void updateCartItem(int orderId, String productId, int quantity) {
        try {
            conn = getConnection();
            String sql = "UPDATE tblCartItem SET quantity = ? WHERE orderId = ? AND productId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, quantity);
            ps.setInt(2, orderId);
            ps.setString(3, productId);
            ps.executeUpdate();

            // Cập nhật tổng tiền
            updateOrderAmount(orderId);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    // 3️⃣ Xóa sản phẩm khỏi giỏ
    public void deleteCartItem(int orderId, String productId) {
        try {
            conn = getConnection();
            String sql = "DELETE FROM tblCartItem WHERE orderId = ? AND productId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            ps.setString(2, productId);
            ps.executeUpdate();

            // Cập nhật tổng tiền
            updateOrderAmount(orderId);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    // 4️⃣ Lấy danh sách cartItem theo orderId
    public List<cartItemDTO> getCartItemsByOrder(int orderId) {
        List<cartItemDTO> list = new ArrayList<>();
        try {
            conn = getConnection();
            String sql = "SELECT c.cartItemId, c.orderId, c.productId, c.quantity, p.price, p.productName " +
                         "FROM tblCartItem c " +
                         "JOIN tblProduct p ON c.productId = p.productId " +
                         "WHERE c.orderId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();
            while (rs.next()) {
                cartItemDTO item = new cartItemDTO();
                item.setCartItemId(rs.getInt("cartItemId"));
                item.setOrderId(rs.getInt("orderId"));
                item.setProductId(rs.getString("productId"));
                item.setQuantity(rs.getInt("quantity"));
                item.setUnitPrice(rs.getDouble("price"));
                item.setTotalPrice(rs.getInt("quantity") * rs.getDouble("price"));

                // Optional: gắn productDTO
                productDTO product = new productDTO();
                product.setProductId(rs.getString("productId"));
                product.setProductName(rs.getString("productName"));
                product.setPrice( rs.getFloat("price"));
                item.setProduct(product);

                list.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return list;
    }

    // 5️⃣ Cập nhật tổng tiền order
    private void updateOrderAmount(int orderId) {
        try {
            conn = getConnection();
            String sql = "UPDATE tblOrder SET amountPrice = " +
                         "(SELECT SUM(p.price * c.quantity) FROM tblCartItem c " +
                         "JOIN tblProduct p ON c.productId = p.productId " +
                         "WHERE c.orderId = ?) " +
                         "WHERE orderId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}