package com.sonhai.controller;

import com.sonhai.exception.ProductException;
import com.sonhai.models.Product;
import com.sonhai.request.CreateProductRequest;
import com.sonhai.response.ApiResponse;
import com.sonhai.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {
    @Autowired
    private ProductService productService;

    /* Create new product */
    @PostMapping("/")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest req){
        Product product = productService.createProduct(req);

        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) throws ProductException {
        productService.deleteProduct(productId);

        ApiResponse res = new ApiResponse();
        res.setMessage("The product has been deleted successfully!");
        res.setStatus(true);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> findAllProducts() {
        List<Product> products = productService.findAllProduct();
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @PutMapping("/{productId}/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product res, @PathVariable Long productId) throws ProductException {
        Product product = productService.updateProduct(productId, res);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PostMapping("/creates")
    public ResponseEntity<ApiResponse> createMultipleProducts(@RequestBody CreateProductRequest[] req) {
        for (CreateProductRequest productRequest : req ) {
            productService.createProduct(productRequest);
        }
        ApiResponse res = new ApiResponse();
        res.setMessage("Products are created successfully!");
        res.setStatus(true);

        return new ResponseEntity<>(res,HttpStatus.CREATED);
    }

}
