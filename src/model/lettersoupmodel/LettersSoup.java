//_________________________________________________________________________________________________________________________________________
	package model.lettersoupmodel;
//_________________________________________________________________________________________________________________________________________
	import java.io.BufferedReader;
	import java.io.FileNotFoundException;
	import java.io.FileReader;
	import java.io.IOException;
	import java.util.ArrayList;
	import java.util.Random;
	import org.controlsfx.control.Notifications;
	import javafx.scene.image.Image;
	import javafx.scene.image.ImageView;
	import model.gamemodel.Difficulty;
	import model.gamemodel.Game;
//_________________________________________________________________________________________________________________________________________
		/**
		 * This class manages the nedeed methods and attributes to create letters soup.
		 * @author Lina Johanna Salinas Delgado
		 * @author Juan Jos� Valencia Jaramillo
		 * @version V_01 May_2019
		 * 
		 */
		public class LettersSoup extends Game{
		
		private String[][] lettersoup;
		private Topic topic;
		private int size;
		
		private ArrayList<String> animals;
		private ArrayList<String> cities;
		private ArrayList<String> numbers; 
		
		private Word[] solution;
		private Random random;
//_________________________________________________________________________________________________________________________________________
		/**
		 * <b>Letters Soup Constructor</b><br>
		 * This method allows to construct objects of type letters soup<br>
		 * @param topic the topic that is going to determinate the words inside the letters soup
		 * @param difficultylevel the difficultylevel that is going to determinate the words to be found
		 */
		public LettersSoup(Topic topic,Difficulty difficultylevel){
			super(difficultylevel);
			this.topic = topic;
			animals = new ArrayList<>();
			cities = new ArrayList<>();
			numbers = new ArrayList<>();
			random = new Random();
			generateLetterSoup(topic);
		}
	//_____________________________________________________________________________________________________________________________________
		/**
		 * This method returns the lettersoup matrix to be manipulated and be printed, modified, etc.<br>
		 * <b>Pre:</b> the lettersoup exists.<br>
		 * @return the matrix of Strings that represents the lettersoup
		 */
		public String[][] getLetterSoup() {
			return lettersoup;
		}
	//_____________________________________________________________________________________________________________________________________
		/**
		 * 
		 * @return
		 */
		public Word[] getSolution(){
			return solution;
		}
	//_____________________________________________________________________________________________________________________________________
		/**
		 * 
		 * @return
		 */
		private String generateRandomWord() {
			String randomWord = "";
			if(topic == Topic.ANIMALS) {
				randomWord = animals.get(random.nextInt(animals.size()));
			}
			else if(topic == Topic.CITIES) {
				randomWord = cities.get(random.nextInt(cities.size()));
			}
			else {
				randomWord = numbers.get(random.nextInt(numbers.size()));
			}
			return randomWord;
		}
	//_____________________________________________________________________________________________________________________________________
		/**
		 * 
		 */
		private void load(){
			try {
				if(topic == Topic.ANIMALS) {
					loadAnimals();
				}
				else if(topic == Topic.CITIES) {
					loadCities();
				}
				else {
					loadNumbers();
				}
			}
			catch(IOException ioe) {
				Notifications.create()
				.title("Announcement")
				.text("The dictionaries cannot be found please make sure there are file texts in the data folder of the project")
				.graphic(new ImageView(new Image("gui/gamegui/images/error.png")))
				.darkStyle();
			}
		}
	//_____________________________________________________________________________________________________________________________________
		/**
		 * 
		 */
		private void initData() {
			if(this.getDifficultylevel() == Difficulty.BASIC) {
				size = 10;
				solution = new Word[3];
			}
			else {
				size = 15;
				solution = new Word[6];
			}
		}
	//_____________________________________________________________________________________________________________________________________
		/**
		 * This method loads the textfile that contains the words related with animals topic<br>
		 * <b>Pos:</b> The textfile with the topic words about animals is read.
		 * @param path
		 * @throws IOException
		 */
		private void loadAnimals() throws IOException {
			String path = "data/dictionaries(lettersoup)/animals.txt";
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line = br.readLine();
				while(line!=null) {
					animals.add(line);
					line = br.readLine();
				}
			br.close();
		}
	//_____________________________________________________________________________________________________________________________________
		/**
		 * 
		 * @param path
		 * @throws IOException
		 */
		private void loadCities() throws IOException {
			String path = "data/dictionaries(lettersoup)/cities.txt";
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line = br.readLine();
				while(line!=null) {
					cities.add(line);
					line = br.readLine();
				}
			br.close();
		}
	//_____________________________________________________________________________________________________________________________________
		/**
		 * 
		 * @param path
		 * @throws IOException
		 */
		private void loadNumbers() throws IOException {
			String path = "data/dictionaries(lettersoup)/numbers.txt";
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line = br.readLine();
				while(line!=null) {
					numbers.add(line);
					line = br.readLine();
				}
			br.close();
		}
	//_____________________________________________________________________________________________________________________________________
		/**
		 * 
		 * @param topic
		 */
		private void generateLetterSoup(Topic topic){
			initData();
			lettersoup = new String[size][size]; 
			load();
			for(int i=0;i<solution.length;i++) {
				String wordfromfiletext = generateRandomWord();
				solution[i] = new Word(wordfromfiletext,random.nextInt(size-1),random.nextInt(size-1),wordfromfiletext.length());
				checkForNonRepitedElements(solution[i], i);
				addWord(solution[i]);
			}
			fillSoup();
		}
	//_____________________________________________________________________________________________________________________________________
		/**
		 * 
		 */
		private void fillSoup() {
			for(int i=0;i<lettersoup.length;i++) {
				for(int j=0;j<lettersoup[i].length;j++) {
					if(lettersoup[i][j] == null) {
						if(topic == Topic.ANIMALS) {
							lettersoup[i][j] = Character.toString(animals.get(random.nextInt(animals.size())).charAt(0));
						}
						else if(topic == Topic.CITIES) {
							lettersoup[i][j] = Character.toString(cities.get(random.nextInt(cities.size())).charAt(1));
						}
						else {
							lettersoup[i][j] = Character.toString(numbers.get(random.nextInt(numbers.size())).charAt(2));
						}
					}
				}
			}
		}
	//_____________________________________________________________________________________________________________________________________
		/**
		 * 
		 * @param word
		 * @param pos
		 */
		private void checkForNonRepitedElements(Word word, int pos) {
			String wordfromfiletext = "";
			boolean nonrepited = false;
			if(countSolutions()>1) {
				for(int i=0;i<solution.length && nonrepited==false;i++) {					
					if(solution[i].getName().equals(word.getName())) {
						if(topic == Topic.ANIMALS) {
							wordfromfiletext = animals.get(random.nextInt(animals.size()));
						}
						else if(topic == Topic.CITIES) {
							wordfromfiletext = cities.get(random.nextInt(cities.size()));
						}
						else if(topic == Topic.NUMBERS) {
							wordfromfiletext = numbers.get(random.nextInt(numbers.size()));
						}
						solution[pos] = new Word(wordfromfiletext,random.nextInt(size-1),random.nextInt(size-1),wordfromfiletext.length());
					}
					else {
						nonrepited = true;
					}
				}
			}
		}
	//_____________________________________________________________________________________________________________________________________
		/**
		 * 
		 * @param word
		 */
		private void addWord(Word word) {
			Direction dir = word.getDirection();
			int column = word.getColumn();
			int row = word.getRow();
			int length = word.getLength()-1;
			switch(dir) {
				case DOWN:
					addWordtoDown(word,row,column,length);
				break;
				case UP:
					addWordToUp(word,row,column,length);
				break;
				case LEFT:
					addWordToLeft(word,row,column,length);
				break;
				case RIGHT:
					addWordToRight(word,row,column,length);
				break;
				case NORTHWEST:
					addWordToNorthWest(word,row,column,length);
					break;
				case NORTHEAST:
					addWordToNorthEast(word,row,column,length);
					break;
				case SOUTHWEST:
					addWordToSouthWest(word,row,column,length);
					break;
				case SOUTHEAST:
					addWordToSouthEast(word,row,column,length);
					break;
			}
		}
	//_____________________________________________________________________________________________________________________________________
		/**
		 * 
		 * @param column
		 * @return
		 */
		private boolean verifyColumn(int column) {
			boolean empty = true;
			for(int i=0;i<lettersoup.length-1&&empty;i++) {
				if(lettersoup[i][column]!=null) {
					empty = false;
				}
			}
			return empty;
		}
		/**
		 * 
		 * @param row
		 * @return
		 */
		private boolean verifyRow(int row) {

			boolean empty = true;
			for(int i=0;i<lettersoup.length-1&&empty;i++) {
				if(lettersoup[row][i]!=null){
					empty = false;
				}
			}
			return empty;
		}
	//_____________________________________________________________________________________________________________________________________
		/**
		 * 
		 * @param row
		 * @param column
		 * @param length
		 * @return
		 */
		private boolean verifyNorthWestDiagonal(int row, int column,int length) {
			boolean empty = true;
			for(int i=0;i<length&&empty;i++) {
				if(lettersoup[row][column] != null) {
					empty = false;
				}
				row--;
				column--;
			}
			return empty;
		}
	//_____________________________________________________________________________________________________________________________________
		/**
		 * 
		 * @param row
		 * @param column
		 * @param length
		 * @return
		 */
		private boolean verifyNorthEastDiagonal(int row, int column,int length) {
			boolean empty = true;
			for(int i=0;i<length&&empty;i++) {
				if(lettersoup[row][column] != null) {
					empty = false;
				}
				row--;
				column++;
			}
			return empty;
		}
	//_____________________________________________________________________________________________________________________________________
		/**
		 * 
		 * @param row
		 * @param column
		 * @param length
		 * @return
		 */
		private boolean verifySouthWestDiagonal(int row, int column,int length) {
			boolean empty = true;
			for(int i=0;i<length&&empty;i++){
				if(lettersoup[row][column] != null) {
					empty = false;
				}
				row++;
				column--;
			}
			return empty;
		}
	//_____________________________________________________________________________________________________________________________________
		/**
		 * 
		 * @param row
		 * @param column
		 * @param length
		 * @return
		 */
		private boolean verifySouthEastDiagonal(int row, int column,int length) {
			boolean empty = true;
			for(int i=0;i<length&&empty;i++) {
				if(lettersoup[row][column] != null) {
					empty = false;
				}
				row++;
				column++;
			}
			return empty;
		}
	//_____________________________________________________________________________________________________________________________________
		/**
		 * 
		 * @param word
		 * @param row
		 * @param column
		 * @param length
		 */
		private void addWordToUp(Word word, int row, int column, int length){
			String c;
			int i = 0;
			
			if(row<length) {
				word.setRow(length);
				row = word.getRow();
			}
			
			boolean spacec = verifyColumn(column);
			boolean spacer = verifyRow(row);
			while(spacec==false && spacer == false) {
				column = random.nextInt(lettersoup.length-1);
				spacec = verifyColumn(column);
				spacer = verifyRow(row);	
			}
			word.setColumn(column);
			
			while(i<=length) {
				c = Character.toString(word.getName().charAt(i));
				lettersoup[row][column] = c;
				i++;
				row--;
			}
		}
	//_____________________________________________________________________________________________________________________________________
		/**
		 * 
		 * @param word
		 * @param row
		 * @param column
		 * @param length
		 */
		private void addWordtoDown(Word word, int row, int column, int length){
			String c;
			int i = 0;
			
			if((lettersoup.length-1)-row<length) {
				word.setRow((lettersoup.length-1)-length);
				row = word.getRow();
			}
			
			boolean spacec = verifyColumn(column);
			boolean spacer = verifyRow(row);
			while(spacec==false && spacer==false){
				column = random.nextInt(lettersoup.length-1);
				spacec = verifyColumn(column);
				spacer = verifyRow(row);
			}
			word.setColumn(column);
			
			while(i<=length) {
				c = Character.toString(word.getName().charAt(i));
				lettersoup[row][column] = c;
				i++;
				row++;
			}
		}
	//_____________________________________________________________________________________________________________________________________
		/**
		 * 
		 * @param word
		 * @param row
		 * @param column
		 * @param length
		 */
		private void addWordToLeft(Word word, int row, int column, int length){
			String c;
			int i = 0;
			
			if(column<=length) {
				word.setColumn(length);
				column = word.getColumn();
			}
			boolean spacec = verifyColumn(column);
			boolean spacer = verifyRow(row);
			
			while((spacec==false)&&(spacer==false)){
				row = random.nextInt(lettersoup.length-1);
				spacec = verifyColumn(column);
				spacer = verifyRow(row);
			}			
			word.setRow(row);
			
			while(i<=length) {
				c = Character.toString(word.getName().charAt(i));
				lettersoup[row][column] = c;
				i++;
				column--;
			}
		}
	//_____________________________________________________________________________________________________________________________________
		/**
		 * 
		 * @param word
		 * @param row
		 * @param column
		 * @param length
		 */
		private void addWordToRight(Word word, int row, int column, int length){
			String c;
			int i = 0;
			
			if(column+length>lettersoup.length-1) {
				word.setColumn((lettersoup.length-1)-length);
				column = word.getColumn();
			}
			boolean spacec = verifyColumn(column);
			boolean spacer = verifyRow(row);
			
			while((verifyRow(row)==false)&&verifyColumn(column)==false){
				row = random.nextInt(lettersoup.length-1);
				spacec = verifyColumn(column);
				spacer = verifyRow(row);
			}
			word.setRow(row);
			
			while(i<=length){
				c = Character.toString(word.getName().charAt(i));
				lettersoup[row][column] = c;
				i++;
				column++;
			}
		}
	//_____________________________________________________________________________________________________________________________________
		/**
		 * 
		 * @param word
		 * @param row
		 * @param column
		 * @param length
		 */
		private void addWordToNorthWest(Word word,int row, int column, int length){
			String c;
			int i = 0;
			
			if(column<length || row<length) {
				word.setColumn(length);
				word.setRow(length);
				column = word.getColumn();
				row = word.getRow();
			}
			
			boolean space = verifyNorthWestDiagonal(row, column, length);
			int temprow = row;
			int tempcolumn = column;
			while(space==false){
				row = random.nextInt(lettersoup.length-1);
				column = random.nextInt(lettersoup.length-1);
				space = verifyNorthWestDiagonal(row, column, length);
				setSolution(temprow, tempcolumn, row, column);
				if(column<length || row<length) {
					word.setRow(length);
					word.setColumn(length);
					column = length;
					row = length;
					setSolution(temprow, tempcolumn, row, column);
				}
			}
			
			while(i<=length){
				c = Character.toString(word.getName().charAt(i));
				lettersoup[row][column] = c;
				i++;
				row--;
				column--;
			}
		}
	//_____________________________________________________________________________________________________________________________________
		/**
		 * 
		 * @param word
		 * @param row
		 * @param column
		 * @param length
		 */
		private void addWordToNorthEast(Word word, int row, int column, int length){
			String c;
			int i = 0;
			
			if(((lettersoup.length-1)-column<length)||(row<length)){
				word.setColumn((lettersoup.length-1)-length);
				word.setRow(length);
				column = word.getColumn();
				row = word.getRow();
			}
			
			boolean space = verifyNorthEastDiagonal(row, column, length);
			int temprow = row;
			int tempcolumn = column;
			while(space==false) {
				column = random.nextInt(lettersoup.length-1);
				row = random.nextInt(lettersoup.length-1);
				space = verifyNorthEastDiagonal(row, column, length);
				setSolution(temprow, tempcolumn, row, column);
				if((lettersoup.length-1)-column<length || row<length){
					word.setColumn((lettersoup.length-1)-length);
					word.setRow(length);
					column = word.getColumn();
					row = word.getRow();
					setSolution(temprow, tempcolumn, row, column);
				}
			}
			
			while(i<=length){
				c = Character.toString(word.getName().charAt(i));
				lettersoup[row][column] = c;
				i++;
				row--;
				column++;
			}
		}
	//_____________________________________________________________________________________________________________________________________
		/**
		 * 
		 * @param word
		 * @param row
		 * @param column
		 * @param length
		 */
		private void addWordToSouthWest(Word word,int row, int column, int length){
			String c;
			int i = 0;
			
			if((row+length>(lettersoup.length-1))||(column<length)){
				word.setRow((lettersoup.length-1)-length);
				word.setColumn(length);
				row = word.getRow();
				column = word.getColumn();
			}
			
			boolean space = verifySouthWestDiagonal(row, column, length);
			int temprow = row;
			int tempcolumn = column;
			while(space==false){
				row = random.nextInt(lettersoup.length-1);
				column = random.nextInt(lettersoup.length-1);
				space = verifySouthWestDiagonal(row, column, length);	
				setSolution(temprow, tempcolumn, row, column);
				if((row+length>(lettersoup.length-1))||(column<length)){
					word.setRow((lettersoup.length-1)-length);
					word.setColumn(length);
					row = word.getRow();
					column = word.getColumn();
					setSolution(temprow, tempcolumn, row, column);
				}	
			}
			
			while(i<=length){
				c = Character.toString(word.getName().charAt(i));
				lettersoup[row][column] = c;
				i++;
				row++;
				column--;
			}
		}
	//_____________________________________________________________________________________________________________________________________
		/**
		 * 
		 * @param word
		 * @param column
		 * @param row
		 * @param length
		 */
		private void addWordToSouthEast(Word word, int column, int row, int length){
			String c;
			int i = 0;
			
			if(((lettersoup.length-1)-column<length)||(row+length>(lettersoup.length-1))){
				word.setColumn((lettersoup.length-1)-length);
				word.setRow((lettersoup.length-1)-length);
				row = word.getRow();
				column = word.getColumn();
			}
			
			boolean space = verifySouthEastDiagonal(row, column, length);
			int temprow = row;
			int tempcolumn = column;
			while(space == false){
				row = random.nextInt(lettersoup.length-1);
				column = random.nextInt(lettersoup.length-1);
				space = verifySouthEastDiagonal(row, column, length);
				setSolution(temprow, tempcolumn, row, column);
				if(((lettersoup.length-1)-column<length)||(row+length>(lettersoup.length-1))){
					word.setColumn((lettersoup.length-1)-length);
					word.setRow((lettersoup.length-1)-length);
					row = word.getRow();
					column = word.getColumn();
					setSolution(temprow, tempcolumn, row, column);
				}
			}
			
			while(i<=length){
				c = Character.toString(word.getName().charAt(i));
				lettersoup[row][column] = c;
				i++;
				row++;
				column++;
			}
		}
	//_____________________________________________________________________________________________________________________________________
		/**
		 * 
		 * @param i
		 * @param j
		 * @param row
		 * @param column
		 */
		private void setSolution(int i,int j, int row, int column) {
			for(Word word : solution) {
				if(word.getRow()==i && word.getColumn()==j) {
					word.setRow(row);
					word.setColumn(column);
				}
			}
		}
	//_____________________________________________________________________________________________________________________________________
		/**
		 * 
		 * @param word
		 * @return
		 */
		public boolean checkSolution(Word word) {
			boolean correct = false;
			System.out.println(word.getName());
			for(int i=0;i<solution.length&&correct==false;i++) {
				if(solution[i].getName().equals(word.getName())) {
					System.out.println("correcta");
					correct = true;
				}
			}
			return correct;
		}
	//_____________________________________________________________________________________________________________________________________
		/**
		 * 
		 * @return
		 */
		private int countSolutions() {
			int size=0;
			for(int i=0;i<solution.length;i++) {
				if(solution[i]!=null) {
					size++;
				}
			}
			return size;
		}
	//_____________________________________________________________________________________________________________________________________
		/**
		 * 
		 * @param i
		 * @return
		 */
		public Direction getDirection(int i) {
			Direction direction = null;
			for(Word word : solution) {
				if(word.getRow()==i) {
					direction = word.getDirection();
				}
			}
			return direction;
		}
	//_____________________________________________________________________________________________________________________________________
		/**
		 * 
		 * @param i
		 * @return
		 */
		public int getLength(int i) {
			int length = 0;
			for(Word word : solution) {
				if(word.getRow()==i) {
					length = word.getLength()-1;
				}
			}
			return length;
		}
//_________________________________________________________________________________________________________________________________________
}
