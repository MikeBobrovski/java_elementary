package HW7;

public class Car {

    private int ID;

    private String manufacturer;
    private String model;
    private int year;
    private String color;
    private boolean fuel;
    private int price;

    public Car(String manufacturer, String model, int year, String color, boolean fuel, int price) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.year = year;
        this.color = color;
        this.fuel = fuel;
        this.price = price;
        //this.ID = ID;
    }

    @Override
    public String toString(){
        String res = "";
        res = ID + ", "
                + manufacturer + ", "
                + model + ", "
                + year + ", "
                + color + ", "
                + ((fuel) ? "petrol" : "diesel fuel") + ", "
                + price;

        return res;
    }

    @Override//если не переопределить, то приедет из Обжетка, который проверит идентичность
    public boolean equals(Object obj) {
        if (obj instanceof Car) { //без этой проверки исключение "животное не кастуется к машине"
            Car other = (Car) obj;
            boolean eq = other.fuel == this.fuel
                    && other.manufacturer == this.manufacturer
                    && other.model == this.model
                    && other.year == this.year
                    && other.color == this.color
                    && other.price == this.price;
            return eq;
        } else return false;
    }


}
