package my.netology.basket;
import my.netology.rounding.Round;
import java.io.*;
import java.util.Arrays;

public class Basket implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private double[] price;
    private String[] foodName;
    private int[] basket;

    public Basket(double[] price, String[] foodName) {
        this.price = price;
        this.foodName = foodName;
        this.basket = new int[foodName.length];
    }

    public double[] getPrice() {
        return price;
    }

    public int[] getBasket() {
        return basket;
    }

    public void addToCart(int productNum, int amount) {
        basket[productNum] += amount;
    }

    public void printCard() {
        double total = 0;
        int count = 0;
        System.out.println("Ваша корзина:");
        System.out.printf("%-5s %-20s %-10s %-12s %-12s\n",
                "п.н.", "Наименование товара", "Кол-во", "Цена (руб.)", "Сумма (руб.)");
        for (int i = 0; i < basket.length; i++) {
            if (basket[i] > 0) {
                System.out.printf("%-5s %-20s %-10s %-12s %-12s\n",
                       ++count , foodName[i], basket[i], price[i], Round.roundingTo((basket[i] * price[i]), 2));
                total += basket[i] * price[i];
            }
        }
        System.out.printf("%-50s %s\n", "ИТОГО: ", Round.roundingTo(total, 2));
    }

    @Override
    public String toString() {
        return "Basket{\n" +
                "foodName: " + Arrays.toString(foodName) + "\n" +
                "price: " + Arrays.toString(price) + "\n" +
                "basket: " + Arrays.toString(basket) +
                '}';
    }
}
