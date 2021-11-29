package com.example.menu.item;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.persistence.*;

@Entity
@Table(name="ITEMS")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @NotNull(message = "name is required")
    @Pattern(regexp="^[a-zA-Z ]+$", message = "name must be a string")
    @Column
    private String name;

    @NotNull(message = "price is required")
    @Positive(message = "price must be positive")
    @Column
    private Long price;

    @NotNull(message = "description is required")
    @Pattern(regexp="^[a-zA-Z ]+$", message = "description must be a string")
    @Column
    private String description;

    @NotNull(message = "image is required")
    @URL(message = "image must be a URL")
    @Column
    private String image;

    public Item(String name, Long price, String description,String image) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    public Item(){}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Item updateWith(Item item) {
        return new Item(item.name, item.price, item.description, item.image);
    }

}
