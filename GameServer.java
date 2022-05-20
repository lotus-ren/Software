package blackjack1;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Scanner;

public class GameServer {
   
   private static Dealer dealer;
   private static Deck deck;
   public static  Scanner scanner;
   
   public GameServer(){
      
      dealer = new Dealer();
      deck = new Deck();
      scanner = new Scanner(System.in);

   }

   public static final int PORT = 8080; // ポート番号を設定する．


 public  static void main(String[] args)
 throws IOException, InterruptedException {
   
  GameServer gameserver=new GameServer();

 ServerSocket s = new ServerSocket(PORT); // ソケットを作成する
 System.out.println("Started: " + s);
 try {
    Socket socket = s.accept(); // コネクション設定要求を待つ
 try {
     System.out.println(
     "Connection accepted: " + socket);
     BufferedReader in = 
    new BufferedReader(
     new InputStreamReader(
      socket.getInputStream())); // データ受信用バッファの設定
 PrintWriter out =
    new PrintWriter(
     new BufferedWriter(
      new OutputStreamWriter(
        socket.getOutputStream())), true); // 送信バッファ設定
 while (true) {
     String str = in.readLine(); // データの受信
     //Game over
    if(str.equals("3")) break;
    //Game start
    if(str.equals("1")) {

     System.out.println("game ready to start : ");
     //play
      deck.shuffle();
      //clear dealer's card
      dealer.clear();
      //bet and start handing out cards
      String betover = in.readLine();

      if(betover.equals("bet")){
         Thread.sleep(500);
         System.out.println("Handing out cards : ");
         dealer.getCardsInHand().add(deck.getOne());
         dealer.getCardsInHand().add(deck.getOne());
        out.println("ディーラーのカードは:");
        out.println("1." + "?");
        out.println("2." + dealer.cardsInHand.get(1).transferColor() + " " + dealer.cardsInHand.get(1).transferPoint());
        out.println("\n");
        //dealer cards over
        out.println("dco");
        System.out.println("Wating for player : ");
        Thread.sleep(500);
        //send　2 cards to player from deck
        for(int i=0;i<2;i++){
        Card c=deck.getOne();
        out.println(c.transferColor());
        out.println(c.point);
        }

   while(true){
      
         String opt=in.readLine();
         //send　1 card to player from deck
         Thread.sleep(500);
        if(opt.equals("needcard")){
         Card c=deck.getOne();
         out.println(c.transferColor());
         out.println(c.point);
        }

        if(opt.equals("PlayerCardOver"))break;

      }
        
      System.out.println("Dealer starts to get card : ");
        
        String finished=in.readLine();

        if (!finished.equals("true") ) {
         Thread.sleep(500);
         //show dealer's card
         out.println("ディーラーのカードは: 点数: " + dealer.getTotalPoints());
         int i = 1;
         for(Card card : dealer.cardsInHand){
         out.println(i++ + "." + card.transferColor() + " " + card.transferPoint());
         
      }
         out.println("\n");
         //17以上になるまでカードを引く
         while (dealer.getTotalPoints() < 17) {
            Thread.sleep(500);
             dealer.getCardsInHand().add(deck.getOne());
             out.println("ディーラーのカードは: 点数: " + dealer.getTotalPoints());
           //show dealer's card
            int j = 1;
            for(Card card : dealer.cardsInHand){
            out.println(j++ + "." + card.transferColor() + " " + card.transferPoint());
            }
            out.println("\n");
         }

         out.println("DealerCardOver");
         System.out.println("Dealer finished  : ");
         Thread.sleep(500);

         //点数が21以上になるとplayer勝ち
         if (dealer.getTotalPoints() > 21) {
            out.println("playerWin");
         } else{
             //ディーラーの点数を計算し、blackJackかどうかを判断、クライアント側に送り、勝負を決める
             out.println("decidewinner");
             List<Card> dealerCards = dealer.getCardsInHand();
             int dealerSum = 0;
             for(Card card : dealerCards){
                 if(card.getPoint() == 1){
                     dealerSum += 11;
                 } else if(card.getPoint() > 10){
                     dealerSum += 10;
                 } else {
                     dealerSum += card.getPoint();
                 }
             }
             while(dealerSum > 21){
                 dealerSum -= 10;
             }
             boolean isDealerBlackJack = false;
             if(dealerCards.size() ==2){
                 isDealerBlackJack = true;
             }
             out.println(isDealerBlackJack);
             out.println(dealerSum);
         }
     }
     

      }
      
      
    }

   

 }
 } finally {
    System.out.println("closing...");
    socket.close();
 }
 } finally {
    s.close();
 }
 }


}

