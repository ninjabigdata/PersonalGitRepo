package com.shopping.cart.app.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "cart_product_mapping")
@Data
//@ToString(exclude = "cart")
//@EqualsAndHashCode(exclude = "cart")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartProduct implements Comparable<CartProduct> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "cart_id", nullable = false)
    private Long cartId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

//    @ManyToOne
//    @JoinColumn
//    private Cart cart;

    @Override
    public int compareTo(CartProduct o) {
        return (int) (this.getId() - o.getId());
    }
}
