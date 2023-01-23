package all;

import java.util.ArrayList;


public class CardList {
	
	public ArrayList<Card> cardList = new ArrayList<>();
	
	
	CardList() {
		
		CardSetFree setFree = new CardSetFree();
		cardList.add(setFree);
		
		CardDoubleCellRate doubleCellRate = new CardDoubleCellRate();
		cardList.add(doubleCellRate);
		
		CardGetThreePointsStone getThreePointsStone = new CardGetThreePointsStone();
		cardList.add(getThreePointsStone);
		
		CardExcludeStone excludeStone = new CardExcludeStone();
		cardList.add(excludeStone);
		
		CardReplaceCard replaceCard = new CardReplaceCard();
		cardList.add(replaceCard);
	}
	

}
