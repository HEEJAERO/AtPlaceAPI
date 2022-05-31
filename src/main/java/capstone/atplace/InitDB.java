package capstone.atplace;

import capstone.atplace.domain.Meeting;
import capstone.atplace.domain.Member;
import capstone.atplace.domain.Place;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class InitDB {


    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
        //initService.dbInit2();
    }
    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        public void dbInit1(){

            Member member = new Member("member1", "123123", "username1", "01020202020", LocalDateTime.now());
            em.persist(member);

            Place place1 = new Place();

            place1.setAddress("산본로");
            place1.setCategory("공부");
            place1.setMember(member);

            em.persist(place1);
            Place place2 = new Place();
            place2.setAddress("군포시");
            place2.setCategory("식사");
            place2.setMember(member);

            em.persist(place2);

            member.addPlace(place1);
            member.addPlace(place2);
            em.persist(member);
            em.flush();
            em.clear();
        }
        private void dbInit2(){


            Member member = new Member("member2", "123123", "username2", "01020202020", LocalDateTime.now());
            em.persist(member);

            Place place1 = new Place();

            place1.setAddress("산본로");
            place1.setCategory("공부");
            place1.setMember(member);

            em.persist(place1);
            Place place2 = new Place();
            place2.setAddress("군포시");
            place2.setCategory("식사");
            place2.setMember(member);

            em.persist(place2);

            member.addPlace(place1);
            member.addPlace(place2);
            em.persist(member);
            em.flush();
            em.clear();
        }

    }
}
