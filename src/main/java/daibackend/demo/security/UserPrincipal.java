package daibackend.demo.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import daibackend.demo.model.Login;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Long id;

    //private String name;

    @JsonIgnore
    private final String email;

    @JsonIgnore
    private final String password;

    private final boolean isAccountNonExpired;
    private final boolean isAccountNonLocked;
    private final boolean isCredentialNonExpired;
    private final boolean isEnabled;

    private final Collection<? extends GrantedAuthority> grantedAuthorities;
   // private GrantedAuthority authorities;

    public UserPrincipal(Long id,/* String name,*/ String email, String password,
                         /*GrantedAuthority authorities*/ boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isCredentialNonExpired, boolean isEnabled, Collection<? extends GrantedAuthority> grantedAuthorities) {
        this.id = id;
        //this.name = name;
        this.email = email;
        this.password = password;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialNonExpired = isCredentialNonExpired;
        this.isEnabled = isEnabled;
        this.grantedAuthorities = grantedAuthorities;
        //this.authorities = authorities;
    }

   public static UserPrincipal create(long id,String email, String password, String roles) {
       Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
       grantedAuthorities.add(new SimpleGrantedAuthority(roles));
       List <GrantedAuthority> authorities = grantedAuthorities.stream()
               .map(role -> new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toList());

       return new UserPrincipal(id,/* user.getName(),*/ email, password, true, true, true, true, authorities);
   }

    public Long getId() {
        return id;
    }

    //public String getName() {
     //   return name;
    //}

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return  grantedAuthorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return null;
    }
}