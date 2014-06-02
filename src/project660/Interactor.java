package project660;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Interactor 
{
    public static String getString(String message) throws IOException
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        System.out.print(message);
        return in.readLine();
    }
    
    public static int getInt(String message) throws IOException
    {
        int n = -1;
        
        while (n < 0) {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
            System.out.print(message);
            try {
                n = Integer.parseInt(in.readLine());
            } catch (NumberFormatException e) {
                System.out.print("\n   Please type a numeric value!\n");
            }
        }
        
        return n;
    }
    
    public static double getDouble(String message) throws IOException
    {
        double n = -1;
        
        while (n < 0) {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
            System.out.print(message);
            try {
                n = Double.parseDouble(in.readLine());
            } catch (NumberFormatException e) {
                System.out.print("\n   Please type a numeric value!\n");
            }
        }
        
        return n;
    }
    
}
