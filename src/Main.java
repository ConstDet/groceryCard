import my.netology.ClientLog;
import my.netology.basket.Basket;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

public class Main {
    private static boolean enabled = false;
    private static String fileName = null;
    private static String format = null;

    private static void getAttribute (NodeList element) {
        for (int t = 0; t < element.getLength(); t++) {
            Node atribut = element.item(t);
            if (Node.TEXT_NODE != atribut.getNodeType()) {//это атрибут
                switch (atribut.getNodeName()) {
                    case "enabled":
                        enabled = "true".equals(atribut.getChildNodes().item(0).getTextContent());
                        break;
                    case "fileName":
                        fileName = atribut.getChildNodes().item(0).getTextContent();
                        break;
                    case "format":
                        format = atribut.getChildNodes().item(0).getTextContent();
                        break;
                    default:
                        break;
                }
            }
        }
    }
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        Basket basket = null;
        ClientLog clientLog = new ClientLog();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse("shop.xml");
        Node root = doc.getDocumentElement();//корневой узел
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            //текущий узел
            if (Node.TEXT_NODE != node.getNodeType()) {
                NodeList element = node.getChildNodes();//следующий узел <load, save, log>
                switch (node.getNodeName()) {
                    case "load":
                        getAttribute(element);//получить атрибуты
                        if (enabled) {
                            basket = new Basket(fileName, format);
                            System.out.printf("Корзина загружена из %s\n", fileName);
                        } else {
                            Double[] price = { 50.0, 14.0, 80.0, 34.53, 65.92 };
                            String[] foodBasket = { "Молоко", "Хлеб", "Гречневая крупа", "Сыр", "Конфеты" };
                            basket = new Basket(price, foodBasket);
                            System.out.println("Корзина загружена из массивов.");
                        }
                        for (int d = 0; d < basket.getPrice().size(); d++) {
                            basket.addToCart(d, 2 * d + 1);//добавим продукт
                            clientLog.log(d, 2 * d + 1);
                        }
                        basket.printCard();
                        break;
                    case "save":
                        for (int d = 0; d < basket.getPrice().size(); d++) {
                            basket.addToCart(d, 3 * d + 1);//добавим продукт
                            clientLog.log(d, 3 * d + 1);
                        }
                        basket.printCard();
                        getAttribute(element);//получить атрибуты
                        if (enabled) {
                            switch (format) {
                                case "json":
                                    basket.saveJSON(fileName);
                                    break;
                                case "text":
                                    basket.saveTxt(fileName);
                                    break;
                                default:
                                    break;
                            }
                            System.out.printf("Корзина сохранена в %s\n", fileName);
                        } else {
                            System.out.printf("Корзина не сохранена.");
                        }
                        break;
                    case "log":
                        getAttribute(element);//получить атрибуты
                        if (enabled) {
                            clientLog.exportAsCSV(new File(fileName));//сохранили лог
                            System.out.printf("Log сохранен в %s\n", fileName);
                        } else {
                            System.out.printf("Log-файл не сохранен.");
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }
}