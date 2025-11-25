package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "categories")
@Data
public class Category {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    
    
    @JoinColumn(name = "user_id")
    private User user; // Ai tạo category này?


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setUser(User user2) {
		// TODO Auto-generated method stub
		
	}
}
