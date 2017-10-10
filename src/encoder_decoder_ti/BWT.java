package encoder_decoder_ti;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Burrows Wheeler Transform
 */
public final class BWT {
  private static String encode(String rawString) {
    String[] strs = new String[rawString.length()];
    for (int i = 0; i < strs.length; ++i) {
      strs[i] = rawString.substring(i) + rawString.substring(0, i);
    }

    java.util.Arrays.sort(strs);

    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < strs.length; ++i) {
      builder.append(strs[i].charAt(strs[i].length() - 1));
    }
    return builder.toString();
  }

  private static final class BWTComparator implements java.util.Comparator<Integer> {
    private final String string;

    BWTComparator(String string) {
      this.string = string;
    }

    @Override
    public int compare(Integer i, Integer j) {
      return string.charAt(i) - string.charAt(j);
    }

    public boolean equals(Integer i, Integer j) {
      return string.charAt(i) == string.charAt(j);
    }
  }

  private static String decode(String encodedString) {
    Integer[] indices = new Integer[encodedString.length()];
    for (int i = 0; i < indices.length; ++i) {
      indices[i] = i;
    }
    java.util.Arrays.sort(indices, new BWTComparator(encodedString));

    int startIndex = 0;
    for (; encodedString.charAt(startIndex) != '$'; ++startIndex);

    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < indices.length - 1; ++i) {
      startIndex = indices[startIndex];
      char c = encodedString.charAt(startIndex);
      builder.append(c);
    }
    return builder.toString();
  }
  
  public String text;

  public void setTxt(String txt){
	  this.text = txt;
  }
  
  public String getTxt(){
	  return this.text;
  }
  
  public static void main(String[] args) throws FileNotFoundException {
	  BWT bwt = new BWT();
	  List<String> linhas = new ArrayList<String>();
      Scanner scanner = new Scanner(new File("src/LZW_gen.txt"));

	  while (scanner.hasNextLine()) {
	      //Lemos a linha
	      linhas.add(scanner.nextLine());
	  }
	  for(int i = 0; i < linhas.size(); i++){
		  bwt.setTxt(bwt.getTxt() + linhas.get(i));
	  }
    String encodedString = BWT.encode(bwt.getTxt() + '$');
    System.out.println(encodedString);
    String decodedString = BWT.decode(encodedString);
    System.out.println(decodedString);
  }
}