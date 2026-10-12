import java.util.Random;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        System.out.println("WELCOME TO TREN STORE! ");

        System.out.print("\nInput your name : ");
        String name = scanner.nextLine();

        System.out.print("What item do you want to buy? : ");
        String item = scanner.nextLine();

        System.out.print("How much do you want to buy? : ");
        int quant = scanner.nextInt();

        double price = 21.9;
        
        System.out.print("\nYour name is : " + name);
        System.out.println("\nThe item that you want to buy is : " + item);
        System.out.println("The quantity of the item is : " + quant);
        System.out.println("Your total price is : Rp." + quant*price + "!");
        System.out.print("\nDo you want a bonus item? [Y/N] : ");
        boolean bonus = scanner.nextBoolean();

        if(bonus){
            int number = random.nextInt(2,5);
            System.out.println("Congrats you win "+ number + " amount of " + item + "'s!");
        }
        else{
            System.out.println("Thankyou for buying in our store!");
        }
        
        scanner.close();
    }
}