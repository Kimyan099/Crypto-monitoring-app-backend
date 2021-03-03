package com.szemereadam.cma.model;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import static com.szemereadam.cma.model.ApplicationUserPermission.CLIENT_READ;

import java.util.Set;
import java.util.stream.Collectors;

public enum ApplicationUserRole {

    CLIENT(Sets.newHashSet(CLIENT_READ)),
    ADMIN(Sets.newHashSet(ApplicationUserPermission.ADMIN_READ, CLIENT_READ));

    private Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
