package my.netology;

import com.opencsv.CSVWriter;
import my.netology.basket.Basket;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class ClientLog {
    private int productNum;
    private int amount;
    private List<String> listProduct = new ArrayList<>();

    public void log(int productNum, int amount) {
        listProduct.add(productNum + ", " + amount);
    }

    public void exportAsCSV(File txtFile) {
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(txtFile))){
            String[] str = "productNum,amount".split(",");
            csvWriter.writeNext(str);
            for (String s : listProduct) {
                csvWriter.writeNext(s.split(","));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
