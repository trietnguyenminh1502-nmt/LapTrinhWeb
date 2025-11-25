package entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor; 
import lombok.Data;
import lombok.NoArgsConstructor;  
import java.util.List;

@Entity
@Table(name = "users")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    private String password;
    
    @Column(name = "role_id")
    private int roleId;

    @OneToMany(mappedBy = "user")
    private List<Category> categories;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRoleId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Object getId() {
		// TODO Auto-generated method stub
		return null;
	}

   
}