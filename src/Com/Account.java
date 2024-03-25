package Com;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;


public class Account implements Serializable {
    private  String  AccountNumber;
    private static Integer  Count=100;
    private String name;
    private Float Balance;
    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getBalance() {
        return Balance;
    }

    public void setBalance(Float balance) {
        Balance = balance;
    }



    public Account()
    {
        Count++;
        this.AccountNumber= "A"+Count;
    }
    public void CreateAccount()
    {
        java.util.Scanner sc= new java.util.Scanner(System.in);
        System.out.print("Enter Your Name: ");
        this.name= sc.nextLine();

        System.out.print("Enter Your Balance: ");
        this.Balance=sc.nextFloat();
    }

    public void StoreAccount(HashMap<String,Account> hm)
    {
        hm.put(this.AccountNumber,this);
    }

    public static void Run()
    {
        String AccountNumber;
        Account FoundAccount;

        /* Account Number: String */
        HashMap<String,Account> hm= new HashMap<>();



        /* Load Accounts From File in Hash Map */
        Account.LoadDataFromFile(hm);

        Scanner sc= new Scanner(System.in);
        Integer Choice=0;


        while(Choice != 6) {

            MainMenu.show();
            Choice = sc.nextInt();


            switch (Choice) {

                /* Create Account */
                case 1:
                    Account UserAccount= new Account();
                    try {
                        UserAccount.CreateAccount();
                        UserAccount.StoreAccount(hm);
                    }
                    catch(InputMismatchException e)
                    {
                        System.out.println("Input must be Float Value!!!");
                    }
                    break;

                /* Delete Account */
                case 2:

                    /* Take Account Number from user */
                    AccountNumber= Account.TakeAccountNumber("Enter Account Number you want to Delete: ");



                    /* Search on Account Number */
                    FoundAccount= Account.Search(hm,AccountNumber);

                    /* Check if account is Found or NOT */
                    if(FoundAccount != null)
                    {
                        hm.remove(FoundAccount.getAccountNumber());
                        System.out.println("Account is Removed...");
                    }
                    else
                    {
                        System.out.println("Account is Not Found!!!");
                    }
                    break;


                /* View Account */
                case 3:

                    /* Take Account Number from user */
                    AccountNumber= Account.TakeAccountNumber("Enter Account Number you want to View: ");

                    /* Search for Account Number */
                    FoundAccount= Account.Search(hm,AccountNumber);

                    /* Check if Account Found or NOt And Print Account  */
                    Account.PrintAccountNumberFound(FoundAccount);

                    break;


                /* View All Accounts */
                case 4:
                    Account.PrintAllAccounts(hm);
                    break;

                /* Save Accounts in File */
                case 5:
                    Account.SaveInFile(hm);
                    break;

                /* Exit */
                case 6:
                    break;


            }
        }
    }

    public static void SaveInFile(HashMap<String,Account> hm)
    {
       try(FileOutputStream fos= new FileOutputStream("Accounts.txt");
           ObjectOutputStream oos= new ObjectOutputStream(fos);)
       {
           oos.writeInt(hm.size());

               for(Map.Entry<String,Account> e: hm.entrySet())
               {
                   oos.writeObject(e.getValue());
               }

            oos.writeInt(Account.Count);


       }
       catch(Exception e)
       {
           System.out.println("File Not Found Exception");
       }

    }

    public static void LoadDataFromFile(HashMap<String,Account> hm)
    {
        try(FileInputStream fis= new FileInputStream("Accounts.txt");
            ObjectInputStream ois= new ObjectInputStream(fis);)
        {
            Integer Length= ois.readInt();
            for(Integer i=0; i<Length; i++)
            {
                Account account =(Account)ois.readObject();
                hm.put(account.AccountNumber,account);
            }

            Account.Count= ois.readInt();
        }
        catch(Exception e)
        {
            System.out.println("File Not Found Exception");
        }

    }
    public static Account Search(HashMap<String,Account> hm,String AccountNumber)
    {

        return hm.get(AccountNumber);
    }

    public static String TakeAccountNumber(String message)
    {
        if(message != null)
        {
            System.out.println(message);
        }
        Scanner sc= new Scanner(System.in);
        return sc.nextLine();

    }
    public static void PrintAccountNumberFound(Account FoundAccount)
    {

        if(FoundAccount != null)
        {
            System.out.println(FoundAccount);
        }
        else
        {
            System.out.println("Account is Not Found!!!");
        }


    }
    public static void PrintAllAccounts(HashMap<String,Account> hm)
    {
        hm.forEach((K,V)-> System.out.println(V));
    }

    @Override
    public String toString()
    {
        return " Name: "+ name+" , Balance: "+Balance;
    }

}
