package com.example.menu.category;


import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@EnableMapRepositories
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category newCategory(Category category) {
        Category aux = new Category(category.getCategoryName(), category.getCategoryDescription());
        return categoryRepository.save(aux);
    }

    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        Iterable<Category> iterable = categoryRepository.findAll();
        iterable.forEach(categories::add);
        return categories;
    }

    public Optional<Category> findById(int id) {
        return categoryRepository.findById(id);
    }

    public void deleteById(int id) {
        categoryRepository.deleteById(id);
    }

    public Optional<Category> update(int id, Category category) {
        return categoryRepository.findById(id).map(c -> {
            c.setCategoryName(category.getCategoryName());
            c.setCategoryDescription(category.getCategoryDescription());
            return categoryRepository.save(c);
        });
    }
}
