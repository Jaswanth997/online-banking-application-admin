package com.capgemini.springboot.jpa.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.capgemini.springboot.jpa.entity.CustomerDetails;
import com.capgemini.springboot.jpa.entity.PostNews;
import com.capgemini.springboot.jpa.entity.Request;
import com.capgemini.springboot.jpa.entity.SequenceNumberGeneration;
import com.capgemini.springboot.jpa.entity.TransactionDetails;


public interface AdminService {
	
	//Customer-related
    
    public Page<CustomerDetails> customersList(int pageNumber, int itemsPerPage);
    
    public Page<CustomerDetails> sortedCustomersList(int pageNumber, int itemsPerPage, String fieldName);
    		
    public SequenceNumberGeneration save(SequenceNumberGeneration sequenceNumberGeneration);
    
	public CustomerDetails save(CustomerDetails customerDetails );
	
	public CustomerDetails update(CustomerDetails customerDetails);
		
	public CustomerDetails findByEmail(String email);

	public CustomerDetails findByAadhar(String aadhar);

	public CustomerDetails findByPhone(String mobNo);

	public CustomerDetails findByAccountNumber(String accNo);
	
	public List<CustomerDetails> findAllCustomers(CustomerDetails customerDetails);
	
	//news
	public PostNews save(PostNews news );
	
	//request
    public Request setSuccess(int id);
	    
	public Request setRejected(int id);
	    
	public Request setInProgress(int id);
	
	public Page<Request> pendingInProgressRequests(int pageNumber, int itemsPerPage);
	
	public Page<Request> sortPendingInProgressRequests(int pageNumber, int itemsPerPage, String fieldName);
	
    public Page<Request> successRejectedRequests(int pageNumber, int itemsPerPage);
	
	public Page<Request> sortSuccessRejectedRequests(int pageNumber, int itemsPerPage, String fieldName);
	
	//transactions
	
    public List<TransactionDetails> findAllTransactions();
	
	public Page<TransactionDetails> getAllTransactions(int pageNumber, int itemsPerPage);
    
    public Page<TransactionDetails> getSortedTransactions(int pageNumber, int itemsPerPage, String fieldName);
	


}
