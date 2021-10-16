package com.freelancer.paypal;

import com.freelancer.model.Job;
import com.freelancer.model.ResponseObject;
import com.freelancer.repository.JobRepository;
import com.freelancer.service.JobService;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/job/payment")
public class PaypalController {
	@Autowired
	JobRepository jobRepository;
	@Autowired
	JobService jobService;
	@Autowired
	PaypalService service;
	private Payment payment = null;
	public static final String SUCCESS_URL = "pay/success";
	public static final String CANCEL_URL = "pay/cancel";

	@RequestMapping(value = "/create-payment",method = RequestMethod.POST)
	public Payment payment(@RequestParam Long id, HttpServletRequest request){
			String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
					.replacePath("/api/v1/job/payment/")
					.build()
					.toUriString();
			Optional<Job> finJob = jobRepository.findById(id);
			if(finJob.isPresent()){
				Job rl = finJob.get();
				try {
					payment = service.createPayment(String.valueOf(id),rl.getPaymentAmount(), baseUrl + CANCEL_URL, baseUrl + SUCCESS_URL);
				} catch (PayPalRESTException e) {
					e.printStackTrace();
				}
				System.out.println(payment.toJSON());
				return payment;
			}
		return payment;
	}

	@GetMapping(value = "/execute-payment")
	public Payment execute(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String PayerID,@RequestParam("id") Long id) {
		try {
			payment = service.executePayment(paymentId, PayerID);
			if (payment.getState().equals("approved")) {
				System.out.println("id la " + id);
				jobService.setIsPaymentStatusJob(id);
				return payment;
			}
		} catch (PayPalRESTException e) {
			e.printStackTrace();
		}
		return payment;
	}




	 	@GetMapping(value = CANCEL_URL)
	    public String cancelPay() {
	        return "Thất bại";
	    }

	    @GetMapping(value = SUCCESS_URL)
	    public String successPay() {
			return "Thành công";
	    }

}
