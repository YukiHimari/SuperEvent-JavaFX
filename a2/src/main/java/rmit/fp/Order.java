package rmit.fp;

public class Order {

        private int orderNumber;
        private String username;
        private String OrderDateTime;
        private double totalPrice;
        public Order(int orderNumber, String username, String OrderDateTime, double totalPrice) {
            this.orderNumber = orderNumber;
            this.username = username;
            this.OrderDateTime = OrderDateTime;
            this.totalPrice = totalPrice;
        }


        public int getOrderNumber() {
            return orderNumber;
        }
        public String getUsername() {
            return username;
        }
        public String getOrderDateTime() {
            return OrderDateTime;
        }
        public double getTotalPrice() {
            return totalPrice;
        }
    
}
