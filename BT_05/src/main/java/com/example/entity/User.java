package com.example.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Locale.Category;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Users")
@Data // Lombok
public class User implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 133156171985679698L;
	@Id
    private String username;
    private String password;
    private String phone;
    private String fullname;
    private String email;
    private boolean admin;
    private boolean active;
    private String images;

    @OneToMany(mappedBy = "user")
    private List<Category> categories;
}