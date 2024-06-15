package com.semicolon.africa.Estore.utils;

import java.util.Random;
import java.util.Scanner;

public class Example {
    public static void main(String[] args) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        int computerValue = random.nextInt(0,3) ;
        String result = "";

        System.out.println("Enter a Number");
           int userInput =  scanner.nextInt();
        System.out.println(computerValue);
       if(computerValue == 0 && userInput == 0){
           System.out.println("Computer is Scissors. You are Scissors. It a draw");
       }
       if(computerValue == 2 && userInput == 2){
           System.out.println("Computer is Paper. You are paper. It a draw");
       }
       if(computerValue == 1 && userInput == 1){
           System.out.println("Computer is Rock. You are Rock. It a draw");
       }
       if(computerValue==1 && userInput == 0){
           System.out.println("Computer is Rock. You are Scissors. You Lose");
       }
       if(computerValue==0 && userInput == 1){
           System.out.println("Computer is Scissors. You are Rock. You Win");
       }
       if(computerValue==2 && userInput == 1){
           System.out.println("Computer is Paper. You are Rock. You Loose");
       }
       if(computerValue==1 && userInput == 2){
           System.out.println("Computer is Rock. You are Paper. You Win");
       }
       if(computerValue==0 && userInput == 2){
           System.out.println("Computer is Scissors. You are Paper. You Lose");
       }
       if(computerValue==2 && userInput == 0){
           System.out.println("Computer is Paper. You are Scissors. You Win");
       }
    }
}
