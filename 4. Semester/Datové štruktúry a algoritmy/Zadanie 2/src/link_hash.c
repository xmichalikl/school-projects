#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include "link_hash.h"

/*
typedef struct data { 
    char name[50];
    int age;
    struct data *next;
} DATA;

typedef struct hashTable {
	int size;		
	struct data *data;
}HASHTABLE;*/

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


int hashFunction(DATA *data) {

    int hash = 0;

    for (int i = 0; i< strlen(data->name); i++) 
        hash = 43*hash + ((data->name)[i]);
    
    return abs(hash % hTableSize);
}

DATA *createNewData(char *newName, int newAge) {

    DATA *newData = malloc(sizeof(DATA));
    strcpy(newData->name, newName);
    newData->age = newAge;
    newData->next = NULL;

    return newData;
}

HASHTABLE *createHTable(int newHTableSize) {

    HASHTABLE *newHTable = (HASHTABLE*)malloc(newHTableSize * sizeof(HASHTABLE));

    for (int i = 0; i < newHTableSize; i++) {	
        (newHTable + i)->size = 0;
        (newHTable + i)->data = NULL;
	}
    hTableSize = newHTableSize;

    return newHTable;
}

HASHTABLE *resizeHTable(HASHTABLE *hTable) {
    
    int oldHTableSize = hTableSize;
    int newHTableSize = nextPrime(2 * hTableSize);

    //printf("newHTableSize = %d\n", newHTableSize);

    HASHTABLE *oldHTable = hTable;
    HASHTABLE *newHTable = createHTable(newHTableSize);
    DATA *oldData, *cpyData;

    for (int i = 0; i < oldHTableSize; i++) {
        oldData = (oldHTable + i)->data;
        if (oldData == NULL)
            continue;
        else {
            while (oldData != NULL) {

                /*cpyData = malloc(sizeof(DATA));
                strcpy(cpyData->name, oldData->name);
                cpyData->age = oldData->age;
                cpyData->next = NULL;
                newHTable = insertHTable(newHTable, cpyData);*/
                
                newHTable = insertHTable(newHTable, oldData);
                inserts--;
                cpyData = oldData;
                oldData = oldData->next;
                cpyData->next = NULL;
            }
        }
    }
    free(oldHTable);
    //freeHTable(&oldHTable, oldHTableSize);
    return newHTable;
}

HASHTABLE *insertHTable(HASHTABLE *hTable, DATA *newData) {

    int index = hashFunction(newData);
    HASHTABLE *pos = (hTable + index);
    DATA *dataPos;
    //newData->next = NULL;

    if (pos->data == NULL) {
        pos->data = newData;		
        pos->size = 1;
        inserts++;
        return hTable;		
	}
    else {
        dataPos = pos->data;
        while (dataPos->next != NULL) {
            if ( strcmp(dataPos->name, newData->name) == 0 ) {
                free(newData);
                return hTable;
            } 
            dataPos = dataPos->next;
        }
        if (dataPos->next == NULL) {
            if ( strcmp(dataPos->name, newData->name) == 0 ) {
                free(newData);
                return hTable;
            }
            inserts++;
            dataPos->next = newData;
            pos->size++;
        }
        float loadFactor = (float)(1.0 * pos->size / hTableSize);
        if (loadFactor >= 0.1) {
            //printf("Resize\n");
            hTable = resizeHTable(hTable);
        }
        return hTable;
    }       
}

DATA *searchHTable(HASHTABLE *hTable, DATA *findData) {

	int index = hashFunction(findData);		
    HASHTABLE *pos = (hTable + index);
    DATA *dataPos = pos->data;

	for (int i = 0; i < pos->size; i++) {
		if ( strcmp(dataPos->name, findData->name) == 0 && dataPos->age == findData->age) {
            free(findData);
            return dataPos;
        }
		dataPos = dataPos->next;
	}
	return NULL;
}

int isPrime(int num) {

    if ( (num & 1) == 0 )
        return num == 2;
    else {
        int i, limit = sqrt(num); //odmocnina
        for (i = 3; i <= limit; i+=2){
            if (num % i == 0)
                return 0;
        }
    }
    return 1;
}

int nextPrime(int num) {

    int nextPrimeNum;

    if (num < 2)
        nextPrimeNum = 2;
    else if (num == 2)
        nextPrimeNum = 3;  
    else if (num & 1){ //last bit check
        num += 2;
        nextPrimeNum = isPrime(num) ? num : nextPrime(num); //if else
    } 
    else {
        nextPrimeNum = nextPrime(num-1);
    }

    return nextPrimeNum;
}

void printHTable(HASHTABLE *hTable) {

    DATA *data;

    for (int i = 0; i < hTableSize; i++) {
        if ( (hTable+i)->size == 0 ) 
            continue;
        printf("[%d] = ", i);
        data = (hTable+i)->data;
        while (data != NULL) {
            printf("%s(%d) -> ", data->name, hashFunction(data));
            data = data->next;
        }
        printf("\n");
    }
}

/*void freeHTable(HASHTABLE **delHTable, int delHTableSize) {
    
    DATA **act, *next, *temp;

    for (int i = 0; i < delHTableSize; i++) {
        act = &(((*delHTable)+i)->data);
        temp = (*act);

        while ( temp != NULL ) {
            (*act) = NULL;
            act = &(temp->next);
            next = temp->next;
            free(temp);
            temp = next;
        }
    }
    free((*delHTable));
    *delHTable = NULL;
}*/

void freeHTable(HASHTABLE **delHTable, int delHTableSize) {

	DATA *data = NULL;
	DATA *delData = NULL;

	if (*delHTable == NULL) 
        return;

	for (int i = 0; i < delHTableSize; i++) {

		data = (*delHTable + i)->data;
		if (data != NULL) {
			while (data->next != NULL) {
				delData = data;
				data = data->next;
				free(delData);
			}
			free(data);
		}
	}
	free(*delHTable);
}

/*
int main() {    

    HASHTABLE *hTable; 
    HASHTABLE *test;
    hTable = createHTable(9);
    char name[50];
    int age = 0;


    for (int i = 0; i < 31; i++)  {
        scanf("%s", name);
        hTable = insertHTable(hTable, createNewData(name,age));
    }   
    printHTable(hTable);

    DATA **first, **second;

    first = &((hTable+0)->data);
    second = &((hTable+6)->data);

    printf("*%s\n", (*first));
    printf("*%s\n", (*second));

    freeHTable(&hTable, hTableSize);

    printf("*%s\n", (*first));
    printf("*%s\n", (*second));


    printf("END\n");
    return 0;
}*/