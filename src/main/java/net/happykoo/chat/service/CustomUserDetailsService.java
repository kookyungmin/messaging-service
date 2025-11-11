package net.happykoo.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.happykoo.chat.dto.MemberDto;
import net.happykoo.chat.entity.Member;
import net.happykoo.chat.repository.MemberRepository;
import net.happykoo.chat.vos.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("계정이 존재하지 않습니다."));

        return new CustomUserDetails(member, null);
    }

    public MemberDto saveMember(MemberDto dto) {
        Member member = dto.toEntity();

        member.updatePassword(dto.password(), dto.confirmPassword(), passwordEncoder);
        memberRepository.save(member);

        return MemberDto.from(member);
    }
}
