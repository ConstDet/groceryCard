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
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        Basket basket;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse("shop.xml");
        Node root = doc.getDocumentElement();//корневой элемент
        System.out.println("Корневой элемент: " + root.getNodeName());
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            System.out.println("Текущий элемент: " + node.getNodeName());
            if (Node.TEXT_NODE != node.getNodeType()) {
                NodeList element = node.getChildNodes();//следующий элемент <load, save, log>
                switch (node.getNodeName()) {
                    case "load":
                        boolean enabled; String fileName = null; String format = null;
                        for (int t = 0; t < element.getLength(); t++) {
                            Node atribut = element.item(t);
                            if (Node.TEXT_NODE != atribut.getNodeType()) {//это атрибут
                                String f = atribut.getNodeName();
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
                        basket = new Basket(fileName, format);
                        for (int d = 0; d < basket.getPrice().length; i++) {
                            basket.addToCart(d, 2 * i + 1);//добавим продукт
                        }
                        basket.printCard();
                        break;
                    case "save":
                        break;
                    case "log":
                        break;
                    default:
                        break;
                }
            }
        }
        System.exit(0);

        /*double[] price = { 50.0, 14.0, 80.0, 34.53, 65.92 };
        String[] foodBasked = { "Молоко", "Хлеб", "Гречневая крупа", "Сыр", "Конфеты" };
        Basket basket = new Basket(price, foodBasked);
        for (int i = 0; i < price.length; i++) {
            basket.addToCart(i, 2 * i + 1);//добавим продукт
        }
        basket.printCard();//печать карзины
        basket.saveTxt("basket");

        Basket basket1 = new Basket("basket");
        ClientLog clientLog = new ClientLog();
        for (int i = 0; i < basket1.getPrice().length; i++) {
            basket1.addToCart(i, 3 * i + 1);
            clientLog.log(i, 3 * i + 1);
        }
        clientLog.exportAsCSV(new File("log.csv"));//сохранили лог
        basket1.saveJSON("basket.json");//записали карзину в json
        basket1.loadFromJSONFile(new File("basket.json"));//считали карзину
        basket1.printCard();//печать карзины*/

    }
}