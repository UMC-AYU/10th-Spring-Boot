package org.example.swaggerpr.store.repository;

import org.example.swaggerpr.store.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
}
