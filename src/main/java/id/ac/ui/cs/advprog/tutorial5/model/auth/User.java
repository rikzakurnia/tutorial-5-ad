package id.ac.ui.cs.advprog.tutorial5.model.auth;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import id.ac.ui.cs.advprog.tutorial5.model.order.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Integer id;
    private String firstname;
    private String lastname;

    private String password;

    @Column(unique=true)
    private String email;

    private String role;

    private boolean active;

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(role.equals("ADMIN")) {
            return ApplicationUserRole.ADMIN.getGrantedAuthority();
        } else {
            return ApplicationUserRole.USER.getGrantedAuthority();
        }
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.active;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }
}
