package br.com.nexus.goat.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nexus.goat.models.Category;
import br.com.nexus.goat.models.Feature;
import br.com.nexus.goat.models.Product;
import br.com.nexus.goat.models.dto.ProductDTO;
import br.com.nexus.goat.repositories.CategoryRepository;
import br.com.nexus.goat.repositories.FeatureRepository;
import br.com.nexus.goat.repositories.ProductRepository;
import br.com.nexus.goat.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductService service;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FeatureRepository featureRepository;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProductDTO obj) {
        Product product = this.service.product(obj);
        List<Category> categories = this.service.categories(obj);
        product = this.repository.save(product);
        Feature feature = this.service.feature(obj);

        Feature verifyFeature = this.featureRepository.findByMarkAndModelAndColorAndComposition(feature.getMark(),
                feature.getModel(), feature.getColor(), feature.getComposition());

        if (verifyFeature == null)
            feature = this.featureRepository.save(feature);
        else
            feature = verifyFeature;

        product.setFeatures(feature);

        for (Category category : categories) {
            Category verifyCategory = this.categoryRepository.findByName(category.getName());
            if (verifyCategory == null)
                category = this.categoryRepository.save(category);
            else
                category = verifyCategory;

            product.getCategories().add(category);
        }

        product = this.repository.save(product);

        return ResponseEntity.ok().body(product);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        List<Product> products = this.repository.findAll();
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Product product = this.repository.findById(id).orElse(null);
        return ResponseEntity.ok().body(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        this.repository.deleteById(id);
        return ResponseEntity.ok().body("Produto deletado!");
    }
}
