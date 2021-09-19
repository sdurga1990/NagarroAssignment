package com.example.BankSatatement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.BankStatement.dto.BankRequestDTO;
import com.example.BankStatement.entity.Account;
import com.example.BankStatement.exception.BusinessException;
import com.example.BankStatement.service.BankService;

@EnableAutoConfiguration(exclude = { HibernateJpaAutoConfiguration.class })
@SpringBootApplication
@RestController
@RequestMapping("/api")
@ComponentScan(basePackages = { "com.example.security.config" })
public class BankSatatementApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankSatatementApplication.class, args);
	}

	@PostMapping("/statement")
	public ResponseEntity<?> searchBy(@RequestParam Long id,@RequestBody BankRequestDTO bankReqDTO) throws Exception {
		if (0 != id) {
			BankService bankService = new BankService();
			Account resposne = bankService.getSatement(id, bankReqDTO);
			if (resposne == null) {
				return ResponseEntity.badRequest().body(new BusinessException("No Statement Avaialble"));
			} else {
				return new ResponseEntity<>(resposne, HttpStatus.OK);
			}
		} else {
			return ResponseEntity.badRequest().body(new BusinessException("Acount Id Shoud not be Empty"));
		}

	}

	@GetMapping("/statement/last")
	public ResponseEntity<?> search(@RequestParam Long id) throws Exception {
		if (0 != id) {
			BankService bankService = new BankService();
			Account resposne = bankService.getLastThreeStatement(id);
			if (resposne == null) {
				return ResponseEntity.badRequest().body(new BusinessException("No Statement Avaialble"));
			} else {
				return new ResponseEntity<>(resposne, HttpStatus.OK);
			}
		} else {
			return ResponseEntity.badRequest().body(new BusinessException("Acount Id Shoud not be Empty"));
		}

	}

}
