package pl.first.firstjava;

import java.util.ListResourceBundle;

public class Authors extends ListResourceBundle {

    /*------------------------ METHODS REGION ------------------------*/
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"1. ", "Michał Glapiński ",},
                {"2. ", "Kacper Bednarski "}
        };
    }
}
