/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author Admin
 */
import DTO.orderDTO;
import java.security.cert.CertPathValidatorException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.DbUtils;
import static utils.DbUtils.getConnection;

public class orderDAO {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public orderDTO getCurrentOrder(String userId) {
        orderDTO order = null;
        String sql = "SELECT * FROM tblOrder WHERE userId = ? AND status = 'Processing'";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                order = new orderDTO(
                        rs.getInt("orderId"),
                        rs.getString("userId"),
                        rs.getDate("createdDate"),
                        rs.getString("status"),
                        rs.getDouble("amountPrice")
                );
            } else {

                int newOrderId = createOrder(userId);
                order = new orderDTO();
                order.setOrderId(newOrderId);
                order.setUserId(userId);
                order.setStatus("Processing");
                order.setAmountPrice(0);
                order.setCreatedDate(new java.sql.Date(System.currentTimeMillis()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }

    // Thêm đơn hàng mới (tạm thời amountPrice = 0, cập nhật sau)
    public int createOrder(String userId) {
        String sql = "INSERT INTO tblOrder (userId, status, amountPrice) VALUES (?, 'Processing', 0)";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, userId);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // trả về orderId vừa tạo
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean createOrder(String userId, double totalBill, int infoId) throws SQLException, ClassNotFoundException {
        Connection conn = DbUtils.getConnection();
        String sql = "INSERT INTO tblOrder (userId, amountPrice, status, infoId) VALUES (?, ?, 'Completed', ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, userId);
        ps.setDouble(2, totalBill);
        ps.setInt(3, infoId);
        int row = ps.executeUpdate();
        conn.close();
        return row > 0;
    }

    // Tính tổng amountPrice cho 1 order
    public double calculateAmountPrice(int orderId) {
        String sql = "SELECT SUM(p.price * c.quantity) AS total "
                + "FROM tblCartItem c "
                + "JOIN tblProduct p ON c.productId = p.productId "
                + "WHERE c.orderId = ?";
        double total = 0;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    // Cập nhật tổng tiền đơn hàng
    public void updateAmountPrice(int orderId) {
        double total = calculateAmountPrice(orderId);
        String sql = "UPDATE tblOrder SET amountPrice = ? WHERE orderId = ?";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, total);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lấy danh sách order của 1 user
    public List<orderDTO> getOrdersByUser(String userId) {
        List<orderDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblOrder WHERE userId = ?";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                orderDTO o = new orderDTO(
                        rs.getInt("orderId"),
                        rs.getString("userId"),
                        rs.getDate("createdDate"),
                        rs.getString("status"),
                        rs.getDouble("amountPrice")
                );
                list.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int insertOrder(orderDTO order) {
        String sql = "INSERT INTO tblOrder (userId, amountPrice) VALUES (?, ?)";
        int orderId = -1;

        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            ps.setString(1, order.getUserId());
            ps.setFloat(2, (float) order.getAmountPrice());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Insert failed, no rows affected.");
            }
            try ( ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Insert failed, no ID obtained.");
                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean updateOrderStatus(orderDTO order) {
        String sql = "UPDATE tblOrder SET [status]=? WHERE [orderId]=?";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); // Optional nếu đã load driver
            ps.setString(1, order.getStatus());
            ps.setInt(2, order.getOrderId());
            return ps.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public int createOrder(orderDTO order) throws SQLException, ClassNotFoundException {
        int orderId = -1;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            String sql = "INSERT INTO tblOrder(userId, infoId,status, amountPrice, paymentMethod) VALUES (?, ?,?, ?, ?)";
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, order.getUserId());
            ps.setString(2, order.getInforId());
            ps.setString(3, order.getStatus());
            ps.setDouble(4, order.getAmountPrice());
            ps.setString(5, order.getPaymentMethod());
            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Tạo order thất bại, không có hàng nào được thêm.");
            }

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                orderId = rs.getInt(1);
            } else {
                throw new SQLException("Tạo order thất bại, không lấy được orderId.");
            }

        } catch (SQLException e) {
            // Đây là nơi bạn có thể lấy thông tin lỗi chi tiết
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Message: " + e.getMessage());
            throw e; // ném lại để controller xử lý
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return orderId;
    }

 public List<orderDTO> getAllOrders() throws SQLException, ClassNotFoundException {
        List<orderDTO> list = new ArrayList<>();
        conn = DbUtils.getConnection();
        String sql = "SELECT * FROM tblOrder WHERE status IN ('Spending','Completed','Failed','Unpaid') ORDER BY orderId DESC";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            orderDTO o = new orderDTO();
            o.setOrderId(rs.getInt("orderId"));
            o.setUserId(rs.getString("userId"));
            o.setInforId(rs.getString("infoId")); // có thể null
            o.setAmountPrice(rs.getFloat("amountPrice"));
            o.setPaymentMethod(rs.getString("paymentMethod"));
            o.setStatus(rs.getString("status"));
            o.setCreatedDate(rs.getDate("createdDate"));
            list.add(o);
        }
        rs.close();
        ps.close();
        return list;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        orderDAO dao = new orderDAO();
        List<orderDTO> orders = dao.getAllOrders();
        for (orderDTO order : orders) {
            System.out.println(order);
        }
    }

}
