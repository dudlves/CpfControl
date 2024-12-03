package com.Maxmilhas.CPF_control.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Maxmilhas.CPF_control.service.CpfService;

@RestController
@RequestMapping("/cpf-Control")
public class CpfController {
  private CpfService cpfService;

  public CpfController(CpfService cpfService){
    this.cpfService = cpfService;
    new CpfService();
  }

  @GetMapping
  public String cpfControl(){
    return cpfService.cpfControl("duda");
  }

}
