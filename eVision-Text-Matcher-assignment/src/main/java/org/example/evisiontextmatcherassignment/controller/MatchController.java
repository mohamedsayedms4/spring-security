package org.example.evisiontextmatcherassignment.controller;

import lombok.RequiredArgsConstructor;
import org.example.evisiontextmatcherassignment.model.MatchResult;
import org.example.evisiontextmatcherassignment.service.TextMatchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * REST Controller to expose text matching endpoints.
 */
@RestController
@RequestMapping("/api/match")
@RequiredArgsConstructor
public class MatchController {

    private final TextMatchService textMatchService;

    /**
     * Returns a list of all similarity matches sorted by descending similarity.
     *
     * @return list of {@link MatchResult}
     * @throws IOException if file reading fails
     */
    @GetMapping("/all")
    public List<MatchResult> getAllMatches() throws IOException {
        return textMatchService.calculateSimilarity();
    }

    /**
     * Returns the best match from the pool.
     *
     * @return the {@link MatchResult} with highest similarity, or null if none
     * @throws IOException if file reading fails
     */
    @GetMapping("/best")
    public MatchResult getBestMatch() throws IOException {
        List<MatchResult> results = textMatchService.calculateSimilarity();
        return results.isEmpty() ? null : results.get(0);
    }
}
