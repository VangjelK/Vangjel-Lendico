package com.api.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.api.web.model.LoanJSON;
import com.api.web.model.RepaymentPlanJSON;

@Service
public class BorrowPaymentServiceImpl implements BorrowPaymentService {

	public List<RepaymentPlanJSON> generateRepaymentPlan(LoanJSON loanJSON) {
		Date stardate = loanJSON.getStartDate();
		double loanAmount = loanJSON.getLoanAmount();
		double nominalRate = loanJSON.getNominalRate() / 100;
		int duration = loanJSON.getDuration();		
		double annuity = calculateBorrowerPaymentAmount(nominalRate, loanAmount, duration);
		double initialOutstandingPrincipal = loanAmount;

		List<RepaymentPlanJSON> plans = new ArrayList<>();
		for (int i=1 ; i <= duration; i++) {
			double interes = calculateInteres(nominalRate, initialOutstandingPrincipal);
			double principal = calculatePrincipal(annuity, interes, initialOutstandingPrincipal);
			double borrowerPaymentAmount = round(interes + principal, 2);
			double remainingOutstandingPrincipal = calculateRemainingOutstandingPrincipal(principal, initialOutstandingPrincipal);
			
			RepaymentPlanJSON repaymentPlan = new RepaymentPlanJSON();
			repaymentPlan.setDate(stardate);
			repaymentPlan.setBorrowerPaymentAmount(borrowerPaymentAmount);
			repaymentPlan.setPrincipal(principal);
			repaymentPlan.setInterest(interes);
			repaymentPlan.setInitialOutstandingPrincipal(initialOutstandingPrincipal);
			repaymentPlan.setRemainingOutstandingPrincipal(remainingOutstandingPrincipal);

			plans.add(repaymentPlan);
		
			Calendar calendar = Calendar.getInstance(); 
			calendar.setTime(stardate); 
			calendar.add(Calendar.MONTH, 1);
			stardate = calendar.getTime();
			initialOutstandingPrincipal = round(initialOutstandingPrincipal - principal, 2);
		}
		return plans;
	}

	private double calculateBorrowerPaymentAmount(double nominalRate, double loanAmount, int duration) {
		double nominalMonthly = nominalRate / 12;
		double annuity = (loanAmount * nominalMonthly ) / (1- Math.pow(1 + nominalMonthly, -duration));
		return round(annuity, 2);
	}

	private double calculatePrincipal(double annuity, double interes, double initialOutstandingPrincipal) {
		double principal = annuity - interes;
		if (initialOutstandingPrincipal < annuity) {
			principal = initialOutstandingPrincipal;
		}
		return round(principal, 2);
	}

	private double calculateRemainingOutstandingPrincipal(double principal, double initialOutstandingPrincipal) {
		double remainingOutstandingPrincipal = initialOutstandingPrincipal - principal;
		return round(remainingOutstandingPrincipal, 2);
	}

	private double calculateInteres(double nominalRate, double initialOutstandingPrincipal) {
		int monthDays = 30;
		int yearDays = 360;
		double interes = (nominalRate * monthDays * initialOutstandingPrincipal) / yearDays;
		return round(interes, 2);
	}
	
	private double round(double value, int places) {
	    if (places < 0) {
	    	throw new IllegalArgumentException();
	    }
	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
}
