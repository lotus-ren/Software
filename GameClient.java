package blackjack1;


import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Scanner;


public class GameClient {
   
   public static int choice;
   public static  Scanner scanner;
   public static Player player;

   public GameClient(){

      player =new Player();
      player.setMoney(1000);
      scanner = new Scanner(System.in);
  }

 public static void main(String[] args)
 throws IOException, InterruptedException {
   
   GameClient gameclient=new GameClient();
   
 InetAddress addr =
    InetAddress.getByName("localhost"); // IP アドレスへの変換
 System.out.println("addr = " + addr);
 Socket socket =
    new Socket(addr, 8080); // ソケットの生成
 try {
    System.out.println("socket = " + socket);
    BufferedReader in = 
        new BufferedReader(
         new InputStreamReader(
          socket.getInputStream())); // データ受信用バッファの設定
 PrintWriter out =
    new PrintWriter(
     new BufferedWriter(
      new OutputStreamWriter(
        socket.getOutputStream())), true); // 送信バッファ設定

        
        System.out.println("BlackJackへようこそ、あなたは1000円を持っています。Game Start！");
        
        while(true){
        //select mode
        playerChoose();
        out.println(choice);
        //mode 1: starting the game
        if(choice==1){
         player.clear();
         //bet
        if(chooseBet()){

           out.println("bet");

           //show dealer's cards
           while(true){
            String str=in.readLine();
            if(str.equals("dco"))break;
            System.out.println(str);
            }
        
            //recieve 2 cards from deck
            for(int i=0;i<2;i++){
            Card c=new Card();
            c.setColor(Color.valueOf(in.readLine()));
            c.setPoint(Integer.parseInt(in.readLine()));
            player.getCardsInHand().add(c);
            }

            //show player's cards
            player.showCards();
            System.out.println("\n");
            boolean finished = false;

            while (true) {
               //No double down because the money is not enough
               if(player.getMoney() < player.getBet() * 2){
                   System.out.println("操作を選んでください");
                   System.out.println("1.カードを引く 2.止める ");
                   int choice = scanner.nextInt();
                   //カードを引く
                   if (choice == 1) {

                      out.println("needcard");

                        Card c=new Card();
                        c.setColor(Color.valueOf(in.readLine()));
                        c.setPoint(Integer.parseInt(in.readLine()));
                        player.getCardsInHand().add(c);
                     
                        player.showCards();
                        System.out.println("\n");
                        Thread.sleep(500);
                       //バースト
                       if (BlackJackRule.isPlayerOver(player)) {
                           playerFail();
                           finished = true;
                           break;
                       }
                   } else {
                       break;
                   }
               } else {
                   System.out.println("操作を選んでください");
                   System.out.println("1.カードを引く 2.ダブルダウン 3.止める");
                   int choice = scanner.nextInt();
                   //カードを引く
                   if (choice == 1) {
                     out.println("needcard");
                     
                        Card c=new Card();
                        c.setColor(Color.valueOf(in.readLine()));
                        c.setPoint(Integer.parseInt(in.readLine()));
                        player.getCardsInHand().add(c);
                     
                  
                     player.showCards();
                     System.out.println("\n");
                       //バースト
                       if (BlackJackRule.isPlayerOver(player)) {
                           playerFail();
                           finished = true;
                           break;
                       }
                   } else if(choice == 2){
                       //ダブルダウン、ベットを２倍する、1枚引いて終わる
                       player.setBet(player.getBet() * 2);

                       out.println("needcard");
                       Card c=new Card();
                       c.setColor(Color.valueOf(in.readLine()));
                       c.setPoint(Integer.parseInt(in.readLine()));
                       player.getCardsInHand().add(c);
                       
                       player.showCards();
                       System.out.println("\n");
                       if (BlackJackRule.isPlayerOver(player)) {
                           playerFail();
                           finished = true;
                       }
                       break;
                   }else {
                       break;
                   }
               }
               if(finished)break;
           }
           
           out.println("PlayerCardOver");
           
           out.println(String.valueOf(finished));
          
           if(!finished){
            //show dealer's card
              while(true){
            String str=in.readLine();
            if(str.equals("DealerCardOver")) {
               break;
            }
            System.out.println(str);
         }
           //recieve the condition of winning
           while(true){
            String str=in.readLine();
            
           if(str.equals("playerWin")) {
            playerWin();
            break;
         }
           if(str.equals("decidewinner")) {
              //プレイヤーの点数を計算し，blackJackかどうかを判断
            List<Card> playerCards = player.getCardsInHand();
            int playerSum = 0;
            for(Card card : playerCards){
               if(card.getPoint() == 1){
                playerSum += 11;
               } else if(card.getPoint() > 10){
                playerSum += 10;
               } else {
                playerSum += card.getPoint();
               }
            }

            while(playerSum > 21){
            playerSum -= 10;
            }

            boolean isPlayerBlackJack = false;

            if(playerSum == 21 && playerCards.size() ==2){
               isPlayerBlackJack = true;
            }
            
             //ディーラーと比べ、勝負を決める
            String isDealerBlackJack=in.readLine();
            int dealerSum=Integer.parseInt(in.readLine());
            if(isPlayerBlackJack){
               if(isDealerBlackJack.equals("true")){
               noOneWin();
               break;
            } else {
               playerWinMore();
               break;
            }
            } else {
               if(playerSum > dealerSum){
               playerWin();
               break;
            } else if(playerSum == dealerSum){
               noOneWin();
               break;
            } else{
               playerFail();
               break;
            }
               }
            }

   
           }
         }
       }
           
       }
       if(choice==3)break;
      }
        
        
      
      
 


 } finally {
 System.out.println("closing...");
 socket.close();
 }
 }


 //select the mode
 public static void playerChoose() {
   System.out.println("-----------------------------");
   System.out.println("-        1.ゲームを始める    -");
   System.out.println("-        2.残高を照会する    -");
   System.out.println("-        3.ゲームを終える    -");
   System.out.println("-----------------------------");
   
   
   do {
       choice = scanner.nextInt();
       switch (choice){
           case 1 :
               System.out.println("ゲームはまもなくスタート！");
               break;
           case 2 :
               System.out.println("あなたの残高は: " + player.getMoney());
               playerChoose();
               break;
           case 3 :
               System.out.println("ご参加ありがとうございます，また遊びに来てください");
               break;
           default:
               System.out.println("入力エラー，もう1度選んでください");
       }
   }while (choice > 3 || choice < 1);
}

public static  boolean chooseBet() {
   System.out.println("ベット額を決めてください:");
   int makeBet;
   
   do {
       makeBet = scanner.nextInt();
       if(makeBet > player.getMoney()){
           System.out.println("残高が足りません，もう一度入力してください");
       return false;
       }else{
       player.setBet(makeBet);
       return true;
      }
   }while (makeBet > player.getMoney());
   
}

public static void showCards(){
   player.showCards();
}

public static  void playerFail() {
   System.out.println("You Lose");
   int bet = player.getBet();
   int money = player.getMoney() - bet;
   //残高を減らす
   player.setMoney(money);
   System.out.println("あなたは" + bet + "円負けました、残り" + money + "円");
   if(player.getMoney() <= 0){
       System.out.println("破産しました...、1000円を差し上げます、ゲームを楽しんでください");
       player.setMoney(1000);
       
   }
}
public static  void playerWin() {
   System.out.println("You Win");
   int bet = player.getBet();
   int money = player.getMoney() + bet;
   //残高を増やす
   player.setMoney(money);
   System.out.println("あなたは" + bet + "円勝ちました、残り" + money + "円");
}

public static void noOneWin() {
   System.out.println("Draw");
   System.out.println("あなたの残高は" + player.getMoney() + "円");
}

public  static void playerWinMore() {
   System.out.println("You Win！");
   int bet = player.getBet();
   int money = player.getMoney() + bet + bet / 2;
   //残高を増やす
   player.setMoney(money);
   System.out.println("あなたは" + (bet + bet / 2) + "円勝ちました、残り" + money + "円");
}



}
