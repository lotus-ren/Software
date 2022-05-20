package blackjack1;

import java.util.ArrayList;
import java.util.List;

public class Player {
    //プレイヤーの残高
    private Integer money;
    //プレイヤーのカード
    private List<Card> cardsInHand;
    private Integer bet;

    public Player() {
        money = 1000;
        bet = 0;
        cardsInHand = new ArrayList<>();
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public List<Card> getCardsInHand() {
        return cardsInHand;
    }

    public void setCardsInHand(List<Card> cardsInHand) {
        this.cardsInHand = cardsInHand;
    }

    public Integer getBet() {
        return bet;
    }

    public void setBet(Integer bet) {
        this.bet = bet;
    }

    
    public void showCards() {
        System.out.println("あなたのカードは:  点数: " + getTotalPoints());
        int i = 1;
        for(Card card : cardsInHand){
            System.out.println(i++ + "." + card.transferColor() + " " + card.transferPoint());
        }
    }

    //プレイヤーの点数を計算する
    public int getTotalPoints() {
        int sum = 0;
        for(Card card : cardsInHand){
            sum += card.getPoint() > 10 ? 10 : card.getPoint();
        }
        return sum;
    }
    //カードをクリア
    public void clear() {
        cardsInHand = new ArrayList<>();
    }
}

