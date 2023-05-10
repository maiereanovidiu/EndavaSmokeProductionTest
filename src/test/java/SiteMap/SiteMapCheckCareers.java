package SiteMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.concurrent.TimeUnit;

class CareersSiteMap {

    public static class SiteMap {

        WebDriver driver;

        @BeforeMethod
        public void beforeMethod() throws IOException, InterruptedException {
            driver =  DriverConfig.initializeDriver();
            Thread.sleep(1500);
        }

        @BeforeTest
        public static Document targetDocument() throws IOException, SAXException, ParserConfigurationException {

            String downloadDirectory = System.getProperty("user.dir") + "\\src\\test\\resources\\Production_SiteMapCareers.xml";
            InputStream in = new URL("https://careers.endava.com/en/sitemapcareers.xml").openStream();
            Files.copy(in, Paths.get(downloadDirectory), StandardCopyOption.REPLACE_EXISTING);
            InputStream inputStream = EndavaSiteMap.class.getResourceAsStream("/Production_SiteMapCareers.xml");
            assert inputStream != null;
            DocumentBuilderFactory m = DocumentBuilderFactory.newInstance();
            DocumentBuilder v = m.newDocumentBuilder();
            return v.parse(inputStream);
        }

        @Test
        public void SiteMapCareers()
                throws ParserConfigurationException, IOException, SAXException, InterruptedException {

            Document doc = targetDocument();
            NodeList nodeList = doc.getElementsByTagName("url");
            SoftAssert soft = new SoftAssert();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String urlString = element.getElementsByTagName("loc").item(0).getTextContent();
                    driver.get(urlString);
                    Thread.sleep(1500);
                    HttpURLConnection conn = (HttpURLConnection) new URL(urlString).openConnection();
                    conn.setRequestMethod("HEAD");
                    conn.connect();
                    int responseCode = conn.getResponseCode();
//                    System.out.println("URL is :" + element.getElementsByTagName("loc").item(0).getTextContent());
                    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
                    if (!driver.findElements(By.id("onetrust-accept-btn-handler")).isEmpty()) {
                        driver.findElement(By.id("onetrust-accept-btn-handler")).click();
                    }
                    if (responseCode < 400 & !driver.getTitle().contains("Maintenance")) {
                        System.out.println("Page " + driver.getCurrentUrl() + " it is OK");
                        List<WebElement> headerLogo = driver
                                .findElements(By.xpath("//header/div[1]/div[1]/div[1]/span[1]/a[1]"));
                        soft.assertTrue(!headerLogo.isEmpty(),"For page : " + driver.getCurrentUrl()
                                + " => Header Logo is missing or is not according to design !!!");
                        List<WebElement> languageSelector = driver.findElements(By.id("selected-lang"));
                        soft.assertTrue(!languageSelector.isEmpty(),"For page : " + driver.getCurrentUrl()
                                + " => Language selector is missing or is NOT according to design !!!");
                        List<WebElement> burgerMenu = driver
                                .findElements(By.xpath("//header/div[1]/div[1]/div[2]/div[1]/a[1]/span[1]"));
                        soft.assertTrue(!burgerMenu.isEmpty(),"For page : " + driver.getCurrentUrl()
                                + " => Burger Menu icon is missing or is NOT according to design !!!");
                        List<WebElement> contactIcons = driver
                                .findElements(By.xpath("//body/div[@id='contact-buttons']/ul[1]/li"));
                        soft.assertTrue(contactIcons.size() == 3,"For page : " + driver.getCurrentUrl()
                                + " => Contact Icons are missing or ar NOT according to design !!!");
                        List<WebElement> footerMenu = driver
                                .findElements(By.xpath("//body/footer[@id='footer']/section[1]/div[1]/div"));
                        soft.assertTrue(!footerMenu.isEmpty(),"For page : " + driver.getCurrentUrl()
                                + " => Footer menu is missing or is NOT according to design !!!");
                        List<WebElement> footerLogo = driver
                                .findElements(By.xpath("//body[1]/footer[1]/section[2]/div[1]/div[1]/div[2]/a[1]"));
                        soft.assertTrue(!footerLogo.isEmpty(),"For page : " + driver.getCurrentUrl()
                                + " => Footer Logo is missing or is NOT according to design !!!");
                        List<WebElement> copyRights = driver
                                .findElements(By.xpath("//body/footer[@id='footer']/section[2]/div[1]/div[1]/div[3]"));
                        soft.assertTrue(!copyRights.isEmpty(),"For page : " + driver.getCurrentUrl()
                                + " => Endava Copy Rights text is missing or is NOT according to design !!!");
                        List<WebElement> footerSocialMedia = driver
                                .findElements(By.xpath("//body/footer[@id='footer']/section[2]/div[1]/div[1]/div[1]"));
                        soft.assertTrue(!footerSocialMedia.isEmpty(),"For page : " + driver.getCurrentUrl()
                                + " => Social Media buttons from footer page are missing or ar NOT according to design !!!");
                    } else {
                        System.out
                                .println("Page " + driver.getCurrentUrl() + " is NOT loading or is in Maintenance !!!");
                    }
                }
            }
            soft.assertAll();
        }
        @AfterMethod
        public void closeDriver() {

            driver.quit();
        }
    }
}