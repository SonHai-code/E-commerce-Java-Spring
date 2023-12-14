package com.sonhai.services;

import com.sonhai.exception.ProductException;
import com.sonhai.models.Product;
import com.sonhai.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    public Product createProduct(CreateProductRequest req);

    public String deleteProduct(Long productId) throws ProductException;

    public Product updateProduct(Long productId, Product product) throws ProductException;

    public Product findProductById(Long id) throws ProductException;

    public List<Product> findProductByCategory(String category);

    public Page<Product> getAllProducts(String category, List<String>colors,List<String>sizes, Integer minPrice, Integer maxPrice,
                                        Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize);


}
