package org.example.evisiontextmatcherassignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchResult {
    /** The name of the file being compared */
    private String fileName;

    /** Similarity percentage between the reference file and this file */
    private double similarity;
}
