package com.freelancer.paypal;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.freelancer.model.Job;
import com.freelancer.model.Transaction;
import com.freelancer.repository.JobRepository;
import com.freelancer.security.JwtTokenProvider;
import com.freelancer.service.JobService;
import com.freelancer.service.TransactionService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/job/payment")
public class PaypalController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private static final String AUTHORIZATION = "Authorization";
    @Autowired
    JobRepository jobRepository;
    @Autowired
    JobService jobService;
    @Autowired
    TransactionService transactionService;
    @Autowired
    PaypalService service;
    private Payment payment = null;
    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";

    @RequestMapping(value = "/create-payment", method = RequestMethod.POST)
    public Payment payment(@RequestParam Long id, HttpServletRequest request) {
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath("/api/v1/job/payment/")
                .build()
                .toUriString();
        Optional<Job> finJob = jobRepository.findById(id);
        if (finJob.isPresent()) {
            Job rl = finJob.get();
            try {
                payment = service.createPayment(String.valueOf(rl.getId()), rl.getName(), rl.getPaymentAmount(), baseUrl + CANCEL_URL, baseUrl + SUCCESS_URL);
            } catch (PayPalRESTException e) {
                e.printStackTrace();
            }
            System.out.println(payment.toJSON());
            return payment;
        }
        return payment;
    }

    @GetMapping(value = "/execute-payment")
    public Payment execute(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String PayerID, HttpServletRequest request) {
            String token = request.getHeader(AUTHORIZATION);
            String username = jwtTokenProvider.getUsername(token);
            com.paypal.api.payments.Transaction rl = payment.getTransactions().get(0);
            jobService.setIsPaymentStatusJob(Long.valueOf(rl.getNoteToPayee())); // Chuyển trạng thái thanh toán của Job
            Transaction history = new Transaction();
            history.setContent(rl.getDescription()); // Description của Job
            history.setPrice(Double.valueOf(rl.getAmount().getTotal())); // paymentAmount của Job
            history.setJob_id(Long.valueOf(rl.getNoteToPayee()));// id cua Job
            history.setType(Transaction.TransactionType.PAYMENT); // kiểu thanh toán
            history.setOrderID(payment.getId()); // orderId của paypal
            transactionService.createTransactionJob(history, username); // tạo Transaction
            System.out.println(payment.toJSON());
            return payment;
        }

        @GetMapping(value = CANCEL_URL)
        public String cancelPay () {
            return "Thất bại";
        }

        @GetMapping(value = SUCCESS_URL)
        public String successPay () {
            return "Thành công";
        }

    }
