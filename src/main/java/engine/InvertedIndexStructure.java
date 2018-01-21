package engine;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class InvertedIndexStructure {

    private Multimap<String, File> mappings = ArrayListMultimap.create();

    public InvertedIndexStructure(List<File> files) {
        files.forEach(this::putIntoMappings);
    }

    public InvertedIndexStructure(File file) {
        putIntoMappings(file);
    }

    private void putIntoMappings(File file) {
        try (Scanner scanner = new Scanner(file)) {
            scanner.useDelimiter(" +");
            while (scanner.hasNext()) {
                mappings.put(scanner.next(), file);
            }
        } catch (FileNotFoundException e) {
            // TODO: logger.error() // warn() here
            System.out.println("Error during opening file. ");
            e.printStackTrace();
        }
    }

    /**
     * BufferedReader version for better performance in bigger documents
     *
     * @param query
     * @return
     */
//    private void putIntoMappings(File file) {
//        try (FileReader fr = new FileReader(file);
//             BufferedReader reader = new BufferedReader(fr);) {
//            String line = reader.readLine();
//            while (line != null) {
//                final String[] split = line.split(" ");
//                Arrays.stream(split)
//                        .forEach(s -> mappings.put(s, file));
//                line = reader.readLine();
//            }
//        } catch (IOException e) {
//            // TODO: logger.error() // warn() here
//            System.out.println("IO Error: " + e.getLocalizedMessage());
//            e.printStackTrace();
//        }
//    }
    private List<File> findPaths(String query) {
        return (List<File>) mappings.get(query);
    }

    /**
     * Return the list of documents (filenames) where given term occurs sorted by tf-idf
     *
     * @param query Word to look for in the index structure
     * @return List of files where given query was found (just the filenames)
     */
    public List<String> find(String query) {
        final List<File> paths = findPaths(query);

        return getFilenames(paths);
    }

    private List<String> getFilenames(final List<File> paths) {
        return paths
                .stream()
                .map(File::getName)
                .collect(Collectors.toList());
    }
}
