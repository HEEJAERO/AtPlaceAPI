package capstone.atplace.service;

import capstone.atplace.domain.Meeting;
import capstone.atplace.domain.Member;
import capstone.atplace.domain.Place;
import capstone.atplace.repository.MemberRepository;
import capstone.atplace.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final PlaceRepository placeRepository;
    // 유저 닉네임으로 조회
    public Long findByUsername(String username){
        Member byUsername = memberRepository.findByUsername(username);
        return byUsername.getId();
    }
    //
    public Optional<Member> findMemberById(Long id) {
        Optional<Member> byId = memberRepository.findById(id);
        return byId;
    }
    //회원가입
    public Long join(Member member) {
        duplicateName(member);
        duplicateUsername(member);
        return memberRepository.save(member).getId();
    }
    // 로그인
    public String login(String username, String password) {
        Member findMember = memberRepository.findByUsernameAndPassword(username, password);
        if (findMember == null) {
            throw new IllegalStateException("존재하지 않는 회원입니다. 회원가입부터 해주세요");
        }
        return findMember.getToken();
    }
    // 즐겨찾기
    public List<Place> getPlaces(Long memberId){
        Optional<Member> byId = memberRepository.findById(memberId);
        log.info("{}",memberId);

        List<Place> placesByMember = placeRepository.findPlacesByMember(byId.get());
        /*if(findMember.equals(null)) {
            throw new IllegalStateException(" 존재하지 않는 회원입니다");
        }*/
        return placesByMember;
    }
    public Member getMemberByToken(String token){
        return memberRepository.findMemberByToken(token);
    }
    // 모임 예약
    public List<Meeting> getMeetings(Member member) {
        Optional<Member> findMember = memberRepository.findById(member.getId());
        if(findMember.isEmpty()) {
            throw new IllegalStateException(" 존재하지 않는 회원입니다");
        }
        return findMember.get().getMeetings();
    }
    //회원가입 시 중복체크 로직
    public void duplicateName(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 가입한 회원입니다");
        }

    }
    public void duplicateUsername(Member member) {
        Member findMember = memberRepository.findByUsername(member.getUsername());
        if (findMember!=null) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
    }

}
