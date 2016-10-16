package clientSrc;
public class State {

	private String identity;
	private String roomId;
	private String pwd;
	
	public State(String identity, String roomId) {
		this.identity = identity;
		this.roomId = roomId;
		
	}
	public State(String identity, String roomId,String pwd) {
		this.identity = identity;
		this.roomId = roomId;
		this.pwd = pwd;

	}
	
	public synchronized String getRoomId() {
		return roomId;
	}
	public synchronized void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	
	public String getIdentity() {
		return identity;
	}
	
	
}
