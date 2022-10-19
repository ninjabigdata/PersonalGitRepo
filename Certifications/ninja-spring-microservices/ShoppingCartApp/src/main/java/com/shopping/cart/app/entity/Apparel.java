package com.shopping.cart.app.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Apparel implements Comparable<Apparel> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String design;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Override
    public int compareTo(Apparel o) {
        return (int) (this.getId() - o.getId());
    }
}
