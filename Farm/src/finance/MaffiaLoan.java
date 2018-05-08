package finance;

import java.io.Serializable;

/**
 * Class for the lender Maffia loan
 * 
 * @author Matthias Falk
 *
 */
public class MaffiaLoan implements Serializable{
	private int minLoan = 100;
	private int maxLoan = 100000;
	private int interest = 50;
	private boolean hasLoan = false;
	private int debt = 0;
	
	/**
	 * Returns the lowest acceptable loan value
	 * 
	 * @return minLoan
	 */
	public int getMinLoan() {
		return minLoan;
	}
	/**
	 * Returns the highest acceptable loan value
	 * 
	 * @return maxLoan
	 */
	public int getMaxLoan() {
		return maxLoan;
	}
	/**
	 * Returns the interest percentage of the loan
	 * 
	 * @return interest
	 */
	public int getInterest() {
		return interest;
	}
	/**
	 * sets the boolean variable hasLoan
	 * 
	 * @param hasLoan
	 *            true if the user takes a loan, false if the loan is payed off
	 */
	public void setHasLoan(boolean hasLoan) {
		this.hasLoan = hasLoan;
	}
	/**
	 * returns the boolean variable hasLoan
	 * 
	 * @return hasLoan
	 */
	public boolean getHasLoan() {
		return hasLoan;
	}
	/**
	 * sets the debt to this lender
	 * 
	 * @param amount
	 *            the amount the user owes
	 */
	public void setDebt(int amount) {
		debt += amount;
	}
	/**
	 * returns the amount that the user owe this lender
	 * 
	 * @return debt
	 */
	public int getDebt() {
		return this.debt;
	}
}
