package blackjack1;

import java.util.ArrayList;
import java.util.List;

public class Dealer {
    //Dealer's cards
    public List<Card> cardsInHand;

    public Dealer() {
        cardsInHand = new ArrayList<>();
    }

    public List<Card> getCardsInHand() {
        return cardsInHand;
    }

    public void setCardsInHand(List<Card> cardsInHand) {
        this.cardsInHand = cardsInHand;
    }

    

    //カードをクリア
    public void clear() {
        cardsInHand = new ArrayList<>();
    }

    //ディーラーの点数を計算する
    public int getTotalPoints() {
        int sum = 0;
        for(Card card : cardsInHand){
            sum += card.getPoint() > 10 ? 10 : card.getPoint();
        }
        return sum;
    }
}

