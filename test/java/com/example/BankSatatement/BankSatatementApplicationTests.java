package com.example.BankSatatement;

import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.BankSatement.Util.BankUtils;
import com.example.BankStatement.dto.BankRequestDTO;
import com.example.BankStatement.entity.Account;
import com.example.BankStatement.entity.Statements;

@SpringBootTest
class BankSatatementApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testByAmountByAdmin() throws NoSuchAlgorithmException, ParseException {
		BankUtils util = new BankUtils();
		List<Statements> stmts = new ArrayList<>();
		Account acc = new Account();
		BankRequestDTO dto = new BankRequestDTO();
		Long accountId = 1L;
		dto.setAmountFrom("23.90");
		dto.setAmountTo("900.980");
		dto.setByAmount(true);
		acc = util.connectionCreation(dto, accountId, stmts);
		assertTrue("", acc.getStatements().size() >= 2);

	}

	@Test
	void testByDateByAdmin() throws NoSuchAlgorithmException, ParseException {
		BankUtils util = new BankUtils();
		List<Statements> stmts = new ArrayList<>();
		Account acc = new Account();
		BankRequestDTO dto = new BankRequestDTO();
		Long accountId = 1L;
		dto.setDateFrom("01.02.2019");
		dto.setDateTo("01.02.2020");
		dto.setByDate(true);
		acc = util.connectionCreation(dto, accountId, stmts);
		assertTrue("", acc.getStatements().size() >= 1);

	}

	@Test
	void testByIdByAdminOrUser() throws NoSuchAlgorithmException, ParseException {
		BankUtils util = new BankUtils();
		List<Statements> stmts = new ArrayList<>();
		Account acc = new Account();
		BankRequestDTO dto = null;
		Long accountId = 1L;
		acc = util.connectionCreation(dto, accountId, stmts);
		acc.setStatements(stmts);
		assertTrue("", acc.getAccount_type().equalsIgnoreCase("current"));
		assertTrue("", acc.getStatements().size() >= 0);
	}

}
