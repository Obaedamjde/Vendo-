package com.aabu.finalproject.config;

import com.aabu.finalproject.model.entity.UserEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class CustomUserDetails implements UserDetails, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // لا نخزن الـ Entity داخل الـ Session
    private final transient UserEntity userEntity;

    // نخزن بيانات بسيطة قابلة للتسلسل
    @Getter
    private final Long id;

    private final String phone;
    private final String passwordHash;
    private final String roleName;
    private final boolean blocked;
    private final boolean verified;

    public CustomUserDetails(UserEntity userEntity) {
        this.userEntity = userEntity;

        this.id = userEntity.getId();
        this.phone = userEntity.getPhone();
        this.passwordHash = userEntity.getPasswordHash();
        this.roleName = userEntity.getRole().name();
        this.blocked = userEntity.isBlocked_user();
        this.verified = userEntity.isVerified();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + roleName));
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return phone;
    }

    /**
     * قد ترجع null بعد استرجاع الـ session من DB (لأن userEntity transient)
     * لا تعتمد عليها إلا إذا كنت بنفس request بعد تسجيل الدخول مباشرة.
     */
    public UserEntity getUserEntity() {
        return userEntity;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !blocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // نفس منطقك الحالي (مع أنه غالبًا المفروض verified == true)
        return !verified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomUserDetails that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
