package my.netology.basket;
import my.netology.ClientLog;
import my.netology.rounding.Round;
import netscape.javascript.JSObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class Basket extends ClientLog {
    private static double[] price;
    private static String[] foodName;
    private int[] basket;
    private File file;

    public Basket(String pathFile) throws FileNotFoundException {
        loadFromTxtFile(new File(pathFile));
        this.basket = new int[foodName.length];
    }

    public Basket(double[] price, String[] foodName) {
        this.price = price;
        this.foodName = foodName;
        this.basket = new int[foodName.length];
    }

    public double[] getPrice() {
        return price;
    }

    private static void loadFromTxtFile(File textFile) throws FileNotFoundException {
        if (textFile.exists()) {
            InputStreamReader iSR = new FileReader(textFile);
            StringBuilder strBuild = new StringBuilder();
            try(BufferedReader BuffR = new BufferedReader(iSR)){
                int intStr;
                while ((intStr = BuffR.read()) != -1) {
                    strBuild.append(Character.toChars(intStr));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String[] resBasket = strBuild.toString().split("=");
            boolean intStr = false; int count = 0;
            for (int i = 0; i < resBasket.length; i++) {
                if ("foodName:".equals(resBasket[i])) {
                    intStr = true; count = 0;
                    continue;
                } else if ("price:".equals(resBasket[i])) {
                    intStr = false; count = 0;
                    continue;
                }
                if (intStr) {
                    foodName[count] = resBasket[i];
                } else {
                    price[count] = Double.parseDouble(resBasket[i]);
                }
                count++;
            }
        } else throw new FileNotFoundException();
    }

    public void loadFromJSONFile(File jsonFile) {
        if (jsonFile.exists()) {
            JSONParser jsonParser = new JSONParser();
            try {
                Object obj = jsonParser.parse(new FileReader(jsonFile));
                JSONObject jsonObject = (JSONObject) obj;
                price[0] = (double) jsonObject.get("Молоко");
                price[1] = (double) jsonObject.get("Хлеб");
                price[2] = (double) jsonObject.get("Гречневая крупа");
                price[3] = (double) jsonObject.get("Сыр");
                price[4] = (double) jsonObject.get("Конфеты");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
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

    public void saveTxt(String pathFile) throws IOException {
        try(PrintWriter pW = new PrintWriter(pathFile)) {
            pW.print("foodName:" + "=");//метка наименования продуктов
            for (int i = 0; i < foodName.length; i++) {
                pW.print(foodName[i] + "=");
            }
            pW.print("price:" + "=");//метка цен
            for (int i = 0; i < price.length; i++) {
                pW.print(price[i] + "=");
            }
            pW.flush();
        }
    }

    public void saveJSON(String pathFile) {
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < foodName.length; i++) {
            jsonObject.put(foodName[i], price[i]);
        }
        try (FileWriter fP = new FileWriter(new File(pathFile))) {
            fP.write(jsonObject.toJSONString());
            fP.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
