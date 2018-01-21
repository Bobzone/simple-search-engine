package domain;

import java.io.File;

public class Term {

    private File source;
    private int nrOfOccurences = 1;

    public Term(final File source) {
        this.source = source;
    }

    public void addCount() {
        this.nrOfOccurences++;
    }

    public File getSource() {
        return source;
    }

    public int getNrOfOccurences() {
        return nrOfOccurences;
    }
}
