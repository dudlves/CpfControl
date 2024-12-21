package com.Maxmilhas.CPF_control.repositorio;

import java.util.Optional;

import com.Maxmilhas.CPF_control.domain.User; 

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Repositorio extends CrudRepository<User, Integer> {
  void save(String cpf);
  Optional<User> findByCpf(String cpf);
  
}
