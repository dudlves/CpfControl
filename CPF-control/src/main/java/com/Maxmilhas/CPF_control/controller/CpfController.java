package com.Maxmilhas.CPF_control.controller;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
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

  @PostMapping("/{cpf}")
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
}

