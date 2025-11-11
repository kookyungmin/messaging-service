package net.happykoo.chat.service;

import lombok.RequiredArgsConstructor;
import net.happykoo.chat.constant.GenderType;
import net.happykoo.chat.constant.UserRole;
import net.happykoo.chat.entity.Member;
import net.happykoo.chat.repository.MemberRepository;
import net.happykoo.chat.vos.CustomOAuth2User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class Custom0Auth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributeMap = oAuth2User.getAttribute("kakao_account");
        String email = (String) attributeMap.get("email");
        Member member = memberRepository.findByEmail(email)
                .orElseGet(() -> registerMember(attributeMap));

        memberRepository.save(member);

        return new CustomOAuth2User(member, oAuth2User.getAttributes());
    }

    private Member registerMember(Map<String, Object> attributeMap) {
        Member member = Member.builder()
                .email((String) attributeMap.get("email"))
                .name((String) attributeMap.get("name"))
                .nickName((String) ((Map) attributeMap.get("profile")).get("nickname"))
                .phoneNumber((String) attributeMap.get("phone_number"))
                .gender(GenderType.valueOf(((String) attributeMap.get("gender")).toUpperCase()))
                .birthDate(getBirthDate(attributeMap))
                .role(UserRole.USER.getCode())
                .build();

        return member;
    }

    private LocalDate getBirthDate(Map<String, Object> attributeMap) {
        String birthYear = (String) attributeMap.get("birthyear");
        String birthDay = (String) attributeMap.get("birthday");
        return LocalDate.parse(birthYear + birthDay, DateTimeFormatter.BASIC_ISO_DATE);
    }
}
