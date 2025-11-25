package repository; 
import org.springframework.data.jpa.repository.JpaRepository;

import entity.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Tìm category theo ID người tạo (Dùng cho Manager)
    List<Category> findByUserId(Object object);
}