package com.freelancer.paypal;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaypalService {
	
	@Autowired
	private APIContext apiContext;

	public Payment createPayment(
			Double paymentAmount) throws PayPalRESTException{
		Amount amount = new Amount();
		amount.setCurrency("USD");
		paymentAmount = new BigDecimal(paymentAmount).setScale(2, RoundingMode.HALF_UP).doubleValue();
		amount.setTotal(String.valueOf(paymentAmount));

		Transaction transaction = new Transaction();
		transaction.setDescription("Thanh toán cho https://freelancer-chat.herokuapp.com/");

		transaction.setAmount(amount);

		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);

		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");

		Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);  
		payment.setTransactions(transactions);
//		RedirectUrls redirectUrls = new RedirectUrls();
//		redirectUrls.setCancelUrl(cancelUrl);
//		redirectUrls.setReturnUrl(successUrl);
//		payment.setRedirectUrls(redirectUrls);
		return payment.create(apiContext);
	}
	
	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
		Payment payment = new Payment();
		payment.setId(paymentId);
		PaymentExecution paymentExecute = new PaymentExecution();
		paymentExecute.setPayerId(payerId);
		return payment.execute(apiContext, paymentExecute);
	}

}
