package org.example.entity;

import lombok.Data;

import java.util.Arrays;
//import lombok.ToString;

@Data
public class SampleItem {
    private String input;
    private String output;

    public String[] getProgramArgs(){
//        System.out.println(Arrays.toString(input.split(" ")));
        return input.split(" ");
    }
    @Override
    public String toString() {
        return "Sample{\n" +
                "input=" + getInput() +
                "\noutput='" + getOutput() + '\'' +
                '}';
    }
}
