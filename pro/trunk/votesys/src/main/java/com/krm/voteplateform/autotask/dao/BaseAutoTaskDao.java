package com.krm.voteplateform.autotask.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.krm.voteplateform.common.spring.utils.SpringContextHolder;

@Component
public class BaseAutoTaskDao {

	private JdbcTemplate jdbcTemplate;

	private PlatformTransactionManager transactionManager;

	public void init() {
		this.transactionManager = SpringContextHolder.getBean("transactionManager");
		this.jdbcTemplate = SpringContextHolder.getBean("jdbcTemplate");
	}

	public void rollback(TransactionStatus status) {
		this.transactionManager.rollback(status);
	}

	public void commit(TransactionStatus status) {
		this.transactionManager.commit(status);
	}

	public PlatformTransactionManager getPlatformTransactionManager() {
		return transactionManager;
	}

	public TransactionDefinition getTransactionDefinition() {
		TransactionDefinition definition = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED);
		return definition;
	}

	public JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}
}
