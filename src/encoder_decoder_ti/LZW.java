package encoder_decoder_ti;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class LZW {
    /** Compress a string to a list of output symbols. */
    public static List<Integer> compress(String uncompressed) {
        // Build the dictionary.
        int dictSize = 256;
        Map<String,Integer> dictionary = new HashMap<String,Integer>();
        for (int i = 0; i < 256; i++)
            dictionary.put("" + (char)i, i);
 
        String w = "";
        List<Integer> result = new ArrayList<Integer>();
        for (char c : uncompressed.toCharArray()) {
            String wc = w + c;
            if (dictionary.containsKey(wc))
                w = wc;
            else {
                result.add(dictionary.get(w));
                // Add wc to the dictionary.
                dictionary.put(wc, dictSize++);
                w = "" + c;
            }
        }
 
        // Output the code for w.
        if (!w.equals(""))
            result.add(dictionary.get(w));
        return result;
    }
 
    /** Decompress a list of output ks to a string. */
    public static String decompress(List<Integer> compressed) {
        // Build the dictionary.
        int dictSize = 256;
        Map<Integer,String> dictionary = new HashMap<Integer,String>();
        for (int i = 0; i < 256; i++)
            dictionary.put(i, "" + (char)i);
 
        String w = "" + (char)(int)compressed.remove(0);
        StringBuffer result = new StringBuffer(w);
        for (int k : compressed) {
            String entry;
            if (dictionary.containsKey(k))
                entry = dictionary.get(k);
            else if (k == dictSize)
                entry = w + w.charAt(0);
            else
                throw new IllegalArgumentException("Bad compressed k: " + k);
 
            result.append(entry);
 
            // Add w+entry[0] to the dictionary.
            dictionary.put(dictSize++, w + entry.charAt(0));
 
            w = entry;
        }
        return result.toString();
    }
 
    public static void main(String[] args) throws FileNotFoundException{
    	Scanner scanner = new Scanner(new File("src/Huffman_gen.txt"));
    	List<Integer> compressed = new ArrayList<Integer>();
    	List<Integer> currentComp;
    	String bff = "";
        BufferedWriter writer = null;
    	ArrayList<List<Integer>> compressedLines = new ArrayList<List<Integer>>();
    	
    	
    	 while (scanner.hasNextLine()) {
             //Lemos a linha
    		 currentComp = compress(scanner.nextLine());
    		 compressed = currentComp;
    		 compressedLines.add(compressed);
         }
    	for(int i = 0; i < compressedLines.size(); i++){
    		System.out.println(compressedLines.get(i));
    		bff += compressedLines.get(i).toString();
    	}
    	try
        {
            writer = new BufferedWriter( new FileWriter("src/LZW_gen.txt"));
            writer.write(bff);

        }
        catch ( IOException e)
        {
        }
        finally
        {
            try
            {
                if ( writer != null)
                writer.close( );
            }
            catch ( IOException e)
            {
            }
        }
    	
    	for(int i = 0; i < compressedLines.size(); i++){
    		if(compressedLines.get(i).size() > 0){
    			String decompressed = decompress(compressedLines.get(i));
    			 System.out.println(decompressed);
    		}
    	}
    }
}