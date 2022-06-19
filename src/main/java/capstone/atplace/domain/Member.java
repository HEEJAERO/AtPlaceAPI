package capstone.atplace.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    //로그인 아이디
    //@Column(unique = true)
    private String username;

    private String password;

    private LocalDateTime createDate;

    //사용자 이름
    //@Column(unique = true)
    private String name;

    private String phoneNumber;

    private String token;
    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Meeting> meetings = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Place> places = new ArrayList<>();


    public Member(String username,String password, String name){
        this.username = username;
        this.password = password;
        this.name = name;
        this.token = UUID.randomUUID().toString();
    }
    public Member(String name, String password, String username, String phoneNumber, LocalDateTime now) {

        this.name = name;
        this.password = password;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.createDate = now;
        this.token = UUID.randomUUID().toString();   // 이후 접근할때 토큰으로 사용
    }

    public void addPlace(Place place) {
        places.add(place);
    }
    public void deletePlace(Place place) {
        places.remove(place);
    }
    public void addMeeting(Meeting meeting) {
        meetings.add(meeting);
    }
    public void deleteMeeting(Meeting meeting){
        meetings.remove(meeting);
    }
}
