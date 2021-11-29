package com.example.menu.item;

import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@EnableMapRepositories
@Transactional
public class ItemService {
    private final InMemoryItemRepository repository;

    public ItemService(InMemoryItemRepository repository) {
        this.repository = repository;
        this.repository.saveAll(defaultItems());
    }

    private static List<Item> defaultItems() {
        return List.of(
                new Item("Burger", 599L, "Tasty", "https://cdn.auth0.com/blog/whatabyte/burger-sm.png"),
                new Item("Pizza", 299L, "Cheesy", "https://cdn.auth0.com/blog/whatabyte/pizza-sm.png"),
                new Item("Tea", 199L, "Informative", "https://cdn.auth0.com/blog/whatabyte/tea-sm.png")
        );
    }

    public List<Item> findAll() {
        List<Item> list = new ArrayList<>();
        Iterable<Item> items = repository.findAll();
        items.forEach(list::add);
        return list;
    }

    public Optional<Item> find(Long id) {
        return repository.findById(id);
    }

    public Item create(Item item) {
        // To ensure the item ID remains unique,
        // use the current timestamp.
        Item copy = new Item(
                item.getName(),
                item.getPrice(),
                item.getDescription(),
                item.getImage()
        );
        return repository.save(copy);
    }

    public Optional<Item> update(Long id, Item newItem) {
        // Only update an item if it can be found first.
        return repository.findById(id)
                .map(oldItem -> {
                    Item updated = oldItem.updateWith(newItem);
                    return repository.save(updated);
                });
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
