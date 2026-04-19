package model;
import enums.PaymentMethod;
import interfaces.Payable;
import java.time.LocalDate;

public class Invoice implements Payable {
    private LocalDate paymentDate ;
    private double totalAmount;
    private PaymentMethod paymentMethod;

    public static void printTotalAmount(double totalAmount)
        {   
            System.out.println("Total amount: " + totalAmount);
        }

    public void pay(double amount)
        {   
            if (amount >= totalAmount){
                paymentDate = LocalDate.now();
                System.out.println("Payment successful" );}
                else
                  {  System.out.println("Payment failed" ) ;}

        }

    }
