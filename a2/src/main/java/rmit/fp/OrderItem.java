package rmit.fp;
//class for order item details (stores the details from the cart)
public class OrderItem {
    private int OrderItemId;
    private int OrderId;
    private int EventId;
    private String EventName;
    private String Venue;
    private String Day;
    private double Price;
    private int TicketQuantity;

    public OrderItem(int orderItemId, int orderId, int eventId, String eventName, String venue, String day, double price, int ticketQuantity) {
        this.OrderItemId = orderItemId;
        this.OrderId = orderId;
        this.EventId = eventId;
        this.EventName = eventName;
        this.Venue = venue;
        this.Day = day;
        this.Price = price;
        this.TicketQuantity = ticketQuantity;
    }
    public int getOrderItemId() {
        return OrderItemId;
    }
    public int getOrderId() {
        return OrderId;
    }
    public int getEventId() {
        return EventId;
    }
    public String getEventName() {
        return EventName;
    }
    public String getVenue() {
        return Venue;
    }
    public String getDay() {
        return Day;
    }
    public double getPrice() {
        return Price;
    }
    public int getTicketQuantity() {
        return TicketQuantity;
    }
    
}
