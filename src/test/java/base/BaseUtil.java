package base;

import Util.WaitService;
import driver.Driver;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;


public class BaseUtil {

    public static WebDriver getWebDriver() {
        return Driver.webDriver;
    }

    WaitService waitService = new WaitService();

    Logger logger = LoggerFactory.getLogger(getClass());


    public void maximizeOfPageUtil() {
        JavascriptExecutor js = (JavascriptExecutor) getWebDriver();
        getWebDriver().manage().window().maximize();
        // js.executeScript("window.scrollBy(0,1000)");
    }

    public WebElement findElementByCss(String css) {
        WebElement webElement = waitService.waitUntilElementToBePresence(By.cssSelector(css));
        logger.info("Element başarılı bir şekilde bulundu!");
        return webElement;
    }

    public WebElement findElementById(String id) {
        waitService.waitUntilElementToBeVisible(By.id(id));
        WebElement webElement = waitService.waitUntilElementToBePresence(By.id(id));
        logger.info("Element başarılı bir şekilde bulundu!");
        return webElement;
    }

    public WebElement findElementByXpath(String xpath) {
        waitService.waitUntilElementToLoadPage();
        WebElement webElement = waitService.waitUntilElementToBePresence(By.xpath(xpath));
        logger.info("Element başarılı bir şekilde bulundu!");
        return webElement;
    }

    public List<WebElement> findElementsByXpath(String xpath) {
        waitService.waitUntilElementToLoadPage();
        List<WebElement> webElement = waitService.waitUntilElementsToBePresence(By.xpath(xpath));
        logger.info("Element başarılı bir şekilde bulundu!");
        return webElement;
    }

    public List<WebElement> findElementsByClassName(String className) {
        waitService.waitUntilElementToLoadPage();
        List<WebElement> webElement = waitService.waitUntilElementsToBePresence(By.className(className));
        logger.info("Element başarılı bir şekilde bulundu!");
        return webElement;
    }

    public List<WebElement> findElementsByCss(String css) {
        waitService.waitUntilElementToLoadPage();
        List<WebElement> webElement = waitService.waitUntilElementsToBePresence(By.cssSelector(css));
        logger.info("Element başarılı bir şekilde bulundu!");
        return webElement;
    }

    public WebElement findElementByClassName(String className) {

        WebElement webElement = waitService.waitUntilElementToBePresence(By.className(className));
        logger.info("Element başarılı bir şekilde bulundu.");
        return webElement;
    }

    public void sendKeyById(String id, String text) {
        findElementById(id).sendKeys(text);
    }

    public void sendKeyByCss(String css, String text) {
        findElementByCss(css).sendKeys(text);
    }

    public void sendKeyByXPath(String XPath, String text) {
        findElementByXpath(XPath).sendKeys(text);
    }

    public void sendKeyByClassName(String Class, String text) {
        findElementByClassName(Class).sendKeys(text);
    }

    private JavascriptExecutor getJSExecutor() {
        return (JavascriptExecutor) getWebDriver();
    }

    private Object executeJS(String script, boolean wait) {
        return wait ? getJSExecutor().executeScript(script, "") : getJSExecutor().executeAsyncScript(script, "");
    }

    public void scrollToByXpathElementClick(String xpath) {
        Point location = findElementByXpath(xpath).getLocation();
        scroolToElement(location.getX(), location.getY());
        findElementByXpath(xpath).click();
    }

    public void scrollToByCssElementClick(String css) {
        Point location = findElementByCss(css).getLocation();
        scroolToElement(location.getX(), location.getY());
        findElementByCss(css).click();
    }

    public void scroolToElement(int locX, int locY) {
        String script = String.format("window.scrollTo(%d, %d);", locX, locY);
        executeJS(script, true);
    }

    public void addtoProductBasket(String className, int key) {
        for (int i = 0; i < key; i++) {
            findElementByClassName(className).click();
        }
    }

    Double fiyat = 0.0, totalPrice;

    public Double getBasketAllPrice() throws InterruptedException {
        List<WebElement> elementsByXpath = findElementsByXpath("//div[@class='product-prices-utils']/div[@class='price']");

        for (int i = 0; i < elementsByXpath.size(); i++) {
            fiyat += Double.valueOf(elementsByXpath.get(i).getText().split(" ")[0].replace(",", ".").trim());
            Thread.sleep(1000);
        }
        return fiyat;
    }

    public Double getOrderSummaryTotalPrice() {
        WebElement elementsByXpath = findElementByXpath("//span[contains(@data-bind,'text: totalPrice')]");
        totalPrice = Double.valueOf(elementsByXpath.getText().split(" ")[0].replace(",", ".").trim());
        return totalPrice;
    }

    public void basketPriceControl() {
        if (fiyat == totalPrice) {
            logger.info("Fiyat doğru");
        } else {
            logger.error("Fiyat hatalı");
        }
    }

    public void clearBasket() {
        List<WebElement> elementsByXpath = findElementsByXpath("//*[contains(@class,'btn-delete')]");
        for (int i = 0; i < elementsByXpath.size(); i++) {
            WebElement element = waitService.waitUntilElementToBePresence(By.linkText("Sil"));
            element.click();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //  findElementByXpath("//*[contains(text(),'Sil')]").click();
        }
    }

    public void basketControl() {
        if (findElementByXpath("//div[@class='empty-cart-icon-container']") != null) {
            logger.error("Sepetiniz boş değil.");
        }
    }

    public void deleteAllCookies() {
        //getWebDriver().navigate().to("https://www.hepsiburada.com/");
        getWebDriver().manage().deleteAllCookies();
    }

    public void randomProductChoice() {
        int ProductSize = findElementsByCss(".box.product").size();
        Random random = new Random();
        int randomProductNumber = random.nextInt(ProductSize - 1);
        waitService.waitUntilElementsToBePresence(By.cssSelector(".box.product"));
        WebElement webElement = findElementsByCss(".box.product").get(randomProductNumber);
        scroolToElement(webElement.getLocation().getX(), webElement.getLocation().getY());
        webElement.click();
    }

    public void selectproductByBrandName(String brandName) throws Exception {
        WebElement elementsByXpath = null;
        try {
            WebElement scrollToElement = findElementByXpath("//li[@class='box-container brand']/ol");

            scroolToElement(scrollToElement.getLocation().getX(),
                    scrollToElement.getLocation().getY());

            findElementByXpath("//div[@class='range-contain-row left full']/input[@placeholder='Marka ara']").sendKeys(brandName);

            elementsByXpath = findElementByXpath("//li[@class='box-container brand']/ol[@class='show-all-brands scrollable-filter-container scroll-lock']/li[@title='" + brandName + "']");

            elementsByXpath.click();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new Exception("Ayakkabı Marka İsmi Yanlış girilmiş olabilir ->" + e.getMessage());
        }

    }

}
