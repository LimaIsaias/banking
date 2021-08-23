package com.example.accessingdatarest;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ContaRepository extends PagingAndSortingRepository<Conta, Long> {

}