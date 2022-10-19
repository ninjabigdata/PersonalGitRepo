package com.shopping.cart.app.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@ToString(exclude = "cart")
@EqualsAndHashCode(exclude = "cart")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Comparable<User> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "email_id", nullable = false)
    private String emailId;

    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;

    @Override
    public int compareTo(User o) {
        return (int) (this.getId() - o.getId());
    }
}
