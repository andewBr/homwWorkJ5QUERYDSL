package by.itacademy.hibernate.entity;

import javax.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


//@FetchProfile(name = "withCompany", fetchOverrides = {
//        @FetchProfile.FetchOverride(entity = User.class, association = "company", mode = FetchMode.JOIN)
//})
//@NamedEntityGraph(
//        name = "WithCompanyAndChat",
//        attributeNodes = {
//                @NamedAttributeNode("company"),
//                @NamedAttributeNode(value = "userChats", subgraph = "chats")
//        },
//        subgraphs = {
//                @NamedSubgraph(name = "chats", attributeNodes = @NamedAttributeNode("chat"))
//        }
//)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "username")
@ToString(exclude = {"company", "profile", "userChats", "payments", "apartments"})
@Builder
@Entity
@Table(name = "users", schema = "public")
public class User implements Comparable<User>, BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @AttributeOverride(name = "birthDate", column = @Column(name = "birth_date"))
    private PersonalInfo personalInfo;

    @Column(unique = true)
    private String username;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appartment_id")
    private Apartment apartments;

//    @OneToOne(
//            mappedBy = "user",
//            cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY
//    )
//    private Profile profile;

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<UserChat> userChats = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    private List<Payment> payments = new ArrayList<>();

    @Override
    public int compareTo(User o) {
        return username.compareTo(o.username);
    }

    public String fullName() {
        return getPersonalInfo().getFirstname() + " " + getPersonalInfo().getLastname();
    }
}
