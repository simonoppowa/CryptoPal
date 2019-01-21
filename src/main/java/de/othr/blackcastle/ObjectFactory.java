
package de.othr.blackcastle;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.swr.oth.service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _BuyGame_QNAME = new QName("http://service.oth.swr.de/", "buyGame");
    private final static QName _GetGameTitelResponse_QNAME = new QName("http://service.oth.swr.de/", "getGameTitelResponse");
    private final static QName _AllOrders_QNAME = new QName("http://service.oth.swr.de/", "allOrders");
    private final static QName _BuyGameResponse_QNAME = new QName("http://service.oth.swr.de/", "buyGameResponse");
    private final static QName _GetGameTitel_QNAME = new QName("http://service.oth.swr.de/", "getGameTitel");
    private final static QName _AllOrdersResponse_QNAME = new QName("http://service.oth.swr.de/", "allOrdersResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.swr.oth.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AllOrdersResponse }
     * 
     */
    public AllOrdersResponse createAllOrdersResponse() {
        return new AllOrdersResponse();
    }

    /**
     * Create an instance of {@link AllOrders }
     * 
     */
    public AllOrders createAllOrders() {
        return new AllOrders();
    }

    /**
     * Create an instance of {@link BuyGameResponse }
     * 
     */
    public BuyGameResponse createBuyGameResponse() {
        return new BuyGameResponse();
    }

    /**
     * Create an instance of {@link GetGameTitel }
     * 
     */
    public GetGameTitel createGetGameTitel() {
        return new GetGameTitel();
    }

    /**
     * Create an instance of {@link GetGameTitelResponse }
     * 
     */
    public GetGameTitelResponse createGetGameTitelResponse() {
        return new GetGameTitelResponse();
    }

    /**
     * Create an instance of {@link BuyGame }
     * 
     */
    public BuyGame createBuyGame() {
        return new BuyGame();
    }

    /**
     * Create an instance of {@link Game }
     * 
     */
    public Game createGame() {
        return new Game();
    }

    /**
     * Create an instance of {@link StringIdEntity }
     * 
     */
    public StringIdEntity createStringIdEntity() {
        return new StringIdEntity();
    }

    /**
     * Create an instance of {@link Updates }
     * 
     */
    public Updates createUpdates() {
        return new Updates();
    }

    /**
     * Create an instance of {@link Library }
     * 
     */
    public Library createLibrary() {
        return new Library();
    }

    /**
     * Create an instance of {@link GameDeveloper }
     * 
     */
    public GameDeveloper createGameDeveloper() {
        return new GameDeveloper();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link GeneratedIdEntity }
     * 
     */
    public GeneratedIdEntity createGeneratedIdEntity() {
        return new GeneratedIdEntity();
    }

    /**
     * Create an instance of {@link Order }
     * 
     */
    public Order createOrder() {
        return new Order();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuyGame }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.oth.swr.de/", name = "buyGame")
    public JAXBElement<BuyGame> createBuyGame(BuyGame value) {
        return new JAXBElement<BuyGame>(_BuyGame_QNAME, BuyGame.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGameTitelResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.oth.swr.de/", name = "getGameTitelResponse")
    public JAXBElement<GetGameTitelResponse> createGetGameTitelResponse(GetGameTitelResponse value) {
        return new JAXBElement<GetGameTitelResponse>(_GetGameTitelResponse_QNAME, GetGameTitelResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AllOrders }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.oth.swr.de/", name = "allOrders")
    public JAXBElement<AllOrders> createAllOrders(AllOrders value) {
        return new JAXBElement<AllOrders>(_AllOrders_QNAME, AllOrders.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuyGameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.oth.swr.de/", name = "buyGameResponse")
    public JAXBElement<BuyGameResponse> createBuyGameResponse(BuyGameResponse value) {
        return new JAXBElement<BuyGameResponse>(_BuyGameResponse_QNAME, BuyGameResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGameTitel }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.oth.swr.de/", name = "getGameTitel")
    public JAXBElement<GetGameTitel> createGetGameTitel(GetGameTitel value) {
        return new JAXBElement<GetGameTitel>(_GetGameTitel_QNAME, GetGameTitel.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AllOrdersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.oth.swr.de/", name = "allOrdersResponse")
    public JAXBElement<AllOrdersResponse> createAllOrdersResponse(AllOrdersResponse value) {
        return new JAXBElement<AllOrdersResponse>(_AllOrdersResponse_QNAME, AllOrdersResponse.class, null, value);
    }

}
