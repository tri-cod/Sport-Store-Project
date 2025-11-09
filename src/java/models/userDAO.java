// File: models/userDAO.java
// ❗❗ DÙNG FILE NÀY - ĐÃ SỬA LỖI TRANSACTION (COMMIT/ROLLBACK) ❗❗
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import DTO.userDTO;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Random;
import utils.DbUtils;

public class userDAO {

    // (Hàm này chỉ ĐỌC, không cần sửa)
    public userDTO getUserByUsername(String userName) {
        userDTO user = null;
        String sql = "SELECT * FROM tblUser WHERE userName = ?";
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, userName);
            rs = pst.executeQuery();

            if (rs.next()) {
                user = new userDTO();
                user.setUserId(rs.getString("userId"));
                user.setUserName(rs.getString("userName"));
                user.setEmail(rs.getString("email"));
                user.setFullName(rs.getString("userFullname"));
                user.setPassword(rs.getString("userPassword"));
                user.setDateOfBirth(rs.getDate("dateOfBirth"));
                user.setIsAdmin(rs.getBoolean("isAdmin"));
                user.setSalt(rs.getString("salt"));
            }
        } catch (Exception e) {
            e.printStackTrace();
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    // 🔽 HÀM 1: ĐÃ SỬA TRANSACTION 🔽
    public boolean insertUser(userDTO user) {
        boolean check = false;
        String sql = "INSERT INTO tblUser(userId, userFullname, userName, userPassword, salt, email, dateOfBirth, isAdmin) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null; // Đổi tên con -> conn
        PreparedStatement ps = null;

        try {
            conn = DbUtils.getConnection();
            if (conn != null) {
                conn.setAutoCommit(false); // 1. Bắt đầu transaction

                String userID = "CM" + String.format("%06d", new Random().nextInt(100000000));

                if (user.getEmail() == null || !user.getEmail().matches("^.+@.+\\..+$")) { // Rút gọn regex
                    return false;
                }

                ps = conn.prepareStatement(sql);
                ps.setString(1, userID);
                ps.setString(2, user.getFullName());
                ps.setString(3, user.getUserName());
                ps.setString(4, user.getPassword());
                ps.setString(5, user.getSalt());
                ps.setString(6, user.getEmail());
                ps.setDate(7, user.getDateOfBirth());
                ps.setBoolean(8, user.isIsAdmin());

                check = ps.executeUpdate() > 0;

                if (check) {
                    conn.commit(); // 2. Lưu thay đổi
                } else {
                    conn.rollback(); // 2b. Hủy (nếu insert 0 dòng)
                }
            }
        } catch (Exception e) { // Sửa thành Exception
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback(); // 3. Hủy nếu có lỗi
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true); // 4. Trả về chế độ cũ
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
        return check;
    }

    public userDTO checkAccountExist(String userName) {
        return this.getUserByUsername(userName);
    }

    // 🔽 HÀM 2: ĐÃ SỬA TRANSACTION 🔽
    public boolean updatePasswordAndSalt(String userName, String newHashedPassword, String newSalt) {
        boolean check = false;
        String sql = "UPDATE tblUser SET userPassword = ?, salt = ? WHERE userName = ?";
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = DbUtils.getConnection();
            if (conn != null) {
                conn.setAutoCommit(false); // 1. Bắt đầu transaction

                pst = conn.prepareStatement(sql);
                pst.setString(1, newHashedPassword);
                pst.setString(2, newSalt);
                pst.setString(3, userName);

                check = pst.executeUpdate() > 0;

                if (check) {
                    conn.commit(); // 2. Lưu thay đổi
                } else {
                    conn.rollback(); // 2b. Hủy (nếu update 0 dòng)
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback(); // 3. Hủy nếu có lỗi
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true); // 4. Trả về chế độ cũ
                }
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return check;
    }

    // (Hàm này chỉ ĐỌC, không cần sửa)
    public userDTO getUserByEmail(String email) {
        userDTO user = null;
        String sql = "SELECT * FROM tblUser WHERE email = ?";
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, email);
            rs = pst.executeQuery();

            if (rs.next()) {
                user = new userDTO();
                user.setUserId(rs.getString("userId"));
                user.setUserName(rs.getString("userName"));
                user.setEmail(rs.getString("email"));
                user.setFullName(rs.getString("userFullname"));
                user.setPassword(rs.getString("userPassword"));
                user.setDateOfBirth(rs.getDate("dateOfBirth"));
                user.setIsAdmin(rs.getBoolean("isAdmin"));
                user.setSalt(rs.getString("salt"));
            }
        } catch (Exception e) {
            e.printStackTrace();
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    // 🔽 HÀM 3: ĐÃ SỬA TRANSACTION 🔽
    public boolean updateResetToken(String email, String token, java.sql.Timestamp expiryTime) {
        boolean check = false;
        String sql = "UPDATE tblUser SET resetToken = ?, tokenExpiry = ? WHERE email = ?";
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = DbUtils.getConnection();
            if (conn != null) {
                conn.setAutoCommit(false); // 1. Bắt đầu transaction

                pst = conn.prepareStatement(sql);
                pst.setString(1, token);
                pst.setTimestamp(2, expiryTime);
                pst.setString(3, email);
                check = pst.executeUpdate() > 0;

                if (check) {
                    conn.commit(); // 2. Lưu thay đổi
                } else {
                    conn.rollback(); // 2b. Hủy
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback(); // 3. Hủy nếu có lỗi
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true); // 4. Trả về chế độ cũ
                }
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return check;
    }

    // (Hàm này chỉ ĐỌC, không cần sửa)
    public userDTO getUserByToken(String token) {
        userDTO user = null;
        String sql = "SELECT * FROM tblUser WHERE resetToken = ? AND tokenExpiry > GETDATE()";
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            if (conn != null) {
                pst = conn.prepareStatement(sql);
                pst.setString(1, token);
                rs = pst.executeQuery();
                if (rs.next()) {
                    user = new userDTO();
                    user.setUserId(rs.getString("userId"));
                    user.setUserName(rs.getString("userName"));
                    user.setEmail(rs.getString("email"));
                    user.setFullName(rs.getString("userFullname"));
                    user.setPassword(rs.getString("userPassword"));
                    user.setDateOfBirth(rs.getDate("dateOfBirth"));
                    user.setIsAdmin(rs.getBoolean("isAdmin"));
                    user.setSalt(rs.getString("salt"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    // 🔽 HÀM 4: ĐÃ SỬA TRANSACTION 🔽
    public boolean clearResetToken(String username) {
        boolean check = false;
        String sql = "UPDATE tblUser SET resetToken = NULL, tokenExpiry = NULL WHERE userName = ?";
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = DbUtils.getConnection();
            if (conn != null) {
                conn.setAutoCommit(false); // 1. Bắt đầu transaction

                pst = conn.prepareStatement(sql);
                pst.setString(1, username);
                check = pst.executeUpdate() > 0;

                if (check) {
                    conn.commit(); // 2. Lưu thay đổi
                } else {
                    conn.rollback(); // 2b. Hủy
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback(); // 3. Hủy nếu có lỗi
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true); // 4. Trả về chế độ cũ
                }
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return check;
    }

    public static void main(String[] args) {
        userDAO dao = new userDAO();
        userDTO u = dao.getUserByUsername("admin");
        System.out.println(u);
    }
}
