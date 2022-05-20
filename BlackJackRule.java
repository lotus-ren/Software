package blackjack1;
import java.util.List;

public  class BlackJackRule {

    //バーストしたかどうかを検知
    public static boolean isPlayerOver(Player player){
        List<Card> cardList = player.getCardsInHand();
        int sum = 0;
        for(Card card : cardList){
            int point = card.getPoint();
            sum += point >= 10 ? 10 : point;
            if(sum > 21){
                return true;
            }
        }
        return false;
    }
}
