import my.netology.basket.Basket;

import java.io.*;

public class Main {
    public static void saveBin(FileOutputStream file, Basket basket) {
        try(ObjectOutputStream oOS = new ObjectOutputStream(file)) {
            oOS.writeObject(basket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Basket loadFromBinFile(FileInputStream file) {
        try(ObjectInputStream oIS = new ObjectInputStream(file)) {
            return (Basket) oIS.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        double[] price = { 50.0, 14.0, 80.0, 34.53, 65.92 };
        String[] foodBasked = { "Молоко", "Хлеб", "Гречневая крупа", "Сыр", "Конфеты" };

        Basket basket = new Basket(price, foodBasked);
        FileOutputStream file = new FileOutputStream("basket_bin");
        saveBin(file, basket);//сохранили карзину

        FileInputStream iS = new FileInputStream("basket_bin");
        Basket basket1 = loadFromBinFile(iS);
        for (int i = 0; i < basket1.getPrice().length; i++) {
            basket1.getBasket()[i] = 2 * i + 1;
        }
        System.out.println(basket1);
        basket1.printCard();
    }
}