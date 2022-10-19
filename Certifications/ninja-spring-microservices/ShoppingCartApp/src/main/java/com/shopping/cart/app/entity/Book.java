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
public class Book implements Comparable<Book> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String publications;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Override
    public int compareTo(Book o) {
        return (int) (this.getId() - o.getId());
    }
}
