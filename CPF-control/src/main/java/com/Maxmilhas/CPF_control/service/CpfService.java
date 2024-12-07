package com.Maxmilhas.CPF_control.service;

import org.springframework.stereotype.Service;

@Service
public class CpfService {
  public boolean isValidCpf(String cpf) {
    System.out.println("CPF: " + cpf);
    cpf = cpf.replaceAll("[^0-9]", "");

    if (cpf.length() != 11) {
      return false;
    }
    return true;
  }
}
