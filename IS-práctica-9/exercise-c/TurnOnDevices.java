interface Connectable {
    public String turnOn();
    public String turnOff();
    public boolean isOn();
}
class Computer implements Connectable{
    private boolean on = false;
    @Override
    public boolean isOn() {
        return on;
    }
    @Override
    public String turnOn() {
        on = true;
        return "Computer is ON.";
    }
    @Override
    public String turnOff() {
        on = false;
        return "Computer is OFF.";
    }

}

class Lamp implements Connectable{
    private boolean on = false;
    @Override
    public boolean isOn() {
        return on;
    }
    @Override
    public String turnOn() {
        on = true;
        return "Lamp is ON.";
    }
    @Override
    public String turnOff() {
        on = false;
        return "Lamp is OFF.";
    }

}

class CoffeeMaker {
    private boolean off = true;
    public boolean isOff() {
        return off;
    }
    public void on(){
        off = false;
    }
    public void off(){
        off = true;
    }
}

class CoffeeMakerAdaptor implements Connectable{
    private CoffeeMaker coffeeMaker = new CoffeeMaker();

    CoffeeMakerAdaptor(CoffeeMaker coffeeMaker){
        this.coffeeMaker = coffeeMaker;
    }
    public boolean isOn() {
        return ! coffeeMaker.isOff();
    }

    public String turnOn() {
        coffeeMaker.on();
        return "CoffeeMaker is ON.";
    }

    public String turnOff() {
        coffeeMaker.off();
        return "CoffeeMaker is OFF.";
    }

}

public class TurnOnDevices {
    public static void main(String[] args) {
        turnOnDevices(new Lamp());
        turnOnDevices(new Computer());
        turnOnDevices(new CoffeeMakerAdaptor(new CoffeeMaker()));
 
    }
 
    private static void turnOnDevices(Connectable device) {
        
        System.out.println(device.turnOn());
    }
}
