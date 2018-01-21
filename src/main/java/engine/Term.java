package engine;

import java.io.File;

class Term {

    private File source;
    private int nrOfOccurences = 0;

    public Term(final File source) {
        this.source = source;
    }

    public void addCount() {
        this.nrOfOccurences++;
    }

    public File getSource() {
        return source;
    }
}
