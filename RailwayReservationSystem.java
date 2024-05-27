import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.util.Scanner;


public class RailwayReservationSystem {
	
	//global access variable 
	static String trainname_sql,destination_sql,departure_sql;
	
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int menu_choice;

        System.out.println("\t\t===============================================");
        System.out.println("\t\t                                              ");
        System.out.println("\t\t        -----------------------------         ");
        System.out.println("\t\t         Railway Reservation System           ");
        System.out.println("\t\t        -----------------------------         ");
        System.out.println("\t\t                                              ");
        System.out.println("\t\t                                              ");
        System.out.println("\t\t         Presented by:-                       ");
        System.out.println("\t\t              Parth Tagadpallewar             ");
        System.out.println("\t\t                                              ");
        System.out.println("\t\t===============================================");

        System.out.print("\n Press any key to continue: ");
       // sc.nextLine();
        sc.nextLine(); // This is to clear the newline character from the buffer.
       
        while (true) {
          
            System.out.println("\n=================================");
            System.out.println("    TRAIN RESERVATION SYSTEM");
            System.out.println("=================================");
            System.out.println("1 Reserve A Ticket");
            System.out.println("------------------------");
            System.out.println("2 View All Available Trains");
            System.out.println("------------------------");
            System.out.println("3 Exit");
            System.out.println("------------------------");
            System.out.print("\nEnter the option: ");

            menu_choice = sc.nextInt();
            sc.nextLine(); // Consume newline character.

            switch (menu_choice) {
                case 1:
                    reservation(sc);
                    break;
                case 2:
                    viewDetails();
                    System.out.print("\nPress Enter to go back to Main Menu...");
                    sc.nextLine(); // Wait for Enter key.
                    break;
                case 3:
                	System.out.println("\nHave a great day :)");
                    System.exit(0);
                default:
                    System.out.println("\nInvalid choice");
                    break;
            }
        }
    }

    public static void viewDetails() {
     
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("Tr.No\tName\t\t\tDestinations\t\tCharges\t\tTime");
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("1000\tKolhapur Express  \tPurna to Kolhapur  \tRs.5000\t\t6:00 am");
        System.out.println("1001\tDevgiri Express   \tNanded to Pune     \tRs.5000\t\t9:00 am");
        System.out.println("1002\tChennai Express   \tMumbai to Chennai  \tRs.4500\t\t12:00 pm");
        System.out.println("1003\tPune City Express \tNanded to Pune     \tRs.4500\t\t8:00 am");
        System.out.println("1004\tNandigram Express \tNanded to Mumbai   \tRs.4000\t\t11:00 am");
        System.out.println("1005\tHigh Court        \tManmad to Nanded   \tRs.4000\t\t7:00 am");
        System.out.println("1006\tSach khand Express\tNanded to Amrutsar \tRs.4000\t\t9.30 am");
        System.out.println("1007\tPanvel Express        \tNanded to Panvel   \tRs.3500\t\t1:00 pm");
        System.out.println("1008\tHaidrabad Express     \tNanded to Haidrabad\tRs.3500\t\t4:00 pm");
        System.out.println("1009\tNagarsol Express      \tNanded to Miami    \tRs.6000\t\t3.35 pm");
        System.out.println("1010\tLokmanya Tilak Express\tMumbai to Kolkatta \tRs.6000\t\t11.25 pm");
    }

    public static void reservation(Scanner sc) {
        char confirm;
        int train_num;
        float charges;

       
        System.out.print("\nEnter Your Name: ");    
        String name = sc.nextLine();

        System.out.print("\nEnter Number of seats: ");
        int num_of_seats = sc.nextInt();

        sc.nextLine(); // Consume newline character.

       
        viewDetails();
        System.out.print("\nEnter train number: ");
        train_num = sc.nextInt();

        charges = charge(train_num, num_of_seats);

        printTicket(name, num_of_seats, train_num, charges);

        System.out.print("\n\nConfirm Ticket (y/n): ");
        
        //even user enter yes it will just take first later as y
        confirm = sc.next().charAt(0);

                //user confirmation to book ticket
        if ( (confirm == 'y' || confirm == 'Y') && trainname_sql != "NOT FOUND"  ) {
        	insertDatabase(name, num_of_seats, train_num,trainname_sql,destination_sql,departure_sql ,charges);
            System.out.println("=============================");
            System.out.println("        Reservation Done     ");
            System.out.println("=============================");
            System.out.print("\n\nPress Enter to go back to Main Menu...");
            sc.nextLine(); // Consume newline character.
        } else {
            System.out.println("Reservation Not Done!");
            System.out.print("Press Enter to go back to Main Menu...");
            sc.nextLine(); // Consume newline character.
        }
    }

    
    //this function responsible for --> printing detail of ticket properly
    public static void printTicket(String name, int num_of_seats, int train_num, float charges) {
        
        System.out.println("    -------------------");
        System.out.println("\t       TICKET");
        System.out.println("    -------------------");
        System.out.println("Name:\t\t\t" + name);
        System.out.println("Number Of Seats:\t" + num_of_seats);
        System.out.println("Train Number:\t\t" + train_num);
        specificTrain(train_num);
        System.out.println("Charges:\t\t" + charges);
    }

    //  //this function responsible for --> search and print train detail based on train_number
    public static void specificTrain(int train_num) {
        String trainName = "NOT FOUND";
        String destination = "Not Available";
        String departure = "Not Available";

        switch (train_num) {
            case 1000:
                trainName = "Kolhapur Express";
                destination = "Purna to Kolhapur";
                departure = "09:00 AM";
                break;
            case 1001:
                trainName = "Devgiri Express";
                destination = "Nanded to Pune";
                departure = "12:00 PM";
                break;
            case 1002:
                trainName = "Chennai Express";
                destination = "Mumbai to Chennai";
                departure = "08:00 AM";
                break;
            case 1003:
                trainName = "Pune City Express";
                destination = "Nanded to Pune";
                departure = "03:30 PM";
                break;
            case 1004:
                trainName = "Nandigram Express";
                destination = "Nanded to Mumbai";
                departure = "07:00 AM";
                break;
            case 1005:
                trainName = "High Court";
                destination = "Manmad to Nanded";
                departure = "09:30 PM";
                break;
            case 1006:
                trainName = "Sach khand Express";
                destination = "Nanded to Amrutsar";
                departure = "01:00 PM";
                break;
            case 1007:
                trainName = "Panvel Express";
                destination = "Nanded to Panvel";
                departure = "04:00 PM";
                break;
            case 1008:
                trainName = "Haidrabad Express";
                destination = "Nanded to Haidrabad";
                departure = "03:35 PM";
                break;
            case 1009:
                trainName = "Nagarsol Express";
                destination = "Nanded to Miami";
                departure = "01:15 AM";
                break;
            case 1010:
                trainName = "Lokmanya Tilak Express";
                destination = "Mumbai to Kolkatta";
                departure = "11:25 PM";
                break;
        }
        trainname_sql=trainName;
        destination_sql=destination;
        departure_sql=departure;
        
        System.out.println("Train:\t\t\t" + trainName);
        System.out.println("Destination:\t\t" + destination);
        System.out.println("Departure:\t\t" + departure);
    }

    
    //this function responsible for --> calculate fare base on number of seat and fare
    public static float charge(int train_num, int num_of_seats) {
        switch (train_num) {
            case 1000:
            case 1001:
                return 5000.0f * num_of_seats;
            case 1002:
            case 1003:
                return 4500.0f * num_of_seats;
            case 1004:
            case 1005:
            case 1006:
                return 4000.0f * num_of_seats;
            case 1007:
            case 1008:
                return 3500.0f * num_of_seats;
            case 1009:
            case 1010:
                return 6000.0f * num_of_seats;
            default:
                return 0.0f;
        }
    }

    
    //this function is responsible for entering data into database
    public static void insertDatabase(String name, int num_of_seats, int train_num,String trainname_sql ,String destination_sql , String departure_sql, float charges) {
    	 try {
             Class.forName("com.mysql.cj.jdbc.Driver");
             
             //connection string/url of mysql
             Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reservation", "root", "" );
          
            
             // Define the SQL INSERT statement
             String sql = "INSERT INTO ticket (passenger_name, seat, train_number, train_name, destination, train_departure, total_fare) VALUES (?, ?, ?, ?, ?, ?,?)";

             // Create a PreparedStatement to execute the query
             PreparedStatement preparedStatement = connection.prepareStatement(sql);

             
          // Set values for the parameters in the SQL statement
             preparedStatement.setString(1, name);
             preparedStatement.setInt(2, num_of_seats);
             preparedStatement.setInt(3, train_num);
             preparedStatement.setString(4, trainname_sql);
             preparedStatement.setString(5, destination_sql);
             preparedStatement.setString(6,departure_sql);
             preparedStatement.setFloat(7,charges);
             
             
          // Execute the INSERT statement
             int rowsAffected = preparedStatement.executeUpdate();

             if (rowsAffected > 0) {
                 System.out.println("\nData inserted successfully.");
             } else {
                 System.out.println("Data insertion failed.");
             }
             
             connection.close();
             //to continue execution any error occoured in sql program
         } catch (Exception e) {
             System.out.println(e);
         }
    	
    	
    }
    
    
    
    
}
