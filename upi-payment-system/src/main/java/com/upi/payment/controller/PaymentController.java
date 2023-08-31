package com.upi.payment.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upi.payment.entity.PaymentDetails;
import com.upi.payment.exception.ResourceNotFoundException;
import com.upi.payment.repository.PaymentRepository;


@RestController
@RequestMapping("/api/payment/upi")
public class PaymentController {
	
	public static final Logger logInfo = LoggerFactory.getLogger(PaymentController.class);
	
	@Autowired
	private PaymentRepository paymentRepository;

	public PaymentController(PaymentRepository paymentRepository) {
		super();
		this.paymentRepository = paymentRepository;
	}

	// post = add new payment
	@PostMapping("/create")
	public PaymentDetails makePayment(@RequestBody PaymentDetails paymentDetails) {
		return this.paymentRepository.save(paymentDetails);
	}
	
	@GetMapping("/getAll")
	public List<PaymentDetails> getAllPayments(PaymentDetails paymentDetails){
		return this.paymentRepository.findAll();
	}

	// GET = retrieve all payment transaction for duplicate person who gave the money, like 2 time time or duplication
	@GetMapping("/getAllNames")
    public ResponseEntity<Set<String>> getAllNames() {
        List<String> names = paymentRepository.findAll().stream()
                                    .map(PaymentDetails::getName)
                                    .collect(Collectors.toList());
         
         System.out.println(names);
         
        // name -> name = Function.identity() as key in map , it return what it gets 
      //   Map<String, Long> mapOfNames = names.stream() .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
        Set<String> duplicateNames = names.stream()
                                        .collect(Collectors.groupingBy(
                                        name -> name, Collectors.counting())) // convert this map into stream as below
                                        .entrySet().stream()
                                        .filter(entry -> entry.getValue() > 1) // check frequecy > 1 for duplicate elements, find such elements
                                        .map(Map.Entry::getKey)
                                        .collect(Collectors.toSet());  // collect in a set
        
        // or second approach line 54 to 61
    	/*Set<String> uniqueNames = new HashSet<>();
		Set<String> duplicateNames =  names.stream()
		                             .filter(name->!uniqueNames.add(name))
		                             .collect(Collectors.toSet());*/
        
        logInfo.info("PaymentController class Info logging is enabled");  // default logging 
        logInfo.debug("PaymentController class Debug logging is enabled");  // explicitely logging or not default = debug, trace
        return new ResponseEntity<>(duplicateNames, HttpStatus.OK);
    }
	
	// GET = retrieve perticular payment by Id
	/*@GetMapping("/{id}")
	public PaymentDetails getPaymentById(@PathVariable(value="id")long id){
		return this.paymentRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Paymentdetails","id",id));		
				
	}*/
	
	
	// by an Optional class, checking if id is not mapped to name, it will handle NPE and print "Anonymous" on console
	@GetMapping(path="id/{id}")
	public ResponseEntity<?> getPaymentById(@PathVariable(value="id")long id){
		Optional<PaymentDetails> e = paymentRepository.findById(id);
		
		if(!e.isEmpty()) {
		String name = Optional.ofNullable(e.get().getName()).orElse("Anonymous");
		return new ResponseEntity<>(name,HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Sorry name with given id is not present",HttpStatus.OK);
		}
	}
	
	// PUT = retrieve existing resource* 
	@PutMapping("/{id}")
	public PaymentDetails updatePayment(@RequestBody PaymentDetails paymentDetails, @PathVariable(value = "id")long id) {
		
		// check if given payment is given or not
		
		PaymentDetails existingPayment = this.paymentRepository.findById(id)
				                   .orElseThrow(()-> new ResourceNotFoundException("Paymentdetails","id",id));
		
		// setting new values to paymentDetails field
		existingPayment.setName(paymentDetails.getName());
		existingPayment.setAmount(paymentDetails.getAmount());
		existingPayment.setRemarks(paymentDetails.getRemarks());
		return this.paymentRepository.save(existingPayment) ;
	}
	
	// Delete = delete resource from the server
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePayment(@PathVariable(value="id")long id){
		paymentRepository.deleteById(id);
		return new ResponseEntity<>("paymentDetails entity deleted successfully",HttpStatus.OK);
	}
	
}
