package controller;

import java.util.Scanner;

public class Calculator {

	private float Princpal;
	private float Tenure;
	private float SimpleInterest;
	private static float RateOfInterest;

	// Static Block
	static {
		System.out.print("\u001B[31m");
		System.out.println("<-- It is more profitable to be a lender than a spender --> ");
		System.out.print("\u001B[0m"); // Reset color to default
	}

	// Method like -> Setter to accept input
	public void AcceptInput() {
		Scanner sc = new Scanner(System.in);

		System.out.print("Enter The Principal Amount ->  ");
		Princpal = sc.nextFloat();

		System.out.print("Enter Rate of Interst -> ");
		RateOfInterest = sc.nextFloat();

		System.out.print("Enter The Tenure -> ");
		Tenure = sc.nextFloat();

		sc.close();
	}

	// Method like -> Setter to calculate simple interest
	public void SI_Calculate() {
		SimpleInterest = (Princpal * Tenure * RateOfInterest) / 100;
	}

	// Method like -> Setter to display the calculated simple interest
	public void Display() {
		System.out.println("Simple Interest: " + SimpleInterest);
	}
}

class Interest_Rate_Calculator {
	public static void main(String[] args) {
		Calculator c = new Calculator();
		c.AcceptInput();
		c.SI_Calculate();
		c.Display();
	}
}
