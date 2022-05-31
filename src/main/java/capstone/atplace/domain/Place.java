package capstone.atplace.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Place {

    @Id
    @GeneratedValue
    @Column(name = "place_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    private String address;

    private String category;

}
