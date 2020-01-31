package com.api.service;

import java.util.List;

import com.api.web.model.LoanJSON;
import com.api.web.model.RepaymentPlanJSON;

public interface BorrowPaymentService {

	List<RepaymentPlanJSON> generateRepaymentPlan(LoanJSON loanJSON);

}
