package it.amrito.pastryshopbe.model;


import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Backoffice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Backoffice implements Serializable,UserDetails{

    @Id
    private String nickname;

    private String email;

    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Backoffice that = (Backoffice) o;
        return nickname.equals(that.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of( new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
