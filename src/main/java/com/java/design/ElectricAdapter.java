package com.java.design;

public class ElectricAdapter implements Motor {
    private ElectricMotor electricMotor;
    public ElectricAdapter(ElectricMotor electricMotor){
        this.electricMotor = electricMotor;
    }

    public void motor() {
        electricMotor.electricMotor();
    }

    public static void main(String[] args) {
        ElectricAdapter electricAdapter = new ElectricAdapter(new ElectricMotor());
        electricAdapter.motor();
    }
}
