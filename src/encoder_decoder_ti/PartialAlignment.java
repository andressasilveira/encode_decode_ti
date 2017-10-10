package encoder_decoder_ti;

/*
 * Peter Edge
 * CSCI 5481 Final Project
 * 
 * This file contains the Partial Alignment class.
 * This class is used to hold info about a read that partially maps to an exon.
*/

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.StringBuilder;

public class PartialAlignment {

    // info about the exon and read that comprise the partial alignment
    String exonHeader;
    Integer exonIndex;
    String readHeader;
    // is the partial alignment from a reversed read?
    Boolean isReversed;
    // start and end indices on the original read and exon
    Integer overhang;

    PartialAlignment(){};

    PartialAlignment(String eh, Integer ei, String rh, boolean ir, Integer oh){

        exonHeader = eh;
        exonIndex = ei;
        readHeader = rh;
        isReversed = ir;
        overhang = oh;
    }

    @Override
    public String toString(){
        return ("exonHeader: " + exonHeader + "\n" + "exonIndex: " + exonIndex + "\n" + "readHeader: " + readHeader + "\n" + "isReversed: " + isReversed + "\n" + "overhang: " + overhang);
    }
}