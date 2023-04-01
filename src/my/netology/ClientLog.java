package my.netology;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientLog {
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
