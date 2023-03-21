package my.netology.basket;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Basket {
    double[] price;
    String[] foodName;

    public Basket(double[] price, String[] foodName) {
        this.price = Arrays.copyOf(price, price.length);
        this.foodName = Arrays.copyOf(foodName, foodName.length);
    }

    public void addToCart(int productNum, int amount) {

    }
}
