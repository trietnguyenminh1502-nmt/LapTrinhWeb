package filter;

import java.io.IOException;
import java.util.Iterator;

// LƯU Ý: Kiểm tra lại 2 import này. Nếu bạn có class User/Role riêng, hãy import class của bạn thay vì org.apache.catalina
import org.apache.catalina.Role;
import org.apache.catalina.User;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AuthFilter implements Filter {

	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();

        // 1. Cho phép truy cập trang login và tài nguyên tĩnh
        // Thêm kiểm tra tránh lỗi null pointer nếu URI ngắn
        if (uri.contains("/login") || uri.startsWith("/css/") || uri.startsWith("/js/")) {
            chain.doFilter(request, response);
            return;
        }

        // 2. Kiểm tra đăng nhập
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            res.sendRedirect(req.getContextPath() + "/login"); // Nên thêm contextPath cho chắc chắn
            return;
        }

        // 3. Phân tích quyền (Role) của user
        // Tạo các biến cờ để đánh dấu xem user có quyền gì
        boolean isAdmin = false;
        boolean isManager = false;
        boolean isUserRole = false;

        Iterator<Role> roleIterator = user.getRoles();
        
        // Duyệt qua tất cả các role mà user đang có
        while (roleIterator != null && roleIterator.hasNext()) {
            Role r = roleIterator.next();
            
           
             if (r.getName().equalsIgnoreCase("ADMIN")) isAdmin = true; // Giả sử Admin tương đương ID 3
             if (r.getName().equalsIgnoreCase("MANAGER")) isManager = true;
             if (r.getName().equalsIgnoreCase("USER")) isUserRole = true;
        }

        // 4. Kiểm tra URL và Quyền tương ứng
        
        // Nếu vào trang admin mà KHÔNG phải là Admin
        if (uri.contains("/admin/") && !isAdmin) {
            res.sendError(403, "BAN KHONG CO QUYEN ADMIN (Role ID: 3)");
            return;
        }

        // Nếu vào trang manager mà KHÔNG phải là Manager
        if (uri.contains("/manager/") && !isManager) {
            res.sendError(403, "BAN KHONG CO QUYEN MANAGER (Role ID: 2)");
            return;
        }
	}
}

