package org.example.evisiontextmatcherassignment.service;

import org.example.evisiontextmatcherassignment.model.MatchResult;
import java.io.IOException;
import java.util.List;

public interface TextMatchService {


    List<MatchResult> calculateSimilarity() throws IOException;
}
