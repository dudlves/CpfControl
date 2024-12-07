package com.Maxmilhas.CPF_control.controller;

import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.Maxmilhas.CPF_control.domain.User;
import com.Maxmilhas.CPF_control.repositorio.Repositorio;
import com.Maxmilhas.CPF_control.service.CpfService;
import lombok.AllArgsConstructor;

@RestController
// @RequestMapping("/cpf-Control")
@AllArgsConstructor
public class CpfController {

  private CpfService cpfService;
  private Repositorio repositorio;

  @PostMapping("/cpf")
  public String addCpf(@RequestBody User user) {
    try {
      String cpf = user.getCpf();
      if (!cpfService.isValidCpf(cpf)) {
        throw new InvalidCpfException("CPF " + cpf + " é inválido.");
      }
      if (repositorio.findByCpf(cpf).isPresent()) {
        throw new ExistsCpfException("CPF " + cpf + " já existe na lista.");
      }
      user.setCreatedAt(LocalDateTime.now());
      repositorio.save(user);
      return "cpf " + ":" + cpf + " createdAt: " + user.getCreatedAt();
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
}
