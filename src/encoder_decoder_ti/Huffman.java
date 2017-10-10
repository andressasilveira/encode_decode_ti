package encoder_decoder_ti;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeMap;

/* Huffman coding , decoding */

public class Huffman {
    static final boolean readFromFile = true;
    static final boolean newTextBasedOnOldOne = false;

    static PriorityQueue<Node> nodes = new PriorityQueue<>((o1, o2) -> (o1.value < o2.value) ? -1 : 1);
    static TreeMap<Character, String> codes = new TreeMap<>();
    static String text = "";
    static String encoded = "";
    static String decoded = "";
    static int ASCII[] = new int[128];

    public static void main(String[] args) throws FileNotFoundException {
    	BWT bwtfile = new BWT();
        Scanner scanner = (readFromFile) ? new Scanner(new File("src/alice29.txt")) : new Scanner(System.in);
        int decision = 1;
        while (decision != -1) {
            if (handlingDecision(scanner, decision)) continue;
            decision = consoleMenu(scanner);
        }
    }

    private static int consoleMenu(Scanner scanner) {
        return -1;
    }

    private static boolean handlingDecision(Scanner scanner, int decision) {
        if (decision == 1) {
            if (handleNewText(scanner)) return true;
        } else if (decision == 2) {
            if (handleEncodingNewText(scanner)) return true;
        } else if (decision == 3) {
            handleDecodingNewText(scanner);
        }
        return false;
    }

    private static void handleDecodingNewText(Scanner scanner) {
        System.out.println("Enter the text to decode:");
        encoded = scanner.nextLine();
        System.out.println("Text to Decode: " + encoded);
        decodeText();
    }

    private static boolean handleEncodingNewText(Scanner scanner) {
    	List<String> linhas = new ArrayList<String>();
        System.out.println("Enter the text to encode:");
        
        while (scanner.hasNextLine()) {
            //Lemos a linha
            linhas.add(scanner.nextLine());
        }
        for(int i = 0; i < linhas.size(); i++){
        	text = text + linhas.get(i);
        }
        System.out.println("Text to Encode: " + text);

        if (!IsSameCharacterSet()) {
            System.out.println("Not Valid input");
            text = "";
            return true;
        }
        encodeText();
        return false;
    }

    private static boolean handleNewText(Scanner scanner) {
    	List<String> linhas = new ArrayList<String>();
        int oldTextLength = text.length();
        System.out.println("Enter the text:");
        while (scanner.hasNextLine()) {
            //Lemos a linha
            linhas.add(scanner.nextLine());
        }
        for(int i = 0; i < linhas.size(); i++){
        	text = text + linhas.get(i);
        }
        if (newTextBasedOnOldOne && (oldTextLength != 0 && !IsSameCharacterSet())) {
            System.out.println("Not Valid input");
            text = "";
            return true;
        }
            ASCII = new int[128];
            nodes.clear();
            codes.clear();
            encoded = "";
            decoded = "";
            System.out.println("Text: " + text);
            calculateCharIntervals(nodes, true);
            buildTree(nodes);
            generateCodes(nodes.peek(), "");

            printCodes();
            System.out.println("-- Encoding/Decoding --");
            encodeText();
            decodeText();
            return false;



    }

    private static boolean IsSameCharacterSet() {
        boolean flag = true;
        for (int i = 0; i < text.length(); i++)
            if (ASCII[text.charAt(i)] == 0) {
                flag = false;
                break;
            }
        return flag;
    }

    private static void decodeText() {
        decoded = "";
        Node node = nodes.peek();
        for (int i = 0; i < encoded.length(); ) {
            Node tmpNode = node;
            while (tmpNode.left != null && tmpNode.right != null && i < encoded.length()) {
                if (encoded.charAt(i) == '1')
                    tmpNode = tmpNode.right;
                else tmpNode = tmpNode.left;
                i++;
            }
            if (tmpNode != null)
                if (tmpNode.character.length() == 1)
                    decoded += tmpNode.character;
                else
                    System.out.println("Input not Valid");

        }
        System.out.println("Decoded Text: " + decoded);
    }

    private static void encodeText() {
        encoded = "";
        String bff = "";
        BufferedWriter writer = null;
        char chartAt;
        for (int i = 0; i < text.length(); i++){
        	chartAt = text.charAt(i);
        	bff += String.valueOf(codes.get(chartAt));
            encoded += codes.get(chartAt);
        }
        System.out.println("Encoded Text: " + encoded);
        
     // get the content in bytes
        
        try
        {
            writer = new BufferedWriter( new FileWriter("src/Huffman_gen.txt"));
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
    }

    private static void buildTree(PriorityQueue<Node> vector) {
        while (vector.size() > 1)
            vector.add(new Node(vector.poll(), vector.poll()));
    }

    private static void printCodes() {
        System.out.println("--- Printing Codes ---");
        codes.forEach((k, v) -> System.out.println("'" + k + "' : " + v));
    }

    private static void calculateCharIntervals(PriorityQueue<Node> vector, boolean printIntervals) {
        if (printIntervals) System.out.println("-- intervals --");

        for (int i = 0; i < text.length(); i++)
            ASCII[text.charAt(i)]++;

        for (int i = 0; i < ASCII.length; i++)
            if (ASCII[i] > 0) {
                vector.add(new Node(ASCII[i] / (text.length() * 1.0), ((char) i) + ""));
                if (printIntervals)
                    System.out.println("'" + ((char) i) + "' : " + ASCII[i] / (text.length() * 1.0));
            }
    }

    private static void generateCodes(Node node, String s) {
        if (node != null) {
            if (node.right != null)
                generateCodes(node.right, s + "1");

            if (node.left != null)
                generateCodes(node.left, s + "0");

            if (node.left == null && node.right == null)
                codes.put(node.character.charAt(0), s);
        }
    }
}

class Node {
    Node left, right;
    double value;
    String character;

    public Node(double value, String character) {
        this.value = value;
        this.character = character;
        left = null;
        right = null;
    }

    public Node(Node left, Node right) {
        this.value = left.value + right.value;
        character = left.character + right.character;
        if (left.value < right.value) {
            this.right = right;
            this.left = left;
        } else {
            this.right = left;
            this.left = right;
        }
    }
}