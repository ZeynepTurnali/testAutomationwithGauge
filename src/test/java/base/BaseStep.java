package base;

import com.thoughtworks.gauge.Step;
import driver.Driver;
import org.openqa.selenium.interactions.Actions;

public class BaseStep extends BaseUtil {

    @Step("Safayı tam ekran yap")
    public void maximizeOfPage() {
        maximizeOfPageUtil();
    }

    @Step("<css> css alana tıkla")
    public void clickByCss(String css) {
        findElementByCss(css).click();
    }

    @Step("<id> id li alana tıkla")
    public void clickById(String id) {
        findElementById(id).click();
    }

    @Step("<XPath> XPath li alana tıkla")
    public void clickByXPath(String XPath) {
        findElementByXpath(XPath).click();
    }

    @Step("<xpath> alanının üzerine kaydır.")
    public void hoverToElementByXpath(String xpath) {

        Actions actions = new Actions(getWebDriver());
        actions.moveToElement(findElementByXpath(xpath)).build().perform();
    }

    @Step("<className> class li alana tıkla")
    public void clickByClassName(String className) {
        findElementByClassName(className).click();
    }

    @Step("<url> sayfasına git")
    public void geturl(String url) {
        //  String app_url = System.getenv("APP_URL");
        Driver.webDriver.get(url + "/");
    }

    @Step("<id> id li alana <text> ifadesini yaz.")
    public void sendKeyByIdTo(String id, String text) {
        sendKeyById(id, text);
    }

    @Step("<css> css li alana <text> ifadesinş yaz.")
    public void sendKeyByCssTo(String css, String text) {
        sendKeyByCss(css, text);
    }

    @Step("<XPath> XPath li alana <text> ifadesini yaz.")
    public void sendKeyByXPathTo(String XPath, String text) {

       // Thread.sleep(3000);
        sendKeyByXPath(XPath, text);
    }

    @Step("<saniye> bekle")
    public void waitSecond(int saniye) throws InterruptedException {
        Thread.sleep(saniye*1000);
    }
    @Step("<Class Name> Class Name li alana <text> ifadesini yaz.")
    public void sendKeyByClassNameTo(String Class, String text) {
        sendKeyByClassName(Class, text);
    }

    @Step("<xpath> alanına kaydır ve tıkla")
    public void scroolAndChoseWithXpath(String xpath) {
        scrollToByXpathElementClick(xpath);
    }

    @Step("<css> alanına kaydır ve tıkla..")
    public void scroolAndChoseWithCss(String css) {
        scrollToByCssElementClick(css);
    }

    @Step("<className> alanına kaydır ve tıkla.")
    public void scroolAndChoseWithClass(String className) {
        scrollToByCssElementClick(className);
    }

    @Step("seçilen ürünü <key> tane arttır.")
    public void addProducttoBasketClass(int key){
        addtoProductBasket("icon-plus",key);
    }
    @Step("Sepet fiyat kontrol")
    public void basketTotalPriceControl(){
        basketPriceControl();
    }
    @Step("Sepeti boşalt")
    public void emptyBasket(){
        clearBasket();
    }
    @Step("Sepet kontrol")
    public void isemptyBasket(){
        basketControl();
    }
    @Step("Ana sayfaya dön")
    public void backtoHomePage(){
        findElementByXpath("(//span[contains(.,'hepsiburada.com')])[1]").click();
    }
    @Step("Cookileri kaldır")
    public void deleteCookies(){
        deleteAllCookies();
    }
    @Step("Rastgele bir ürün seç")
    public  void randomProduct(){
        randomProductChoice();
    }
    @Step("<marka> markalı ayakkabıyı seç")
    public void choiceProductBrand(String brand) throws Exception {
        selectproductByBrandName(brand);
    }
}
