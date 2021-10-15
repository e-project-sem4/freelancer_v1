package com.freelancer.paypal;

import com.freelancer.model.Job;
import com.freelancer.repository.JobRepository;
import com.freelancer.service.JobService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
					payment = service.createPayment(rl.getPaymentAmount());
				} catch (PayPalRESTException e) {
					e.printStackTrace();
				}
				return payment;
			}
		return payment;
	}

	@RequestMapping(value = "/execute-payment",method = RequestMethod.GET)
	public Payment execute(@RequestParam("paymentId") String paymentId, @RequestParam("payerId") String payerId, HttpServletResponse response) {
		try {
			payment = service.executePayment(paymentId, payerId);
			if (payment.getState().equals("approved")) {
				String id = payment.getExperienceProfileId();
				Optional<Job> finJob = jobRepository.findById(Long.valueOf(id));
				if(finJob.isPresent()){
					Job rl = finJob.get();
					rl.setIsPaymentStatus(1);
					jobService.update(rl, Long.valueOf(id));
				}
				return payment;
			}
		} catch (PayPalRESTException e) {
			System.out.println(e.getMessage());
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
