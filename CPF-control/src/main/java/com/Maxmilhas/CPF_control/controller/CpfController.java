package com.Maxmilhas.CPF_control.controller;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Maxmilhas.CPF_control.domain.User;
import com.Maxmilhas.CPF_control.repositorio.Repositorio;
import com.Maxmilhas.CPF_control.service.CpfService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/cpf")
public class CpfController {
  private CpfService cpfService;
  private Repositorio repositorio;
  
  @PostMapping
  public String addCpf(@RequestBody User user) {
    try {
      String cpf = user.getCpf();
      if (!cpfService.isValidCpf(cpf)) {
        throw new InvalidCpfException(cpf + " é inválido.");
      }
      if (repositorio.findByCpf(cpf).isPresent()) {
        throw new ExistsCpfException(cpf + " já existe na lista.");
      }
      user.setCreatedAt(LocalDateTime.now());
      repositorio.save(user);
      return "cpf " + ":" + cpf ;
    } catch (InvalidCpfException | ExistsCpfException e) {
      return "Erro: " + e.getMessage();
    }
  }
  private static class InvalidCpfException extends RuntimeException {
    public InvalidCpfException(String message) {
      super(message);
    }
  }
  private static class ExistsCpfException extends RuntimeException {
    public ExistsCpfException(String message) {
      super(message);
    }
  }

  @GetMapping("/{cpf}")
  public ResponseEntity<String> handleCpf(@PathVariable String cpf) {
    Optional<User> user = repositorio.findByCpf(cpf);
    try{
      if (user.isPresent()) {
        User foundUser = user.get();
        String createdAtIso = foundUser.getCreatedAt().atZone(java.time.ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC).format(DateTimeFormatter.ISO_DATE_TIME);
        return ResponseEntity.ok("cpf: " + cpf + " createdAt: " + createdAtIso);
      }
      if (!cpfService.isValidCpf(cpf)) {
        throw new InvalidCpfException(cpf + " é inválido.");
      }
      throw new NotFoundCpfException(cpf + " não encontrado.");
    }catch (NotFoundCpfException |InvalidCpfException e) {
      return ResponseEntity.status(404).body("Erro: " + e.getMessage());
    }
  }

  public static class NotFoundCpfException extends RuntimeException {
    public NotFoundCpfException(String message) {
      super(message);
    }
  }
  @DeleteMapping("/{cpf}")
  public ResponseEntity<String> deleteCpf(@PathVariable String cpf) {
    Optional<User> user = repositorio.findByCpf(cpf);
    try {
      if (user.isPresent()) {
        repositorio.delete(user.get());
        return ResponseEntity.ok("CPF " + cpf + " removido com sucesso.");
      } else {
        throw new NotFoundCpfException(cpf + " não encontrado.");
      }
    } catch (NotFoundCpfException e) {
      return ResponseEntity.status(404).body("Erro: " + e.getMessage());
    }
  }
  @GetMapping
  public ResponseEntity<List<Object>> getAllCpfs() {
    List<User> users = new ArrayList<>();
    repositorio.findAll().forEach(users::add);
    List<Object> cpfs = users.stream()
        .map(user -> {
          return new Object() {
            public final String cpf = user.getCpf();
            public final String createdAt = user.getCreatedAt().atZone(java.time.ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC).format(DateTimeFormatter.ISO_DATE_TIME);
          };
        })
        .collect(Collectors.toList());
    return ResponseEntity.ok(cpfs);
  }

}

