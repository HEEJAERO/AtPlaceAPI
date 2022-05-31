package capstone.atplace.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Meeting {

    @Id @GeneratedValue
    @Column(name = "meeting_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    private String name;

    private String Address;

    private LocalDateTime dateTime;


}
