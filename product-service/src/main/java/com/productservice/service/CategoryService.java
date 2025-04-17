package com.productservice.service;

import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    boolean existsById(Long id);
}
