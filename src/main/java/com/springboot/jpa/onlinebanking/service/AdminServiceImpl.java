package com.springboot.jpa.onlinebanking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.jpa.onlinebanking.dao.CustomerDetailsRepository;
import com.springboot.jpa.onlinebanking.dao.PostNewsRepository;
import com.springboot.jpa.onlinebanking.dao.RequestRepository;
import com.springboot.jpa.onlinebanking.dao.SequenceNumberGenerationRepository;
import com.springboot.jpa.onlinebanking.dao.TransactionDetailsRepository;
import com.springboot.jpa.onlinebanking.entity.CustomerDetails;
import com.springboot.jpa.onlinebanking.entity.PostNews;
import com.springboot.jpa.onlinebanking.entity.Request;
import com.springboot.jpa.onlinebanking.entity.SequenceNumberGeneration;
import com.springboot.jpa.onlinebanking.entity.TransactionDetails;

@Service
public class AdminServiceImpl implements AdminService {

	private CustomerDetailsRepository customerDetailsRepository;
	private PostNewsRepository  postNewsRepository;
	private RequestRepository   requestRepository;
	private TransactionDetailsRepository transactionDetailsRepository;
    private SequenceNumberGenerationRepository sequenceNumberGenerationRepository;
    
	@Autowired
	public AdminServiceImpl(CustomerDetailsRepository CustomerDetailsRepository,
			                PostNewsRepository  PostNewsRepository,
			                RequestRepository RequestRepository,
			                TransactionDetailsRepository TransactionDetailsRepository,
			                SequenceNumberGenerationRepository SequenceNumberGenerationRepository ) {
		this.customerDetailsRepository = CustomerDetailsRepository;
		this.postNewsRepository        = PostNewsRepository  ;
		this.requestRepository         = RequestRepository ;
		this.transactionDetailsRepository = TransactionDetailsRepository;
		this.sequenceNumberGenerationRepository = SequenceNumberGenerationRepository;
	}
   
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//customer
	@Override
	public Page<CustomerDetails> customersList(int pageNumber, int itemsPerPage) {

		Pageable pageable = PageRequest.of(pageNumber, itemsPerPage);
		return customerDetailsRepository.customersList(pageable);
	}

	@Override
	public Page<CustomerDetails> sortedCustomersList(int pageNumber, int itemsPerPage, String fieldName) {
		Pageable pageable = PageRequest.of(pageNumber, itemsPerPage, Sort.by(fieldName));
		return customerDetailsRepository.customersList(pageable);
	}

	@Override
	public CustomerDetails save(CustomerDetails customerDetails) {
		customerDetails.setPassword(passwordEncoder.encode(customerDetails.getPassword()));
		return customerDetailsRepository.save(customerDetails);

	}//password encoder
	
	@Override
	public SequenceNumberGeneration save(SequenceNumberGeneration sequenceNumberGeneration) {
		return sequenceNumberGenerationRepository.save(sequenceNumberGeneration);
	}
	
	@Override
	public CustomerDetails update(CustomerDetails customerDetails) {
	
		return customerDetailsRepository.save(customerDetails);
	}

	@Override
	public CustomerDetails findByEmail(String emailId) {
    return customerDetailsRepository.findbyEmail(emailId);
		

	}

	@Override
	public CustomerDetails findByAadhar(String aadharNumber) {
		return customerDetailsRepository.findbyAadhar(aadharNumber);
	}

	@Override
	public CustomerDetails findByPhone(String phoneNumber) {
		return customerDetailsRepository.findbyPhone(phoneNumber);
	}

	@Override
	public CustomerDetails findByAccountNumber(String accountNumber) {
		return customerDetailsRepository.findbyAccNo(accountNumber);
	}

	
	@Override
	public List<CustomerDetails> findAllCustomers(CustomerDetails customerDetails) {
		return customerDetailsRepository.findAll();
	}

	//news
	@Override
	public PostNews save(PostNews news) {
		return postNewsRepository.save(news);
	}
	
	//request

	@Override
	public Page<Request> pendingInProgressRequests(int pageNumber, int itemsPerPage) {
		Pageable pageable = PageRequest.of(pageNumber, itemsPerPage);
		return requestRepository.pendingInProgressRequests(pageable);
	}

	@Override
	public Page<Request> sortPendingInProgressRequests(int pageNumber, int itemsPerPage, String fieldName) {
		Pageable pageable =PageRequest.of(pageNumber, itemsPerPage, Sort.by(fieldName));
		return requestRepository.pendingInProgressRequests(pageable);
	}
	
	@Override
	public Page<Request> successRejectedRequests(int pageNumber, int itemsPerPage) {
		Pageable pageable =PageRequest.of(pageNumber, itemsPerPage);
		return requestRepository.successRejectedRequests(pageable);
	}

	@Override
	public Page<Request> sortSuccessRejectedRequests(int pageNumber, int itemsPerPage, String fieldName) {
		Pageable pageable =PageRequest.of(pageNumber, itemsPerPage, Sort.by(fieldName));
		return requestRepository.successRejectedRequests(pageable);
	}

	
	@Override
	public Request setSuccess(int id) {
		Optional<Request> result = requestRepository.findById(id);
		Request customer = null;
		customer = result.get();
		customer.setStatus("Success");
		requestRepository.save(customer);
		return customer;
	}

	@Override
	public Request setRejected(int id) {
		Optional<Request> result = requestRepository.findById(id);
		Request customer = null;
		customer = result.get();
		customer.setStatus("Rejected");
		requestRepository.save(customer);
		return customer;
	}

	@Override
	public Request setInProgress(int id) {
		Optional<Request> result = requestRepository.findById(id);
		Request customer = null;
		customer = result.get();
		customer.setStatus("inprogress");
		requestRepository.save(customer);
		return customer;
	}
	
	//Transactions
	
	@Override
	public List<TransactionDetails> findAllTransactions() {

		return transactionDetailsRepository.findAll();
	}

	@Override
	public Page<TransactionDetails> getAllTransactions(int pageNumber, int itemsPerPage) {
		 Pageable pageable = PageRequest.of(pageNumber, itemsPerPage);
			return transactionDetailsRepository.findAll(pageable);
	}

	@Override
	public Page<TransactionDetails> getSortedTransactions(int pageNumber, int itemsPerPage, String fieldName) {
		Pageable pageable =PageRequest.of(pageNumber, itemsPerPage, Sort.by(fieldName));
		return transactionDetailsRepository.findAll(pageable);
	}

	

	

	

	
}
