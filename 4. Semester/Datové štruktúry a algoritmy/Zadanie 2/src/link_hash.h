#ifndef LINK_HASH_H
#define LINK_HASH_H

typedef struct data { 
    char name[50];
    int age;
    struct data *next;
} DATA;

typedef struct hashTable {
	int size;		
	struct data *data;
}HASHTABLE;

int hTableSize;
int inserts;

int hashFunction(DATA *data);
DATA *createNewData(char *newName, int newAge);
HASHTABLE *createHTable(int newHTableSize);
HASHTABLE *resizeHTable(HASHTABLE *hTable);
HASHTABLE *insertHTable(HASHTABLE *hTable, DATA *newData);
DATA *searchHTable(HASHTABLE *hTable, DATA *findData);
int isPrime(int num);
int nextPrime(int num);
void printHTable(HASHTABLE *hTable);
void freeHTable(HASHTABLE **delHTable, int delHTableSize);

#endif