import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.time.LocalDateTime;

public class CustomProgram {
	public static void main(String[] args) throws Exception {
		// reads the provided customPage.html into list
		Scanner in = new Scanner(new File("customPage.html"));
		ArrayList<String> list = new ArrayList<>();
		while(in.hasNextLine()) list.add(in.nextLine());

		// update list to reflect changes requested through command line args
		// TODO: Complete this section
		if(args.length > 0) {
			String Name = "";

			for(String arg : args[0].split("&")) {
				String[] keyValuePair = arg.split("=");
				switch(keyValuePair[0]) {
				case "name":
					// TODO: when a greeting is selected (below), this
					// arguments's value should be displayed in that greeting
					if (keyValuePair.length == 2){
						Name = keyValuePair[1];
					}
					break;
				case "background":
					// TODO: when background="Dark", the body's color should be
					// set to white and it's background-color should be set to
					// black (the opposite of how they are set for "Light" by
					// default.
					if (keyValuePair[1].equals("Dark")) {
						int blackIndex = -1;
						int whiteIndex = -1;
						for (int i=0; i<list.size(); i++) {
							if (list.get(i).contains("black")) {
								blackIndex = i;
							}
							else if (list.get(i).contains("white")) {
								whiteIndex = i;
							}

							if (blackIndex != -1 && whiteIndex != -1) {
								break;
							}
						}	
						list.set(blackIndex, list.get(blackIndex).replace("black", "white"));
						list.set(whiteIndex, list.get(whiteIndex).replace("white", "black"));
					}
					break;
				case "Greeting":
					// TODO: when this argument is present and =true, an <h1>
					// element containing the text "Welcome Stranger" should
					// be inserted as the first element within the body.  If
					// a non-empty-string name is provided (see above), that
					// name should be used in place of the word Stranger in
					// this greeting.
					if (keyValuePair[1].equals("true")) {
						int index = -1;
						for (int i=0; i<list.size(); i++) {
							if (list.get(i).contains("<body>")) {
								index = i;
								break;
							}
						}
						if (!Name.equals("")) {
							list.add(index+1, "<h1>Welcome " + Name + "</h1>");
						}
						else {
							list.add(index+1, "<h1>Welcome Stranger</h1>");
						}
					}
					break;
				case "Time":
					// TODO: when this argument is present and =true, a <p>
					// element containing the text: "Page Updated: date-time"
					// should be inserted as the last element within the body.
					// Note that the date-time part of this paragraph should
					// be dynamically generated by calling
					// java.time.LocalDateTime.now()
					if (keyValuePair[1].equals("true")) {
						int index = -1;
						for (int i=0; i<list.size(); i++) {
							if (list.get(i).contains("</body>")) {
								index = i;
								break;
							}
						}
						LocalDateTime clock = java.time.LocalDateTime.now();	
						String date = clock.toLocalDate().toString();
						String time = clock.toLocalTime().toString();
						date = date.replace('-', '/');
						time = (String) time.subSequence(0, 8);
						list.add(index, "<p>Page Updated: " + date + "-" + time + "</p>");
					}
					break;
				case "SuppressOptions":
					// TODO: when this argument is present and =true, the
					// customization controls should be removed from the page.
					// Everything from and including the <h1> label through the
					// final </ul> should be omitted to accomplish this.
					if (keyValuePair[1].equals("true")) {
						int firstIndex = -1;
						int lastIndex = -1;
						for (int i=0; i<list.size(); i++) {
							if (list.get(i).contains("<h1>")) {
								firstIndex = i;
								break;
							}
						}

						for (int i=list.size()-1; i>=0; i--) {
							if (list.get(i).contains("</ul>")) {
								lastIndex = i;
								break;
							}
						}

						for (int i=0; i<lastIndex-firstIndex+1; i++) {
							list.remove(firstIndex);
						}
					}
					break;
				}
			}
		}

		// print the resulting html out to system.out (standard out)
		for(String line : list)
			System.out.println(line);
	}

}

