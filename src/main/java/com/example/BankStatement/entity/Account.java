package com.example.BankStatement.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "account")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private int id;

	@Column(name = "account_type")
	private String account_type;

	@Column(name = "account_number")
	private String accountNumber;

	@OneToMany(mappedBy = "account", cascade = { CascadeType.ALL })
	private List<Statements> statements;

	public Account(int id, String account_type, String accountNumber, List<Statements> statements) {
		super();
		this.id = id;
		this.account_type = account_type;
		this.accountNumber = accountNumber;
		this.statements = statements;
	}

	public Account() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccount_type() {
		return account_type;
	}

	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public List<Statements> getStatements() {
		return statements;
	}

	public void setStatements(List<Statements> statements) {
		this.statements = statements;
	}

}
