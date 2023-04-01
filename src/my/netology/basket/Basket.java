package my.netology.basket;
import my.netology.rounding.Round;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Basket {
    private static List<Double> price = new ArrayList<>();
    private static List<String> foodName = new ArrayList<>();
    private int[] basket;

    public Basket(String pathFile, String format) throws FileNotFoundException {
        switch (format) {
            case "text":
                loadFromTxtFile(new File(pathFile));
                break;
            case "json":
                loadFromJSONFile(new File(pathFile));
                break;
            default:
                break;
        }
        this.basket = new int[foodName.size()];
    }

    public Basket(Double[] price, String[] foodName) {
        this.price.addAll(Arrays.asList(price));
        this.foodName.addAll(Arrays.asList(foodName));
        this.basket = new int[this.foodName.size()];
    }

    public List<Double> getPrice() {
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
                    foodName.add(resBasket[i]);
                } else {
                    price.add(Double.parseDouble(resBasket[i]));
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
                for (int i = 0; i < jsonObject.size() / 2; i++) {
                    foodName.add((String) jsonObject.get(i + "-text"));
                    price.add((double) jsonObject.get(i + "-double"));
                }
            } catch (ParseException | IOException e) {
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
                       ++count ,
                        foodName.get(i),
                        basket[i],
                        price.get(i),
                        Round.roundingTo((basket[i] * price.get(i)),
                                2));
                total += basket[i] * price.get(i);
            }
        }
        System.out.printf("%-50s %s\n", "ИТОГО: ", Round.roundingTo(total, 2));
    }

    public void saveTxt(String pathFile) throws IOException {
        try(PrintWriter pW = new PrintWriter(pathFile)) {
            pW.print("foodName:" + "=");//метка наименования продуктов
            for (int i = 0; i < foodName.size(); i++) {
                pW.print(foodName.get(i) + "=");
            }
            pW.print("price:" + "=");//метка цен
            for (int i = 0; i < price.size(); i++) {
                pW.print(price.get(i) + "=");
            }
            pW.flush();
        }
    }

    public void saveJSON(String pathFile) {
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < foodName.size(); i++) {
            jsonObject.put(i + "-text", foodName.get(i));
        }
        for (int i = 0; i < price.size(); i++) {
            jsonObject.put(i + "-double", price.get(i));
        }
        try (FileWriter fP = new FileWriter(new File(pathFile))) {
            fP.write(jsonObject.toJSONString());
            fP.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
