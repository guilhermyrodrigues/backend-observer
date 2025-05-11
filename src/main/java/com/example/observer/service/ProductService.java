package com.example.observer.service;

import com.example.observer.model.ProductModel;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    private Map<Long, ProductModel> products = new HashMap<>();
    private List<String> observers = new ArrayList<>();
    private RestTemplate restTemplate = new RestTemplate();

    public void registerObserver(String callbackUrl) {
        if (!observers.contains(callbackUrl)) {
            observers.add(callbackUrl);
        }
    }

    public void unregisterObserver(String callbackUrl) {
        observers.remove(callbackUrl);
    }

    public ProductModel addProduct(ProductModel product) {
        products.put(product.getId(), product);
        return product;
    }

    public void updatePrice(Long id, Double newPrice) {
        if (products.containsKey(id)) {
            ProductModel product = products.get(id);
            product.setPrice(newPrice);
            products.put(id, product); // Update the product in the map
            notifyObservers(product);
        }
    }

    public void notifyObservers(ProductModel product) {
        for (String url : observers) {
            try {
                restTemplate.postForObject(url, product, Void.class);
            } catch (Exception e) {
                System.err.println("Erro ao notificar o observador " + url + ": " + e.getMessage());
            }
        }
    }

    public List<ProductModel> getProducts() {
        return new ArrayList<>(products.values());
    }

}
