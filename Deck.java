package blackjack1;

import java.util.Random;

public class Deck {
    private Card [] cards;
    private Integer index;
    private Random random;
    //Initialization
    public Deck() {
        random = new Random();
        index = 0;
        cards = new Card[52];
        //52枚のカードを生成
        for (int i = 0; i < 52; i++) {
            cards[i] = new Card();
            cards[i].setPoint(i % 13 + 1);
            switch (i / 13){
                case 0 :
                    cards[i].setColor(Color.SPADE);
                    break;
                case 1 :
                    cards[i].setColor(Color.HEART);
                    break;
                case 2 :
                    cards[i].setColor(Color.CLUB);
                    break;
                case 3 :
                    cards[i].setColor(Color.DIAMOND);
            }
        }
    }

    //シャフル
    public void shuffle(){
        for(int i =1;i <= 52;i ++){
            int randId = random.nextInt(i);
            swap(randId, i - i);
        }
        index = 0;
    }

    //カードを配る
    public Card getOne(){
        //牌不够
        if(index == 53){
            return null;
        }
        return cards[index ++];
    }

    private void swap(int randId, int i) {
        Card temp = cards[i];
        cards[i] = cards[randId];
        cards[randId] = temp;
    }

    public Card[] getCards() {
        return cards;
    }
}

