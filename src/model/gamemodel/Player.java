//_________________________________________________________________________________________________________________________________________
	
	package model.gamemodel;
//_________________________________________________________________________________________________________________________________________

//_________________________________________________________________________________________________________________________________________
	
	public class Player {
		
		private String name;
		private String nickname;
		private String password;
		private String favColor;
		private Date birthday;
		private Shape avatar;
//_________________________________________________________________________________________________________________________________________
		
		public Player(String nickname, String password) {
			this.nickname = nickname;
			this.password = password;
		}
//_________________________________________________________________________________________________________________________________________
}
