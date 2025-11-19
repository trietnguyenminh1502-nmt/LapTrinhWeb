package vn.iotstar.controller;

import java.io.File;
import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.model.Category;
import vn.iotstar.service.impl.CategoryServiceImpl;

@WebServlet(urlPatterns = { "/admin/category/edit" })


public class CategoryEditController<cateService, CategoryService> extends HttpServlet {
	private static final long serialVersionUID = -32091097762276225L;
	@SuppressWarnings("unchecked")
	CategoryService cateService = (CategoryService) new CategoryServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws
	ServletException, IOException {
	String id = req.getParameter("id");
	vn.iotstar.model.Category category = ((CategoryServiceImpl) cateService).get(Integer.parseInt(id));
	req.setAttribute("category", category);
	RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/editcategory.jsp");
	dispatcher.forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
	Category category = new Category();
	DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
	ServletFileUpload servletFileUpload = new
	ServletFileUpload(diskFileItemFactory);
	servletFileUpload.setHeaderEncoding("UTF-8");
	try {
	resp.setContentType("text/html");
	resp.setCharacterEncoding("UTF-8");
	req.setCharacterEncoding("UTF-8");
	List<FileItem> items = servletFileUpload.parseRequest(req);
	for (FileItem item : items) {
		if (item.getFieldName().equals("id")) {
			category.setId(Integer.parseInt(item.getString()));
			} else if (item.getFieldName().equals("name")) {
			category.setName(item.getString("UTF-8"));
			} else if (item.getFieldName().equals("icon")) {
			if (item.getSize() > 0) {// neu co file d
			String originalFileName = item.getName();
			int index = originalFileName.lastIndexOf(".");
			String ext = originalFileName.substring(index + 1);
			String fileName = System.currentTimeMillis() + "." + ext;
			File file = new File(Constant.DIR + "/category/" + fileName);
			item.write(file);
			category.setIcon("category/"+fileName);
			} else {
			category.setIcon(null);}}}
			cateService.edit(category);
			resp.sendRedirect(req.getContextPath() + "/admin/category/list");
			} catch (FileUploadException e) {
			e.printStackTrace();
			} catch (Exception e) {e.printStackTrace();
			}
}}


