package rmit.fp;
//class to store the cart items
public class Cart {
    private int eventId;
    private String eventName;
    private String venue;
    private String day;
    private double price;
    private int ticketQuantity;
    private double calcPrice;
    private String username;


    public Cart(int eventId,String eventName, String venue, String day, double price, int ticketQuantity, double calcPrice, String username) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.venue = venue;
        this.day = day;
        this.price = price;
        this.ticketQuantity = ticketQuantity;
        this.calcPrice = calcPrice;
        this.username = username;
    }

    public String getEventName() {
        return eventName;
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
    public int getTicketQuantity() {
        return ticketQuantity;
    }
    public double getCalcPrice() {
        return calcPrice;
    }
    public void setCalcPrice(double calcPrice) {
        this.calcPrice = calcPrice;
    }

    public void setTicketQuantity(int ticketQuantity) {
        this.ticketQuantity = ticketQuantity;
    }

    // to get the event object from the cart
    private Event event;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
    
    public int getEventId() { 
        return eventId;
     }
}
