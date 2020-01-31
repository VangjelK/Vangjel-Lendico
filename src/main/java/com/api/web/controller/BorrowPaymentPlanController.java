package com.api.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.service.BorrowPaymentService;
import com.api.web.model.LoanJSON;
import com.api.web.model.RepaymentPlanJSON;

@RestController
@RequestMapping("")
public class BorrowPaymentPlanController {

	@Autowired
	private BorrowPaymentService borrowPaymentService;

	@RequestMapping(value = "/generate-plan", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public List<RepaymentPlanJSON> generateRepaymentPlan(@RequestBody LoanJSON loanJSON) {
		return borrowPaymentService.generateRepaymentPlan(loanJSON);
	}
	
}
