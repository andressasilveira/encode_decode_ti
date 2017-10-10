package encoder_decoder_ti;

/*
 * Peter Edge
 * CSCI 5481 Final Project
 * 
 * This file contains the Permutation class.
 * It is used while forming the burrow-wheeler transform
 * to hold a single BWT matrix permutation.
 * The main purpose of this class is twofold:
 * 
 * 1. need to hold the sequence of the permutation
 * 2. need to hold on to the original index from before sequence is lexically sorted
 * 
 * This is bc java has no convenient way to get the indeces of a sort operation, as MATLAB does.
*/

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.StringBuilder;

// needs to implement Comparable so that it can be sorted in BWT

public class Permutation implements Comparable<Permutation>{

    // fasta header of the original short read
    String sequence;
    Integer index; // will be sorted, need to hold onto original index

    Permutation(){};

    Permutation(String s, Integer i){
        sequence = s;
        index = i;
    }

    // permutations need to be sorted
    @Override
    public int compareTo(Permutation other){
        return this.sequence.compareTo(other.sequence);
    }
}