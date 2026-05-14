package org.htw.prog2.aufgabe1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class SeqFile {

    private HashSet<String> sequences = new HashSet<>();
    private String firstSequence = "";
    private boolean valid = false;

    /**
     * Reads the specified FASTA file and stores sequences. In case the file does not exist or is not a valid FASTA
     * file, the Constructor does not throw an Exception. Instead, isValid() on the resulting object will return false.
     * @param filename
     */
    public SeqFile(String filename) {
        valid = readFile(filename);
    }

    /**
     * Reads the specified FASTA file.
     * @param filename The path to the FASTA file
     * @return false if the file could not be parsed (wrong format, does not exist), true otherwise.
     */
    private boolean readFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            StringBuilder seq = new StringBuilder();
            boolean headerFound = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    return false;
                }

                if (line.startsWith(">")) {
                    if (headerFound) {
                        if (addSequence(seq) == 0) {
                            return false;
                        }
                        seq = new StringBuilder();
                    }

                    headerFound = true;
                } else {
                    if (!headerFound) {
                        return false;
                    }

                    seq.append(line);
                }
            }

            if (!headerFound) {
                return false;
            }

            return addSequence(seq) > 0;

        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Adds the sequence in the passed StringBuilder to the internal hash set and also sets the first sequence if it
     * is still empty.
     * @param seq SequenceBuilder to get the sequence from.
     * @return The length of the added sequence.
     */
    private int addSequence(StringBuilder seq) {
        String sequence = seq.toString();

        if (sequence.length() > 0) {
            sequences.add(sequence);

            if (firstSequence.isEmpty()) {
                firstSequence = sequence;
            }
        }

        return sequence.length();
    }

    /**
     *
     * @return The number of sequences read from the FASTA file, or 0 if isValid() is false.
     */
    public int getNumberOfSequences() {
        if (!isValid()) {
            return 0;
        }

        return sequences.size();
    }

    /**
     *
     * @return The sequences read from the FASTA file, or an empty HashSet if isValid() is false.
     */
    public HashSet<String> getSequences() {
        if (!isValid()) {
            return new HashSet<>();
        }

        return sequences;
    }

    /**
     *
     * @return The first sequence read from the FASTA file, or an empty String if isValid() is false.
     */
    public String getFirstSequence() {
        if (!isValid()) {
            return "";
        }

        return firstSequence;
    }

    /**
     *
     * @return true if the FASTA file was read successfully, false otherwise.
     */
    public boolean isValid() {
        return valid;
    }
}