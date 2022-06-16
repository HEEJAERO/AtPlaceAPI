package capstone.atplace.repository;

import capstone.atplace.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUsername(String username);

    Member findMemberByToken(String token);
    List<Member> findByPassword(String password);

    List<Member> findByName(String name);

    Member findByUsernameAndPassword(String username, String passwd);


}

