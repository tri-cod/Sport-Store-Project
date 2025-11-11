package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.sql.Date;
import DTO.userDTO;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import utils.DbUtils;

public class userDAO {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    private String email_regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    // 🔹 Lấy thông tin user theo userName
    public userDTO getUserById(String userName) {
        userDTO user = null;
        try {
            Connection conn = DbUtils.getConnection();
            String sql = "SELECT * FROM tblUser WHERE userName = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, userName);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                user = new userDTO();
                user.setUserId(rs.getString("userId"));
                user.setUserName(rs.getString("userName"));
                user.setEmail(rs.getString("email"));
                user.setFullName(rs.getString("userFullname"));
                user.setPassword(rs.getString("userPassword"));
                user.setDateOfBirth(rs.getDate("dateOfBirth"));
                user.setIsAdmin(rs.getBoolean("isAdmin"));
            }
            rs.close();
            pst.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    // 🔹 Kiểm tra đăng nhập
    public boolean login(String userName, String password) {
        try {
            userDTO user = getUserById(userName);
            if (user != null) { // chỉ cho phép nếu user đang active
                return user.getPassword().equals(password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 🔹 Thêm user mới vào database
    public boolean insertUser(userDTO user) {
        boolean check = false;

        try {
            con = DbUtils.getConnection();
            if (con != null) {
                String userID;
                do {
                    // Tạo ID ngẫu nhiên 7 ký tự (CMxxxxxx)
                    int randomNum = new Random().nextInt(1000000); // 0 đến 999,999
                    userID = "CM" + String.format("%06d", randomNum);
                    // Bạn cần tạo thêm một phương thức checkUserIdExist(userID) trong DAO
                } while (checkUserIdExist(userID));
                if (user.getEmail() == null || !user.getEmail().matches(email_regex)) {
                    System.out.println("❌ Email không hợp lệ hoặc bị trống: " + user.getEmail());
                    return false;
                }
                String sql = "INSERT INTO tblUser(userId, userFullname, userName,userPassword, email , dateOfBirth) "
                        + "VALUES(?, ?, ?, ?, ?, ?)";

                ps = con.prepareStatement(sql);
                ps.setString(1, userID);
                ps.setString(2, user.getFullName());
                ps.setString(3, user.getUserName());
                ps.setString(4, user.getPassword());
                ps.setString(5, user.getEmail());
                ps.setDate(6, user.getDateOfBirth());

                check = ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); //
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return check;
    }

// Trong userDAO.java
    public userDTO checkAccountExist(String userName) {
        userDTO user = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            if (conn != null) {
                // Dùng SELECT * để lấy đủ các cột và kiểm tra tồn tại
                String sql = "SELECT * FROM tblUser WHERE userName = ?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, userName);

                rs = pst.executeQuery();

                if (rs.next()) {
                    // Nếu tìm thấy, gán dữ liệu vào userDTO (đây là logic quan trọng)
                    user = new userDTO();
                    user.setUserId(rs.getString("userId"));
                    user.setUserName(rs.getString("userName"));
                    user.setEmail(rs.getString("email"));
                    user.setFullName(rs.getString("userFullname"));
                    user.setPassword(rs.getString("userPassword"));
                    user.setDateOfBirth(rs.getDate("dateOfBirth"));
                    user.setIsAdmin(rs.getBoolean("isAdmin"));
                    // Chú ý: Có thể cần thêm các trường khác nếu có (ví dụ: role, status)
                }
            }
        } catch (Exception e) {
            // DÒNG NÀY CỰC KỲ QUAN TRỌNG: In lỗi ra console để bạn biết lý do thất bại DB
            System.err.println("Database Error in checkAccountExist: " + e.getMessage());
            e.printStackTrace();

            // Nếu có lỗi DB (ví dụ: kết nối, sai tên cột), nó sẽ trả về NULL, 
            // và Controller sẽ nghĩ tài khoản chưa tồn tại.
            // Đây là lý do tại sao bạn cần kiểm tra Console.
            user = null;
        } finally {
            // Khối finally đảm bảo tất cả tài nguyên được đóng dù có lỗi hay không
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace(); // In lỗi đóng tài nguyên (nếu có)
            }
        }
        // Trả về DTO nếu tồn tại, hoặc NULL nếu không tồn tại HOẶC CÓ LỖI DB
        return user;
    }
    // Trong userDAO.java (Thêm mới)
// 🔹 Kiểm tra Email đã tồn tại

// userDAO.java
    // Trong userDAO.java
    public boolean checkEmailExist(String email) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        boolean exists = false;
        try {
            conn = DbUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT 1 FROM tblUser WHERE email = ?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, email);

                rs = pst.executeQuery();
                exists = rs.next(); // True nếu tìm thấy
            }
        } catch (Exception e) {
            System.err.println("Database Error in checkEmailExist: " + e.getMessage());
            e.printStackTrace();
            // Cực kỳ quan trọng: Nếu lỗi DB, nên trả về TRUE để ngăn INSERT 
            // và báo lỗi chung cho người dùng, thay vì để đăng ký thành công.
            return true; // Giả định là TỒN TẠI khi không thể kiểm tra
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return exists;
    }
// 🔹 Kiểm tra UserID đã tồn tại

    public boolean checkUserIdExist(String userId) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            String sql = "SELECT 1 FROM tblUser WHERE userId = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, userId);

            rs = pst.executeQuery();
            boolean exists = rs.next(); // True nếu tìm thấy bản ghi

            // Đóng tài nguyên
            rs.close();
            pst.close();
            conn.close();
            return exists;

        } catch (Exception e) {
            e.printStackTrace();
            // Nếu có lỗi DB xảy ra trong quá trình kiểm tra, giả định là ID chưa tồn tại 
            // để vòng lặp tiếp tục, hoặc trả về true để lỗi dừng lại.
            // Tốt nhất là in lỗi và dừng (trả về true) để tránh tạo ID lỗi.
            return true;
        }
    }
// 🔹 Kiểm tra email có trùng với một username đã tồn tại không

    public boolean checkEmailSameAsUsername(String email) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        boolean exists = false;
        try {
            conn = DbUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT 1 FROM tblUser WHERE userName = ?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, email);
                rs = pst.executeQuery();
                exists = rs.next();
            }
        } catch (Exception e) {
            System.err.println("Database Error in checkEmailSameAsUsername: " + e.getMessage());
            e.printStackTrace();
            // Trả về true nếu có lỗi DB để tránh cho phép đăng ký sai
            return true;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return exists;
    }

// 🔹 Kiểm tra username có trùng với email của người khác không
    public boolean checkUsernameSameAsEmail(String username) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        boolean exists = false;
        try {
            conn = DbUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT 1 FROM tblUser WHERE email = ?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, username);
                rs = pst.executeQuery();
                exists = rs.next();
            }
        } catch (Exception e) {
            System.err.println("Database Error in checkUsernameSameAsEmail: " + e.getMessage());
            e.printStackTrace();
            return true;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return exists;
    }

    public static void main(String[] args) {
        userDAO dao = new userDAO();
        userDTO u = dao.getUserById("admin");
        System.out.println(u);
    }
}
