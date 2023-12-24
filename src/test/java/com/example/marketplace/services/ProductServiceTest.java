package com.example.marketplace.services;

import com.example.marketplace.models.Product;
import com.example.marketplace.models.User;
import com.example.marketplace.repositories.ProductRepository;
import com.example.marketplace.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void testListProducts() {
        // Arrange
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<Product> result = productService.listProducts(null);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testSaveProduct() throws IOException {
        // Arrange
        Principal principal = createPrincipal("test@example.com");
        Product product = new Product();
        MockMultipartFile file1 = new MockMultipartFile("file1", "test1.jpg", "image/jpeg", "content1".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("file2", "test2.jpg", "image/jpeg", "content2".getBytes());
        MockMultipartFile file3 = new MockMultipartFile("file3", "test3.jpg", "image/jpeg", "content3".getBytes());

        // Act
        productService.saveProduct(principal, product, file1, file2, file3);

        // Assert
        verify(productRepository, times(1)).save(product);
        assertEquals("test@example.com", product.getUser().getEmail());
        assertNotNull(product.getPreviewImageId());
        assertNotNull(product.getImages());
        assertEquals(3, product.getImages().size());
    }

    @Test
    void testGetUserByPrincipal() {
        // Arrange
        Principal principal = createPrincipal("test@example.com");
        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findByEmail(any())).thenReturn(user);

        // Act
        User result = productService.getUserByPrincipal(principal);

        // Assert
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void testDeleteProduct() {
        // Arrange
        Long productId = 1L;

        // Act
        productService.deleteProduct(productId);

        // Assert
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void testGetProductById() {
        // Arrange
        Long productId = 1L;
        Product product = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        Product result = productService.getProductById(productId);

        // Assert
        assertNotNull(result);
        assertEquals(product, result);
    }

    private Principal createPrincipal(String email) {
        Authentication auth = new UsernamePasswordAuthenticationToken(email, "password");
        SecurityContextHolder.getContext().setAuthentication(auth);
        return auth;
    }
}
