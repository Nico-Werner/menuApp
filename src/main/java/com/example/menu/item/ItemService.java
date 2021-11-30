package com.example.menu.item;

import com.example.menu.category.Category;
import com.example.menu.category.CategoryRepository;
import com.example.menu.itemCategory.ItemCategory;
import com.example.menu.itemCategory.ItemCategoryRepository;
import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@EnableMapRepositories
@Transactional
public class ItemService {
    private final InMemoryItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final ItemCategoryRepository itemCategoryRepository;

    public ItemService(InMemoryItemRepository repository, CategoryRepository categoryRepository, ItemCategoryRepository itemCategoryRepository) {
        this.itemRepository = repository;
        this.categoryRepository = categoryRepository;
        this.itemCategoryRepository = itemCategoryRepository;
        this.itemRepository.saveAll(defaultItems());
        this.categoryRepository.saveAll(defaultCategories());
    }

    private static List<Item> defaultItems() {
        return List.of(
                new Item("Burger", 599L, "Tasty", "https://cdn.auth0.com/blog/whatabyte/burger-sm.png"),
                new Item("Pizza", 299L, "Cheesy", "https://cdn.auth0.com/blog/whatabyte/pizza-sm.png"),
                new Item("Tea", 199L, "Informative", "https://cdn.auth0.com/blog/whatabyte/tea-sm.png")
        );
    }

    private static List<Category> defaultCategories() {
        return List.of(
                new Category("Breakfast", "Breakfast Meal"),
                new Category("Lunch", "Lunch Meal"),
                new Category("Dinner", "Dinner Meal"),
                new Category("Dessert", "Desert")
        );
    }

    public List<Item> findAll() {
        List<Item> list = new ArrayList<>();
        Iterable<Item> items = itemRepository.findAll();
        items.forEach(list::add);
        return list;
    }

    public Optional<Item> find(Long id) {
        return itemRepository.findById(id);
    }

    public Item create(Item item, Category category) {
        // To ensure the item ID remains unique,
        // use the current timestamp.
        Item copy = new Item(
                item.getName(),
                item.getPrice(),
                item.getDescription(),
                item.getImage()
        );

        Optional<Category> categoryOptional = categoryRepository.findById(category.getId());
        if (categoryOptional.isPresent()) {
            itemCategoryRepository.save(new ItemCategory(copy, category));
        }
        return itemRepository.save(copy);
    }

    public Optional<Item> update(Long id, Item newItem) {
        // Only update an item if it can be found first.
        return itemRepository.findById(id)
                .map(oldItem -> {
                    Item updated = oldItem.updateWith(newItem);
                    return itemRepository.save(updated);
                });
    }

    public void delete(Long id) {
        itemRepository.deleteById(id);
    }
}
