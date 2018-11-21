package alex.tw.posdemo;

public class DataModel {

    String name;
    int size;
    int sugar;
    int temp;
    int quantity;

    public DataModel(String name, int size, int sugar, int temp) {
        this.name=name;
        this.size=size;
        this.sugar=sugar;
        this.temp=temp;

    }

    public DataModel(String name, int size, int sugar, int temp, int quantity) {
        this.name=name;
        this.size=size;
        this.sugar=sugar;
        this.temp=temp;
        this.quantity=quantity;

    }



    public String getName() {
        return name;
    }



    public int getSize() {
        return size;
    }

    public int getSugar() {
        return sugar;
    }

    public int getTemp() {
        return temp;
    }

    public int getQuantity() { return quantity;}

}
