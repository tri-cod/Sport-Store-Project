/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filter;

import DTO.userDTO;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        userDTO user = (session != null) ? (userDTO) session.getAttribute("user") : null;

        // Dùng 'isIsAdmin()' từ file userDTO của bạn
        if (user != null && user.isIsAdmin()) { 
            // Là Admin -> Cho phép đi tiếp
            chain.doFilter(request, response);
        } else {
            // Không phải Admin (là Guest hoặc Customer) -> Đá về trang chủ
            res.sendRedirect("homeController"); 
        }
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    
    
    @Override
    public void destroy() {}
}
