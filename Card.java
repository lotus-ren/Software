package blackjack1;

public class Card {
    public int point;
    public Color color;


    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Card{" +
                "point=" + point +
                ", color=" + color +
                '}';
    }

    //color to string
    public String transferColor(){
        switch (color){
            case SPADE:
                return "SPADE";
            case HEART:
                return "HEART";
            case CLUB:
                return "CLUB";
            case DIAMOND:
                return "DIAMOND";
        }
        return "";
    }

    //1~13 to A~K
    public String transferPoint(){
        if(point <= 10 && point >1){
            return Integer.toString(point);
        } else {
            switch (point){
                case 1 :
                    return "A";
                case 11 :
                    return "J";
                case 12 :
                    return "Q";
                case 13 :
                    return "K";
            };
        }
        return "";
    }

}
