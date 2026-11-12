package Object;

public class OBJ_Coin extends SuperObject {
    public OBJ_Coin(int amount) {
        name = "Gold Coin";
        image = setup("/src/object/coin.png");
        value = amount;
        description = "[Gold Coin]\nAncient kingdom currency.\nUsed at Merchant Boris' shop.";
    }

    public OBJ_Coin() {
        this(10);
    }
}
