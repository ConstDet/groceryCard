import my.netology.basket.Basket;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        double[] price = { 50.0, 14.0, 80.0, 34.53, 65.92 };
        String[] foodBasked = { "Молоко", "Хлеб", "Гречневая крупа", "Сыр", "Конфеты" };
        Basket basket = new Basket(price, foodBasked);
        for (int i = 0; i < price.length; i++) {
            basket.addToCart(i, 2 * i + 1);//добавим продукт
        }
        basket.printCard();//печать карзины
        basket.saveTxt("basket");
        Basket basket1 = new Basket("basket");
        for (int i = 0; i < basket1.getPrice().length; i++) {
            basket1.addToCart(i, 3 * i + 1);
        }
        basket1.printCard();//печать карзины
    }
}