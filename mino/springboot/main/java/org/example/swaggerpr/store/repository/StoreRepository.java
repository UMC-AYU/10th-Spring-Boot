package org.example.swaggerpr.store.repository;

import org.example.swaggerpr.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
