package com.springboot.jpa.onlinebanking.controller;

import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.jpa.onlinebanking.entity.CustomerDetails;
import com.springboot.jpa.onlinebanking.entity.PostNews;
import com.springboot.jpa.onlinebanking.entity.Request;
import com.springboot.jpa.onlinebanking.entity.SequenceNumberGeneration;
import com.springboot.jpa.onlinebanking.entity.TransactionDetails;
import com.springboot.jpa.onlinebanking.exceptions.CustomerNotAddedException;
import com.springboot.jpa.onlinebanking.response.Response;
import com.springboot.jpa.onlinebanking.service.AdminService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins= "http://localhost:4200")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	//customer
	
	@GetMapping("/customer/{pageNumber}/{itemsPerPage}")
	public Page<CustomerDetails> getAllCustomers(@PathVariable int pageNumber, @PathVariable int itemsPerPage){
		return adminService.customersList(pageNumber, itemsPerPage);
		
	}
	
	@GetMapping("/customer/{pageNumber}/{itemsPerPage}/{fieldName}")
	public Page<CustomerDetails> getSortedCustomers(@PathVariable int pageNumber, @PathVariable int itemsPerPage, @PathVariable String fieldName){
		return adminService.sortedCustomersList(pageNumber, itemsPerPage, fieldName);
		
	}
	
	@PostMapping("/addCustomer")
	public Response<CustomerDetails> addCustomer(@Valid @RequestBody CustomerDetails customerDetails) {
		
		
		CustomerDetails result  = adminService.findByEmail(customerDetails.getEmailId()) ;
		
		CustomerDetails result1 = adminService.findByPhone(customerDetails.getPhoneNumber()) ;
		
		CustomerDetails result2 = adminService.findByAadhar(customerDetails.getAadharNumber()) ;
		
		CustomerDetails result3 = adminService.findByAccountNumber(customerDetails.getAccountNumber()) ;
		
		if(result !=null) {
			return new Response<CustomerDetails>(true, "This Email already exists" ,null);
		}else if (result1 != null){
			return new Response<CustomerDetails>(true, "This Phone Number already exists" ,null);
		}else if(result2 != null) {
			return new Response<CustomerDetails>(true, "This Aadhar Number already exists" ,null);
		}else if(result3 != null) {
			return new Response<CustomerDetails>(true, "This Account Number already exists" ,null);
		}
		
		customerDetails.setCustomerId(0);
		
		SequenceNumberGeneration sequenceNumberGeneration = new SequenceNumberGeneration();
		SequenceNumberGeneration sequence = adminService.save(sequenceNumberGeneration);

		long accountNumber = 1010100000000101l + sequence.getId();
		customerDetails.setAccountNumber(accountNumber + "");
		
        Random random = new Random();
		
		String generatedPin = String .format("%04d", random.nextInt(10000));
		
		customerDetails.setPin(generatedPin);
		
		CustomerDetails customer=adminService.save(customerDetails);
		
		if(customer != null) {
			
			return new Response<>(false,"Customer added Successfully", customer);
		}else {
			throw new CustomerNotAddedException("Customer not added Successfully"); 
		}
		
	}
	
	@PutMapping("/updateCustomer")
	public CustomerDetails updateCustomer(@RequestBody CustomerDetails customer) {
		
		adminService.update(customer);
		return customer;
	}
	
	@SuppressWarnings("unused")
	@PutMapping("/forgotPassword/{emailId}/{password}")
	public Response<CustomerDetails> forgotPassword(@PathVariable String emailId ,@PathVariable String password , @RequestBody CustomerDetails customerDetails) {
		
		customerDetails= adminService.findByEmail(emailId);
		
		customerDetails.setPassword(password);
		
		adminService.save(customerDetails);
		if(customerDetails != null) {
		return new Response<>(false, "your password was successfully changed!", customerDetails);
		}else {
			return new Response<>(true, "new password saving failed", null);
		}
	}
	
	//news
	@PostMapping("/post-news")
	public Response<PostNews> savePost(@Valid @RequestBody PostNews postNews) {
		
		postNews.setId(0);
		
		PostNews sendNews = adminService.save(postNews);
		if(sendNews != null) {
			return new Response<PostNews>(false, "News posted successfully", sendNews);
		} else {
			return new Response<PostNews>(true, "News failed to post", null);
		}

	}
	
	//requests
	@GetMapping("/requests/{pageNumber}/{itemsPerPage}")
	public Page<Request> getRequests(@PathVariable int pageNumber, @PathVariable int itemsPerPage){
		return adminService.pendingInProgressRequests(pageNumber, itemsPerPage);
		
	}
	
	@GetMapping("/requests/{pageNumber}/{itemsPerPage}/{fieldName}")
	public Page<Request> getSortRequests(@PathVariable int pageNumber, @PathVariable int itemsPerPage, @PathVariable String fieldName){
		return adminService.sortPendingInProgressRequests(pageNumber, itemsPerPage, fieldName);
		
	}
	
	@GetMapping("/completedRequests/{pageNumber}/{itemsPerPage}")
	public Page<Request> getAllRequests(@PathVariable int pageNumber, @PathVariable int itemsPerPage){
		return adminService.successRejectedRequests(pageNumber, itemsPerPage);
		
	}
	
	@GetMapping("/completedRequests/{pageNumber}/{itemsPerPage}/{fieldName}")
	public Page<Request> getSortedRequests(@PathVariable int pageNumber, @PathVariable int itemsPerPage, @PathVariable String fieldName){
		return adminService.sortSuccessRejectedRequests(pageNumber, itemsPerPage, fieldName);
		
	}
	
	@PutMapping("/update-success/{id}")
	public Response<Request> findAllSuccess(@PathVariable Integer id) {
		Request result = adminService.setSuccess(id);
		if(result == null) {
			return new Response<>(true, "Updation failed", null);
		} else {
			return new Response<>(false, "successfully updated", result);
		}
	}
	@PutMapping("/update-rejected/{id}")
	public Response<Request> findAllChanged(@PathVariable Integer id) {
		Request result =  adminService.setRejected(id);
		if(result == null) {
			return new Response<>(true, "Updation failed", null);
		} else {
			return new Response<>(false, "successfully updated", result);
		}
	}
	
	@PutMapping("/update-inprogress/{id}")
	public Response<Request> findAllInProgress(@PathVariable Integer id) {
		Request result =  adminService.setInProgress(id);
		if(result == null) {
			return new Response<>(true, "Updation failed", null);
		} else {
			return new Response<>(false, "successfully updated", result);
		}
	}
	
	//Transactions
	@GetMapping("/allTransactions")
	public List<TransactionDetails> findAllTranscations() {
		
		return adminService.findAllTransactions();
	}

	@GetMapping("/allTransactions/{pageNumber}/{itemsPerPage}")
	public Page<TransactionDetails> getAllTransactions(@PathVariable int pageNumber, @PathVariable int itemsPerPage){
		return adminService.getAllTransactions(pageNumber, itemsPerPage);
		
	}
	
	@GetMapping("/allTransactions/{pageNumber}/{itemsPerPage}/{fieldName}")
	public Page<TransactionDetails> getSortedTransactions(@PathVariable int pageNumber, @PathVariable int itemsPerPage, @PathVariable String fieldName){
		return adminService.getSortedTransactions(pageNumber, itemsPerPage, fieldName);
		
	}
}
