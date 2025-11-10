package net.happykoo.chat.vos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.happykoo.chat.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class CustomOAuth2User implements OAuth2User {
    @Getter
    private Member member;
    private Map<String, Object> attributes;

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> member.getRole());
    }

    @Override
    public String getName() {
        return member.getEmail();
    }
}
