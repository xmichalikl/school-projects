#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#include "avl_tree.h"
#include "link_hash.h"

char *fillString(char *string, int len);
void createStrings(char ***strings, int count);
void freeStrings(char ***strings, int count);
void treeTest(char **strings, int *numbers, int count);
void hTableTest(char **strings, int *numbers, int count);

char *fillString(char *string, int len) {

    char alphabet[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    int alphaPos;

    for (int i = 0; i < len; i++) {
        alphaPos = (rand() % (strlen(alphabet))) ;
        string[i] = alphabet[alphaPos];
    }
    return string;
}

int *fillNumbers(int *numbers, int count) {

    int number, lower = 0, upper = 100;

    numbers = (int*) malloc(count * sizeof(int));

    for (int i = 0; i < count; i++) 
        numbers[i] = (rand() % (upper - lower + 1)) + lower;
    
    return numbers;
}

void createStrings(char ***strings, int count) {

    int len, lower = 1, upper = 50;

    *strings = (char **) calloc(count , sizeof(char *));

    for (int i = 0; i < count; i++) {
        len = (rand() % (upper - lower + 1)) + lower;
        (*strings)[i] = (char *) calloc(len + 1 , sizeof(char));
        fillString((*strings)[i], len);
    }
}

void freeStrings(char ***strings, int count) {
    for (int i = 0; i < count; i++)
        free((*strings)[i]);
    free(*strings); 
}

void treeTest(char **strings, int *numbers, int count) {

    TREE *tree = NULL;

    clock_t time;
    time = clock();

    for (int i = 0; i < count; i++) 
        tree = insertTree(tree, strings[i], numbers[i]);

    time = clock() - time;
    double duration = ((double)time) / CLOCKS_PER_SEC; // in seconds
    printf("Insert AVL tree test pre %d prvkov trval %f sekund\n", count, duration);

    /////////////////////////////////////////////////////////////////////////////////

    TREE *find;
    time = clock();
    for (int i = 0; i < count; i++) 
        find = searchTree(tree, strings[i], numbers[i]);

    time = clock() - time;
    duration = ((double)time) / CLOCKS_PER_SEC; // in seconds
    printf("Search AVL tree test pre %d prvkov trval %f sekund\n", count, duration);

    freeTree(&tree);
}

void hTableTest(char **strings, int *numbers, int count) { 

    HASHTABLE *hTable = createHTable(nextPrime(10*count));
    inserts = 0;

    clock_t time;
    time = clock();

    for (int i = 0; i < count; i++) 
        hTable = insertHTable(hTable, createNewData(strings[i], numbers[i]));

    time = clock() - time;
    double duration = ((double)time) / CLOCKS_PER_SEC; // in seconds
    printf("Insert HASH table test pre %d prvkov trval %f sekund\n", count, duration);

    ///////////////////////////////////////////////////////////////////////////////////

    DATA *find;
    time = clock();
    
    for (int i = 0; i < count; i++) 
        find = searchHTable(hTable, createNewData(strings[i], numbers[i]));

    time = clock() - time;
    duration = ((double)time) / CLOCKS_PER_SEC; // in seconds
    printf("Search HASH table test pre %d prvkov trval %f sekund\n", count, duration);


    freeHTable(&hTable, hTableSize);
}

int main() {
    
    int count = 1000000;
    char **strings;
    int *numbers;

    srand(time(NULL));
    createStrings(&strings, count);
    numbers = fillNumbers(numbers, count);

    treeTest(strings, numbers, count);
    hTableTest(strings, numbers, count);
    
    freeStrings(&strings, count);
    free(numbers);
    printf("END OK");
    return 0;
}