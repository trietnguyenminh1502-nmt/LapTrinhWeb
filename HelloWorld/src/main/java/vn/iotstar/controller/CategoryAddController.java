package vn.iotstar.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.model.Category;
import vn.iotstar.service.CategoryService;
import vn.iotstar.service.impl.CategoryServiceImpl;



@WebServlet(urlPatterns = { "/admin/category/add" })
public class CategoryAddController extends HttpServlet {
    private static final long serialVersionUID = 1L; // Thêm serialVersionUID

    CategoryService cateService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/add-category.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Category category = new Category();
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
        servletFileUpload.setHeaderEncoding("UTF-8");

        try {
            // Cài đặt Encoding cho response và request (nếu cần)
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            req.setCharacterEncoding("UTF-8"); 

            List<FileItem> items = servletFileUpload.parseRequest(req);

            for (FileItem item : items) {
                // Kiểm tra xem item là trường dữ liệu thông thường hay file
                if (item.isFormField()) {
                    // Xử lý trường dữ liệu thông thường
                    if (item.getFieldName().equals("name")) {
                        // Lấy giá trị của trường 'name'
                        category.setName(item.getString("UTF-8")); 
                    }
                    // Thêm các trường dữ liệu khác nếu có
                } else {
                    // Xử lý File upload (trường 'icon')
                    if (item.getFieldName().equals("icon")) {
                        String originalFileName = item.getName();
                        
                        // Chỉ xử lý nếu có file được chọn
                        if (originalFileName != null && !originalFileName.isEmpty()) {
                            int index = originalFileName.lastIndexOf(".");
                            String ext = originalFileName.substring(index + 1);
                            
                            // Tạo tên file duy nhất dựa trên thời gian
                            String fileName = System.currentTimeMillis() + "." + ext;
                            
                            // Tạo đường dẫn file trên server
                            File file = new File(Constant.DIR + "/category/" + fileName);
                            
                            // Ghi file lên server
                            item.write(file); 
                            
                            // Lưu đường dẫn vào model
                            category.setIcon("category/" + fileName); 
                        }
                    }
                }
            }

            // Sau khi xử lý hết các items, thực hiện insert vào database
            cateService.insert(category);
            
            // Chuyển hướng về trang danh sách
            resp.sendRedirect(req.getContextPath() + "/admin/category/list");

        } catch (FileUploadException e) {
            e.printStackTrace();
            // Có thể thêm log hoặc thông báo lỗi cho người dùng
        } catch (Exception e) {
            e.printStackTrace();
            // Các ngoại lệ khác (ví dụ: lỗi DB, lỗi IO khi ghi file)
        }
    }
}