package com.example.BankStatement.service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.example.BankSatement.Util.BankUtils;
import com.example.BankStatement.dto.BankRequestDTO;
import com.example.BankStatement.entity.Account;
import com.example.BankStatement.entity.Statements;

@Service
public class BankService {

	org.slf4j.Logger logger = LoggerFactory.getLogger(BankService.class);

	public Account getSatement(Long accountId, BankRequestDTO bankRequest) throws SQLException, NoSuchAlgorithmException, ParseException {
		List<Statements> sts = new ArrayList<>();
		BankUtils util = new BankUtils();
		Account acc = util.connectionCreation(bankRequest, accountId, sts);
		return acc;
	}

	public Account getLastThreeStatement(Long id) throws SQLException, NoSuchAlgorithmException, ParseException {
		List<Statements> sts = new ArrayList<>();
		BankUtils util = new BankUtils();
		BankRequestDTO bankRequest =null;
		Account acc =util.connectionCreation(bankRequest, id, sts);
		return acc;

	}

}
