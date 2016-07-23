package com.example.amrarafa.numberfacts;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by amr arafa on 7/21/2016.
 */
public class ColorWheel {

    String [] colors={"#39add1",
            "#3079ab",
            "#c25975",
            "#e15258",
            "#f9845b",
            "#53bbb4"};

    public int getcolor()
    {
        String color=" ";

        Random randomGenerator= new Random();

        int randomNumber = randomGenerator.nextInt(colors.length);

        color=colors[randomNumber];
        int  colorasint= Color.parseColor(color);

        return colorasint;
    }

}


