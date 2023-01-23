package all;

import java.util.HashMap;
import java.util.Map;

import javax.websocket.Session;


public class RoomController {
	
	Map<Integer, Room> roomMap; // (roomNumber, room)
	
	
	
	public RoomController () {
		roomMap = new HashMap<Integer, Room>();
	}
	
	
	public void makeRoom(int roomNumber, PlayerInfo whitePlayer, PlayerInfo blackPlayer) {
		Room room = new Room(roomNumber, whitePlayer, blackPlayer);
		roomMap.put(room.getRoomNumber(), room);
	}
	
	
	public void receiveFromCommunication(MessageAppPl message, Session session) {
		roomMap.get( message.roomNumber ).receive( message, session );
		
	}
	
	
	// ルーム -> ロビーサーバ -> の順でルーム破壊要請を受け取り、ルームを破壊する
	public void breakRoom(int roomNumber) {
		// ルームの削除
		this.roomMap.remove( roomNumber );
	}
}
