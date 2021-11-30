package com.example.menu.itemCategory;

import com.example.menu.category.Category;
import com.example.menu.item.Item;

import javax.persistence.*;

@Entity
@Table(name = "ITEM_CATEGORY", uniqueConstraints = @UniqueConstraint(columnNames = {"ITEM_ID", "CATEGORY_ID"}))
public class ItemCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int id;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    public ItemCategory() {}

    public ItemCategory(Item item, Category category) {
        this.item = item;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public Category getCategory() {
        return category;
    }
    
    public void setItem(Item item) {
        this.item = item;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
