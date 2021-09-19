package com.example.BankSatement.Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;

import com.example.BankStatement.dto.BankRequestDTO;
import com.example.BankStatement.entity.Account;
import com.example.BankStatement.entity.Statements;

public class BankUtils {

	SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
	org.slf4j.Logger logger = LoggerFactory.getLogger(BankUtils.class);
	private String databaseURL = "jdbc:ucanaccess://C://Users//Pavanaj//Downloads//accountsdb.accdb";
	private String strParserExcMsg="Error in Bank service class for ParseException";

	public Account setValuesForStatements(ResultSet result) throws SQLException, NoSuchAlgorithmException {
		List<Statements> statementsDetails = new ArrayList<Statements>();
		String accountNumberToHash = "";
		String accountType = "";
		int accountId = 0;
		while (result.next()) {
			Statements s = new Statements();
			accountId = result.getInt(1);
			accountType = result.getString(4);
			accountNumberToHash = result.getString(5);
			s.setId(result.getInt(6));
			s.setAmount(result.getString(3));
			s.setDateField(result.getString(2));
			statementsDetails.add(s);
		}
		byte[] salt = getSalt();
		String secureaccountNumber = getSecureAccountNumber(accountNumberToHash, salt);
		Account ac = new Account();
		ac.setId(accountId);
		ac.setAccountNumber(secureaccountNumber);
		ac.setAccount_type(accountType);
		ac.setStatements(statementsDetails);
		return ac;
	}

	public Account connectionCreation(BankRequestDTO bankRequest, Long accountId, List<Statements> sts)
			throws NoSuchAlgorithmException, ParseException {
		Account acc = new Account();
		try (Connection connection = DriverManager.getConnection(databaseURL)) {

			String sql = "SELECT s.account_id,s.datefield,s.amount,ac.account_type,ac.account_number,s.id FROM statement as s FULL JOIN Account as ac ON s.account_id= ac.id  where s.account_id="
					+ accountId;

			Statement statement = connection.createStatement();

			ResultSet result = statement.executeQuery(sql);

			acc = setValuesForStatements(result);

			List<Statements> stmts = new ArrayList<>();
			if ((null == bankRequest)) {
				return callDateAndAmountNotPreset(stmts, acc);
			} else if (bankRequest.isByDate() && bankRequest.isByAmount()) {
				return callForDateandAmountRange(bankRequest, stmts, acc);
			} else if ((!bankRequest.isByDate()) && (!bankRequest.isByAmount())) {
				return callDateAndAmountNotPreset(stmts, acc);
			} else if (bankRequest.isByAmount()) {
				return callForAmountRange(bankRequest, stmts, acc);

			} else if (bankRequest.isByDate()) {
				return callForDateRange(bankRequest, stmts, acc);

			}

		} catch (SQLException ex) {
			logger.debug("Error in Bank service class in getSattement for SQL connection");
		}
		return acc;
	}

	private Account callForDateandAmountRange(BankRequestDTO bankRequest, List<Statements> stmts, Account acc)
			throws ParseException {
		String fromDate = bankRequest.getDateFrom();
		final java.util.Date frdate = format.parse(fromDate);
		String toDate = bankRequest.getDateTo();
		final java.util.Date todate = format.parse(toDate);
		double fromAmout = Double.parseDouble(bankRequest.getAmountFrom());
		double toAmount = Double.parseDouble(bankRequest.getAmountTo());
		if (acc.getStatements().size() > 0) {
			List<Statements> stmtsToStream = acc.getStatements();
			stmtsToStream.stream().forEach(data -> {
				try {
					java.util.Date checkDate = format.parse(data.getDateField());
					double amount = Double.parseDouble(data.getAmount());
					if ((checkDate.after(frdate) && checkDate.before(todate))
							&& (amount >= fromAmout && amount <= toAmount)) {
						stmts.add(data);
					}
				} catch (ParseException e) {
					logger.debug(strParserExcMsg);
				}
			});
			acc.getStatements().clear();
			acc.setStatements(stmts);
		}
		return acc;
	}

	private Account callForDateRange(BankRequestDTO bankRequest, List<Statements> stmts, Account acc)
			throws ParseException {
		String fromDate = bankRequest.getDateFrom();
		final java.util.Date frdate = format.parse(fromDate);
		String toDate = bankRequest.getDateTo();
		final java.util.Date todate = format.parse(toDate);
		if (acc.getStatements().size() != 0) {
			List<Statements> stmtsToStream = acc.getStatements();
			stmtsToStream.stream().forEach(data -> {
				try {
					java.util.Date checkDate = format.parse(data.getDateField());
					if (checkDate.after(frdate) && checkDate.before(todate)) {
						stmts.add(data);
					}
				} catch (ParseException e) {
					logger.debug(strParserExcMsg);
				}
			});
			acc.getStatements().clear();
			acc.setStatements(stmts);
		}
		return acc;
	}

	private Account callForAmountRange(BankRequestDTO bankRequest, List<Statements> stmts, Account acc) {
		double fromAmout = Double.parseDouble(bankRequest.getAmountFrom());
		double toAmount = Double.parseDouble(bankRequest.getAmountTo());
		if (acc.getStatements().size() != 0) {
			List<Statements> stmtsToStream = acc.getStatements();
			stmtsToStream.stream().forEach(data -> {
				double Amount = Double.parseDouble(data.getAmount());
				if (Math.round(Amount) >= Math.round(fromAmout) && Math.round(Amount) <= toAmount) {
					stmts.add(data);
				}
			});
			acc.getStatements().clear();
			acc.setStatements(stmts);
		}
		return acc;
	}

	private Account callDateAndAmountNotPreset(List<Statements> stmts, Account acc)
			throws ParseException {

		String fromDate = "01.07.2021";
		final java.util.Date fdate = format.parse(fromDate);
		String toDate = "15.09.2021";
		final java.util.Date tdate = format.parse(toDate);

		if (acc.getStatements().size() != 0) {
			List<Statements> stmtsToStream = acc.getStatements();
			stmtsToStream.stream().forEach(data -> {
				try {
					java.util.Date checkDate = format.parse(data.getDateField());
					if (checkDate.after(fdate) && checkDate.before(tdate)) {
						stmts.add(data);
					}
				} catch (ParseException e) {
					logger.debug("Error in Bank service class for ParseException");
				}
			});
			acc.getStatements().clear();
			acc.setStatements(stmts);
		}
		return acc;
	}

	public static String getSecureAccountNumber(String passwordToHash, byte[] salt) {
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(salt);
			byte[] bytes = md.digest(passwordToHash.getBytes());
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}

	public static byte[] getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt;
	}
}
