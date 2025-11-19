package vn.iotstar.service;

import java.util.List;
import vn.iotstar.model.Category;

public interface CategoryService {
	void insert(Category category);

	void edit(Category category);

	static void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	Category get(int id);

	Category get(String name);

	List<Category> getAll();

	List<Category> search(String keyword);

	void edit1(Category newCategory);
}