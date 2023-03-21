import my.netology.rounding.Round;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //массив продуктовой корзины
        String[] foodBasked = { "Молоко", "Хлеб", "Гречневая крупа", "Сыр", "Конфеты" };
        //массив цен
        double[] price = { 50, 14, 80, 34.53, 65.92 };
        //выведем корзину на экран
        for (int i = 0; i < foodBasked.length; i++) {
            System.out.println((i + 1) + ". " + foodBasked[i] + " " + price[i] + " руб/шт");
        }
        int[] sum = new int[5]; //заполним нулям
        final String INDICATION = "\"номер товара [пробел] количество\"";
        while (true) {
            System.out.println("Выберите " + INDICATION + " или введите \"end\"");
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                break;
            }
            String[] parts = input.split(" ");
            //проверка на правильность ввода пользователем
            if (parts.length != 2) {
                System.out.println("Ошибка ввода! Требуется: " + INDICATION + ". Было введено: " + input);
                continue;
            }
            int position = 0, amount = 0;
            try {
                position = Integer.parseInt(parts[0]);
                amount = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка ввода формата числа! " + e);
                System.out.println("Требуется: положительное целое");
                System.out.println("Было введено: " + input);
                continue;
            }
                //проверка на правильность значений
                if (position > foodBasked.length || position <= 0 || amount < 0) {
                    System.out.println("Ошибка ввода! Требуется для номера товара: число от 1 до " + foodBasked.length
                            + ". Для количества положительное число." +
                            " Было введено: " + input);
                    continue;
                }
                sum[position - 1] += amount;
        }
        //вышли из цикла
        double total = 0;
        System.out.println("Ваша корзина:");
        for (int i = 0; i < sum.length; i++) {
            if (sum[i] > 0){
                System.out.println(foodBasked[i] +
                        " " + sum[i] +
                        " шт " + price[i] +
                        " руб/шт " +
                        Round.roundingTo((sum[i] * price[i]), 2) +
                        " руб в сумме");
                total += sum[i] * price[i];
            }
        }
        System.out.println("ИТОГО: " + Round.roundingTo(total, 2) + " руб.");
        System.out.println("Программа завершена!");
    }
}