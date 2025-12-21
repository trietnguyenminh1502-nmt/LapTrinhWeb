package vn.iotstar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iotstar.entity.Product;
import vn.iotstar.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    // Lấy danh sách tất cả sản phẩm (Dùng cho trang index bài 3) 
    public List<Product> listAll() {
        return repo.findAll();
    }

    // Lưu hoặc cập nhật sản phẩm 
    public void save(Product product) {
        repo.save(product);
    }

    // Lấy thông tin chi tiết một sản phẩm theo ID 
    public Product get(Long id) {
        return repo.findById(id).orElse(null);
    }

    // Xóa sản phẩm theo ID 
    public void delete(Long id) {
        repo.deleteById(id);
    }
}