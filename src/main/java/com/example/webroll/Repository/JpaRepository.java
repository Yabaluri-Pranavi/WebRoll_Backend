package com.example.webroll.Repository;

/**
 * Compatibility wrapper to prevent accidental shadowing of Spring Data's JpaRepository.
 */
public interface JpaRepository<T, ID> extends org.springframework.data.jpa.repository.JpaRepository<T, ID> {
}
