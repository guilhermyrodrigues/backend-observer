package com.example.observer.controller;

import com.example.observer.model.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.observer.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/observers")
    public ResponseEntity<Void> registerObserver(@RequestBody String callbackUrl) { // Alterado callbackurl -> callbackUrl
        productService.registerObserver(callbackUrl);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/observers")
    public ResponseEntity<Void> unregisterObserver(@RequestBody String callbackUrl) { // Alterado callbackurl -> callbackUrl
        productService.unregisterObserver(callbackUrl);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/products")
    public ResponseEntity<ProductModel> addProduct(@RequestBody ProductModel product) { // Alterado ProductModel -> Product
        ProductModel addedProduct = productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedProduct); // Adicionado body
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Void> updatePrice(@PathVariable Long id, @RequestBody ProductModel product) { // Alterado ProductModel -> Product
        productService.updatePrice(id, product.getPrice());
        return ResponseEntity.ok().build(); // Alterado HttpStatus.OK -> ResponseEntity.ok()
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getProducts() { // Alterado ProductModel -> Product
        List<ProductModel> products = productService.getProducts();
        return ResponseEntity.ok(products);
    }
}