package com.fastcampus.catalog_service.cassandra.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.fastcampus.catalog_service.cassandra.entity.Product;

@Repository
public interface ProductRepository extends CassandraRepository<Product, Long> {

}
