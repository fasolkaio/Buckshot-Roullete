package sk.tuke.gamestudio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}