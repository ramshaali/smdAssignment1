public class itemcard {


        private int imageResourceId;
        private String itemName;
        private String price;
        private int views;
        private String date;




    public itemcard(int imageResourceId, String itemName, String price, int views, String date) {
        this.imageResourceId = imageResourceId;
        this.itemName = itemName;
        this.price = price;
        this.views = views;
        this.date = date;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
