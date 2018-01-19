package engine;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

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
    public List<File> search(String query) {
        return null;
    }
}
