package org.example.evisiontextmatcherassignment.service.implementation;

import org.example.evisiontextmatcherassignment.model.MatchResult;
import org.example.evisiontextmatcherassignment.service.TextMatchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TextMatchServiceImpl implements TextMatchService {

    /** Path to the reference file (File A) */
    @Value("${fileA.path}")
    private String fileAPath;

    /** Directory that contains all the other text files (the pool) */
    @Value("${pool.dir}")
    private String poolDir;

    /**
     * Main function of this service:
     * - Reads all words from the reference file (file A).
     * - Reads all words from each file in the pool directory.
     * - Calculates similarity between file A and each pool file.
     * - Returns the results sorted from most similar to least similar.
     */
    @Override
    public List<MatchResult> calculateSimilarity() throws IOException {

        Set<String> wordsA = readWordsFromFile(new File(fileAPath));

        File dir = new File(poolDir);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));

        if (files == null) {
            throw new IOException("Pool directory not found: " + poolDir);
        }

        List<MatchResult> results = new ArrayList<>();

        for (File file : files) {
            Set<String> wordsFile = readWordsFromFile(file);
            double similarity = calculateSimilarity(wordsA, wordsFile);

            results.add(new MatchResult(file.getName(), similarity));
        }

        return results.stream()
                .sorted((a, b) -> Double.compare(b.getSimilarity(), a.getSimilarity()))
                .collect(Collectors.toList());
    }

    /**
     * Reads all words from a given file using File I/O.
     */
    private Set<String> readWordsFromFile(File file) throws IOException {
        Set<String> words = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] tokens = line.split("\\W+");

                for (String token : tokens) {
                    if (token.matches("[a-zA-Z]+")) {
                        words.add(token.toLowerCase());
                    }
                }
            }
        }
        return words;
    }

    /**
     * Calculates similarity as:
     * (number of common words between two files ÷ number of words in reference file) × 100
     */
    private double calculateSimilarity(Set<String> wordsA, Set<String> wordsFile) {
        if (wordsA.isEmpty()) return 0.0;

        Set<String> intersection = new HashSet<>(wordsA);
        intersection.retainAll(wordsFile);

        return ((double) intersection.size() / wordsA.size()) * 100;
    }
}
