[![Build Status](https://travis-ci.org/Bobzone/simple-search-engine.svg?branch=master)](https://travis-ci.org/Bobzone/simple-search-engine)
# Java assignment - Simple search engine

The goal of this assignment is to create a simple search engine in Java. The search engine should be
implemented as an inverted index that runs in memory and can
return a list that is sorted by TF-IDF

The search engine should:
* be able to take in a list of documents
* support searches for single terms in the document set
* return a list of matching documents sorted by TF-IDF
	
## Example

The following documents are indexed:
Document 1: "the brown fox jumped over the brown dog"
Document 2: "the lazy brown dog sat in the corner"
Document 3: "the red fox bit the lazy dog"

A search for "brown" should now return the list: [document 1, document 2]
A search for "fox" should now return the list: [document 1, document 3]

NOTE:
The search engine does not need to persist the index to disk. a simple implementation in memory is fine.
A document need only be a simple string. No GUI is needed but you should be able to write a query and get a result back.
