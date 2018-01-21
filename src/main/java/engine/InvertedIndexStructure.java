package engine;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import domain.Term;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class InvertedIndexStructure {

    private static Logger logger = LogManager.getLogger(InvertedIndexStructure.class);
    private Multimap<String, Term> mappings = ArrayListMultimap.create();

    public InvertedIndexStructure(List<File> files) {
        if (null == files) {
            throw new IllegalArgumentException("Null passed as an argument for structure building. ");
        }
        files.forEach(this::putIntoMappings);
    }

    public InvertedIndexStructure(File file) {
        putIntoMappings(file);
    }

    /**
     * Return the list of documents (filenames) where given term occurs sorted by tf-idf
     *
     * @param query Word to look for in the index structure
     * @return List of files where given query was found (just the filenames)
     */
    public List<String> find(String query) {
        if (null == query) {
            throw new IllegalArgumentException("Not possible to execute query-based search with empty query. ");
        }
        final long start = System.currentTimeMillis();

        final List<Term> terms = findPaths(query);
        applyTfidfSort(terms);
        final long end = System.currentTimeMillis();

        logger.debug("Find with query: [{}] finished in {} ms.", query, end - start);
        return getFilenames(terms);
    }

    private void putIntoMappings(File file) {
        try (Scanner scanner = new Scanner(file)) {
            scanner.useDelimiter(" +");
            while (scanner.hasNext()) {
                String word = scanner.next();
                if (mappings.containsKey(word) && wordComesFromFile(word, file)) {
                    addCountInMapForWord(word, file);
                } else {
                    mappings.put(word, new Term(file));
                }
            }
        } catch (FileNotFoundException e) {
            logger.warn("Error during opening file: ", e);
        }
    }


    /**
     * BufferedReader version for better performance in bigger documents
     *
     * @param file
     * @return
     */
    private void putIntoMappingsBuffered(File file) {
        try (FileReader fr = new FileReader(file);
             BufferedReader reader = new BufferedReader(fr);) {
            String line = reader.readLine();
            while (line != null) {
                final String[] split = line.split(" ");
                Arrays.stream(split)
                        .forEach(word -> {
                            if (mappings.containsKey(word) && wordComesFromFile(word, file)) {
                                addCountInMapForWord(word, file);
                            } else {
                                mappings.put(word, new Term(file));
                            }
                        });
                line = reader.readLine();
            }
        } catch (IOException e) {
            logger.warn("IO Error: ", e);
        }
    }

    private boolean wordComesFromFile(final String word, final File file) {
        return mappings.get(word)
                .stream()
                .anyMatch(term -> term.getSource() == file);
    }

    private void addCountInMapForWord(final String word, final File file) {
        mappings.get(word).stream()
                .filter(it -> it.getSource().equals(file))
                .findFirst()
                .ifPresent(Term::addCount);
    }

    private List<Term> findPaths(String query) {
        return (List<Term>) mappings.get(query);
    }


    private void applyTfidfSort(final List<Term> terms) {
        terms.sort(Comparator.comparingInt(Term::getNrOfOccurences).reversed());
    }

    private List<String> getFilenames(final List<Term> paths) {
        return paths
                .stream()
                .map(Term::getSource)
                .map(File::getName)
                .collect(Collectors.toList());
    }
}
