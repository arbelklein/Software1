# Assignment 5

## Description
The assignment involves building a basic language model in Java using bigrams (pairs of consecutive words) from a given text.

## Output
The program produces various outputs based on the tasks it performs:
1. Vocabulary Index:
    - Builds a vocabulary index from a given text file, containing up to 14,500 unique words.
2. Bigram Counts Array:
    - Constructs a 2D array representing the counts of bigram occurrences in the text.
3. Model Saving and Loading:
    - Saves the vocabulary and bigram counts to files.
    - Loads the model from saved files.
4. Bigram Queries:
    - Retrieves the index of a word in the vocabulary.
    - Gets the count of a specific bigram.
    - Finds the most frequent word following a given word.
    - Checks if a sentence is legal based on the bigram model.
5. Cosine Similarity:
    - Calculates the cosine similarity between two vectors representing word contexts.
6. Closest Word:
    - Finds the word most similar to a given word based on the cosine similarity of their context vectors.
