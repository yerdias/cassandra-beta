package com.example.cassandra_beta.repository;

import com.example.cassandra_beta.model.Example;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.UUID;

public interface ExampleRepository extends CassandraRepository<Example, UUID> {
  @AllowFiltering
  List<Example> findByPublished(boolean published);
  
  List<Example> findByTitleContaining(String title);
}
