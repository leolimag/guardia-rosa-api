package br.com.tcc.guardia.rosa.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")	
public class TesteController {
	
	@GetMapping
	public ResponseEntity<Teste> teste() {
		Teste teste = new Teste();
		teste.setNumero(1);
		teste.setConteudo("parabéns");
		return ResponseEntity.ok(teste);
	}

//	@ResponseBody
//	@GetMapping
//	public ResponseEntity<Hello> getHello(){
//		Hello hello = new Hello();
//		hello.setMessage("hello angular");
//		hello.setId(1);
//		return ResponseEntity.ok(hello);
//	}
}
