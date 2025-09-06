package rmit.fp;


import java.io.FileReader;
import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;

//class to store the event details
public class Event{
    private int id;
    private String name;
    private String venue;
    private String day;
    private double price;
    private int soldTickets;
    private int totalTickets;



    //constructor for Event class - with id for Database Loaded Events

    public Event(int id, String name, String venue, String day, double price, int soldTickets, int totalTickets) {
        this.id = id;
        this.name = name;
        this.venue = venue;
        this.day = day;
        this.price = price;
        this.soldTickets = soldTickets;
        this.totalTickets = totalTickets;
    }

    // Constructor for Event class - without id for new events
    public Event(String name, String venue, String day, double price, int soldTickets, int totalTickets) {
        this.name = name;
        this.venue = venue;
        this.day = day;
        this.price = price;
        this.soldTickets = soldTickets;
        this.totalTickets = totalTickets;
    }

    public String getName() {
        return name;
    }

    public String getVenue() {
        return venue;
    }

    public String getDay() {
        return day;
    }

    public double getPrice() {
        return price;
    }
    public int getSoldTickets() {
        return soldTickets;
    }
    public int getTotalTickets() {
        return totalTickets;
    }
    public void setSoldTickets(int soldTickets) {
        this.soldTickets = soldTickets;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    //Method to read events from a  text file
public static List<Event> getEvents(){
    List<Event> events = new ArrayList<>();
    try  {
        FileReader in = new FileReader("events.dat");
        BufferedReader reader = new BufferedReader(in);
        String line = reader.readLine();
        while (line != null) {
            String[] eventParts = line.split(";");
            if (eventParts.length == 6) {
                String name = eventParts[0].trim();
                String venue = eventParts[1].trim();
                String day = eventParts[2].trim();
                double price = Double.parseDouble(eventParts[3].trim());
                int soldTickets = Integer.parseInt(eventParts[4].trim());
                int totalTickets = Integer.parseInt(eventParts[5].trim());

                Event event = new Event(name, venue, day, price, soldTickets, totalTickets);
                events.add(event);
            }
            line = reader.readLine();
        }
        reader.close();
        // return events;
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error reading events file: " + e.getMessage());
    }
     return events;
}
    
}
