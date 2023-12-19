package com.sonhai.controller;

import com.sonhai.exception.ProductException;
import com.sonhai.models.Product;
import com.sonhai.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> findProductsByCategoryHandler(@RequestParam String category,
                                                                       @RequestParam List<String> color,
                                                                       @RequestParam List<String> size,
                                                                       @RequestParam Integer minPrice,
                                                                       @RequestParam Integer maxPrice,
                                                                       @RequestParam Integer minDiscount,
                                                                       @RequestParam String sort,
                                                                       @RequestParam String stock,
                                                                       @RequestParam Integer pageNumber,
                                                                       @RequestParam Integer pageSize){
        Page<Product> res = productService.getAllProducts(category, color, size,
                                                          minPrice, maxPrice, minDiscount,
                                                          sort, stock, pageNumber, pageSize);

        System.out.println("Filtered products completed!");

        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    };

    @GetMapping("/products/id/{productId}")
    public ResponseEntity<Product> getProductByIdHandler(@PathVariable Long productId) throws ProductException {
        Product product = productService.findProductById(productId);

        return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProductsHandler(@RequestParam String query){
        List<Product> products = productService.searchProduct(query);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }


}
