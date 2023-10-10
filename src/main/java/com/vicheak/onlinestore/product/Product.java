package com.vicheak.onlinestore.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "pro_code", length = 30, nullable = false, unique = true)
    private String code;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String image;
    @ManyToOne
    @JoinColumn(name = "cat_id")
    private Category category;

}
