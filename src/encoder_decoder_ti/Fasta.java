package encoder_decoder_ti;

/*
 * Peter Edge
 * CSCI 5481 Final Project
 * 
 * This file contains the Fasta class, used to represent fasta sequences.
 *
*/

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.StringBuilder;

public class Fasta {

    // header and sequence of the fasta file
    String header;
    String sequence;

    // see if the sequence is being stored in reverse order of original
    boolean isReversed;

    Fasta(){};

    Fasta(String h, String s, boolean ir){
        header = h;
        sequence = s;
        isReversed = ir;
    }

    // reverse the sequence and set the isReversed tag.
    Fasta reverse(){
    	return new Fasta(header ,(new StringBuilder(sequence)).reverse().toString(), !isReversed);
    }

    @Override
    public String toString(){
        return ("header: " + header + "\n" + "sequence: " + sequence + "\n" + "isReversed: " + isReversed);
    }
}