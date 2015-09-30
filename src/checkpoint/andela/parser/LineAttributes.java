package checkpoint.andela.parser;

public class LineAttributes {
	
	private static String commentTag = "#";
	private static String separator = " - ";
	private static String chosenAttributes = "(UNIQUE-ID|TYPES|COMMON-NAME|ATOM-MAPPINGS"
			+ "|CANNOT-BALANCE?|ENZYMATIC-REACTION|ORPHAN?).*";
	
	public boolean isValidLine(String lineInFile) {
		if (!lineInFile.startsWith(commentTag) && !lineInFile.isEmpty() && lineInFile.contains(separator)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean lineHasselectedAttributes(String lineInFile) {
		if(lineInFile.matches(chosenAttributes)){
			return true;
		}
		else{
			return false;
		}
	}
}