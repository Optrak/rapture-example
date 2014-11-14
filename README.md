 Project for testing rapture-json deserialization of quoted and unquoted ints
==============================================================================

There is one test file. The json contains an array of two Persons, one of which has an int value quoted, and the other, both quoted and unquoted int values. There is a special implicit BasicExtractor[Int, Json] to handle quoted ints. Deserialization succeeds for a Person with a quoted int, but fails for the one with a mix of quoted and unquoted ints. Is it possible to deserialize ints regardless of whether they are quoted?