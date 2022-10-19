package com.shopping.cart.app.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(exclude = {"book", "apparel"})
@ToString(exclude = {"book", "apparel"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQueries(value = {
        @NamedQuery(name = "Product.findAllBooks", query = "FROM Product p INNER JOIN Book b ON p.productId = b.product"),
        @NamedQuery(name = "Product.findAllApparels", query = "FROM Product p INNER JOIN Apparel a ON p.productId = a.product")
})
public class Product implements Comparable<Product> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "prod_name", nullable = false, unique = true)
    private String productName;

    @Column(nullable = false)
    private Double price;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private Book book;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private Apparel apparel;

    @Override
    public int compareTo(Product o) {
        return (int) (this.getProductId() - o.getProductId());
    }
}
