#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <math.h>
#include <time.h>

typedef struct bf {
    char *vector;
    int vectorSize;
} BF;

typedef struct bdd {
    int variableCount;
    int elementCount;
    struct element *firstElement;
} BDD;

typedef struct element {
    int stage;
    char side;
    bool originalLeft;
    bool originalRight;
    BF booleanFunction;
    struct element *parentElement;
    struct element *neighborElement;
    struct element *leftElement;
    struct element *rightElement;
} ELEMENT;


int sizeOfInput(int variableCount);
char *createInput(int inputSize);
BF *createBooleanFunction(int variableCount);

BDD *BDD_create(BF *booleanFunction);
ELEMENT *createNewLeftElement(BDD *newBDD, ELEMENT *parent, int size, char *vector, int stage, ELEMENT **neighborStack);
ELEMENT *createNewRightElement(BDD *newBDD, ELEMENT *parent, int size, char *vector, int stage, ELEMENT **neighborStack);
char BDD_use(BDD *bdd, char *input);
int BDD_reduce(BDD *bdd);

int checkElements(ELEMENT *actualElement);
int checkElementsNew(ELEMENT *firstElement);
void deleteThis(ELEMENT *mainElement, ELEMENT *prevElement, ELEMENT *delElement, ELEMENT *nextElement);

void BDD_free(BDD *bdd);
void freeElement(ELEMENT *element);
void freeElementNew(ELEMENT **element);

char *decToBin(int size, int decNum);
char *decToBinNew(int size, int decNum);
void BDD_test(int variableCount, int BDDCount);


int sizeOfInput(int variableCount) {

    int inputSize = pow(2, variableCount);

    //vrati vypocitanu velkost vstupu
    return inputSize;
}

char *createInput(int inputSize) {

    char *newInput = malloc(inputSize * sizeof(char));

    for (int i = 0; i < inputSize; i++)
        newInput[i] = (rand() % 2);

    //vrati ukazovatel na nahodne vygenerovany input 
    return newInput;
}

BF *createBooleanFunction(int variableCount) {

    BF *newBF = malloc(sizeof(BF));

    newBF->vectorSize = sizeOfInput(variableCount);
    newBF->vector = createInput(newBF->vectorSize);

    //vrati ukazovatel na vytvorenu bf funkciu
    return newBF;
}


BDD *BDD_create(BF *booleanFunction) {

    BDD *newBDD;

    if (booleanFunction == NULL)
        return NULL;

    //vytvorenie noveho BDD
    newBDD = malloc(sizeof(BDD));

    ELEMENT *neighborStack[20];
    for (int i = 0; i < 20; i++)
        neighborStack[i] = NULL;


    int vectorSize = booleanFunction->vectorSize;
    char *vector = malloc(vectorSize * sizeof(char));

    //prekopirovanie vectora
    for (int i = 0; i < vectorSize; i++)
        vector[i] = booleanFunction->vector[i];

    newBDD->variableCount = log2(vectorSize);
    newBDD->elementCount = 1;


    //vytvorenie prveho elementu v BDD
    ELEMENT *firstElement = malloc(sizeof(ELEMENT));

    firstElement->stage = 0;
    firstElement->booleanFunction.vector = vector;
    firstElement->booleanFunction.vectorSize = vectorSize;

    firstElement->originalLeft = true;
    firstElement->originalRight = true;

    firstElement->parentElement = NULL;
    firstElement->neighborElement = NULL;

    firstElement->leftElement = createNewLeftElement(newBDD, NULL, vectorSize, vector, 1, neighborStack);
    firstElement->rightElement = createNewRightElement(newBDD, NULL, vectorSize, vector, 1, neighborStack);

    newBDD->firstElement = firstElement;

    //vratenie ukazovatela na uspesne vytvoreny BDD
    return newBDD;
}

ELEMENT *createNewLeftElement(BDD *newBDD, ELEMENT *parent, int size, char *vector, int stage, ELEMENT **neighborStack) {

    int newSize = size/2;

    if (size == 1)
        return NULL;

    char *newVector = malloc(newSize * sizeof(char));

    //prekopirovanie vektora
    for (int i = 0; i < newSize; i++) {
        newVector[i] = vector[i];
    }

    ELEMENT *newElement = malloc(sizeof(ELEMENT));

    newElement->stage = stage;
    newElement->side = 'l';
    newElement->booleanFunction.vector = newVector;
    newElement->booleanFunction.vectorSize = newSize;

    newElement->originalLeft = true;
    newElement->originalRight = true;

    newElement->parentElement = parent;
    newElement->leftElement = createNewLeftElement(newBDD, newElement, newSize, newVector, stage+1, neighborStack);
    newElement->rightElement = createNewRightElement(newBDD, newElement, newSize, newVector, stage+1, neighborStack);
    newElement->neighborElement = NULL;

    if (neighborStack[stage] == NULL)
        neighborStack[stage] = newElement;
    else {
        neighborStack[stage]->neighborElement = newElement;
        neighborStack[stage] = newElement;
    }

    //printf("IDEM\n");
    newBDD->elementCount++;

    return newElement;
}

ELEMENT *createNewRightElement(BDD *newBDD, ELEMENT *parent, int size, char *vector, int stage, ELEMENT **neighborStack) {

    int newSize = size/2;

    if (size == 1)
        return NULL;

    int pos = 0;
    char *newVector = malloc(newSize * sizeof(char));

    //prekopirovanie vektora
    for (int i = newSize; i < size; i++) {
        newVector[pos] = vector[i];
        pos++;
    }

    ELEMENT *newElement = malloc(sizeof(ELEMENT));

    newElement->stage = stage;
    newElement->side = 'r';
    newElement->booleanFunction.vector = newVector;
    newElement->booleanFunction.vectorSize = newSize;

    newElement->originalLeft = true;
    newElement->originalRight = true;

    newElement->parentElement = parent;
    newElement->leftElement = createNewLeftElement(newBDD, newElement, newSize, newVector, stage+1, neighborStack);
    newElement->rightElement = createNewRightElement(newBDD, newElement, newSize, newVector, stage+1, neighborStack);
    newElement->neighborElement = NULL;

    if (neighborStack[stage] == NULL)
        neighborStack[stage] = newElement;
    else {
        neighborStack[stage]->neighborElement = newElement;
        neighborStack[stage] = newElement;
    }

    newBDD->elementCount++;

    return newElement;
}

char BDD_use(BDD *bdd, char *input) {

    ELEMENT *actualElement;
    int inputSize;

    if (bdd == NULL)
        return 3;

    actualElement = bdd->firstElement;
    inputSize = bdd->variableCount;

    //postupne prechadzanie diagramu
    for (int i = 0; i < inputSize; i++) {
        if (input[i] == 0)
            actualElement = actualElement->leftElement;
        if (input[i] == 1)
            actualElement = actualElement->rightElement;
    }

    if (actualElement->booleanFunction.vectorSize > 1)
        return 4;
    else
        return actualElement->booleanFunction.vector[0];
}

int BDD_reduce(BDD *bdd) {

    ELEMENT *actualElement;
    int deletedElements;

    if (bdd == NULL) {
        return -1;
    }

    actualElement = bdd->firstElement;
    deletedElements = checkElements(actualElement);
    //deletedElements = checkElementsNew(actualElement);
    bdd->elementCount -= deletedElements;

    return deletedElements;
}


int checkElements(ELEMENT *actualElement) {

    if (actualElement->leftElement == NULL || actualElement->rightElement == NULL)
        return 0;

    ELEMENT *leftElement = actualElement->leftElement;
    ELEMENT *rightElement = actualElement->rightElement;
    int deletedElements = 0;

    //kontrola rovnosti vektorov
    bool sameVectors = true;
    for (int i = 0; i < leftElement->booleanFunction.vectorSize; i++) {
        if (leftElement->booleanFunction.vector[i] != rightElement->booleanFunction.vector[i]) {
            sameVectors = false;
            break;
        }
    }
    //rekurzivne prechadzanie uzlov
    if (sameVectors == true) {
        deletedElements = ((rightElement->booleanFunction.vectorSize * 2) - 1);
        freeElement(rightElement);
        //freeElementNew(&rightElement);
        actualElement->rightElement = actualElement->leftElement;
        deletedElements += checkElements(actualElement->leftElement);
    }
    if (sameVectors == false) {
        deletedElements += checkElements(actualElement->leftElement);
        deletedElements += checkElements(actualElement->rightElement);
    }
    return deletedElements;
}

int checkElementsNew(ELEMENT *firstElement) {

    ELEMENT *stageElement = firstElement->leftElement;
    ELEMENT *actualElement, *checkElement, *previousElement;
    bool sameVectors;
    int deletedElements = 0;

    while (stageElement != NULL) {
        actualElement = stageElement;

        while (actualElement != NULL) {
            checkElement = actualElement->neighborElement;
            previousElement = actualElement;

            while (checkElement != NULL) {

                //kontrola vektorov
                sameVectors = true;
                for (int i = 0; i < actualElement->booleanFunction.vectorSize; i++) {
                    if (actualElement->booleanFunction.vector[i] != checkElement->booleanFunction.vector[i]) {
                        sameVectors = false;
                        break;
                    }
                }
                if (sameVectors == true) {
                    previousElement->neighborElement = checkElement->neighborElement;
                    deletedElements += ((checkElement->booleanFunction.vectorSize * 2) - 1 );
                    deleteThis(actualElement, previousElement, checkElement, checkElement->neighborElement);
                    checkElement = previousElement->neighborElement;
                }
                else {
                    previousElement = checkElement;
                    checkElement = checkElement->neighborElement;
                }
            }

            actualElement = actualElement->neighborElement;
        }

        stageElement = stageElement->leftElement;
    }
    return deletedElements;
}

void deleteThis(ELEMENT *mainElement, ELEMENT *prevElement, ELEMENT *delElement, ELEMENT *nextElement) {

    ELEMENT *nextPrevElement;
    ELEMENT *nextNextElement;
    ELEMENT *parentElement;
    char delElementSide;

    //delElement je posledny
    if (nextElement == NULL) {
        nextPrevElement = prevElement->rightElement;
        while (nextPrevElement != NULL) {
            nextPrevElement->neighborElement = NULL;
            nextPrevElement = nextPrevElement->rightElement;
        }
        parentElement = delElement->parentElement;
        delElementSide = delElement->side;

        freeElement(delElement);
        //freeElementNew(&delElement);

        if (delElementSide == 'r') {
            parentElement->rightElement = mainElement;
            parentElement->originalRight = false;
        }
        if (delElementSide == 'l') {
            parentElement->leftElement = mainElement;
            parentElement->originalLeft = false;
        }
    }
    //delElement nie je posledny
    else {
        nextPrevElement = prevElement->rightElement;
        nextNextElement = nextElement->leftElement;

        while (nextPrevElement != NULL && nextNextElement != NULL) {
            nextPrevElement->neighborElement = nextNextElement;

            nextPrevElement = nextPrevElement->rightElement;
            nextNextElement = nextElement->leftElement;
        }
        parentElement = delElement->parentElement;
        delElementSide = delElement->side;

        freeElement(delElement);
        //freeElementNew(&delElement);

        if (delElementSide == 'r') {
            parentElement->rightElement = mainElement;
            parentElement->originalRight = false;
        }
        if (delElementSide == 'l') {
            parentElement->leftElement = mainElement;
            parentElement->originalLeft = false;
        }
    }

}


void BDD_free(BDD *bdd) {

    ELEMENT *firstElement;

    if (bdd != NULL) {
        firstElement = bdd->firstElement; 
        freeElement(firstElement);
        //freeElementNew(&firstElement);
        bdd->firstElement = NULL;
        free(bdd);
    }
}

void freeElement(ELEMENT *element) {

    //printf("VectorSize = %d\n", element->booleanFunction.vectorSize);

    if (element != NULL) {
        if (element->leftElement == element->rightElement) {
            if (element->leftElement != NULL && element->originalLeft == true) {
                freeElement(element->leftElement);
                element->leftElement = NULL;
                element->rightElement = NULL;
            }
        }
        else {
            if (element->leftElement != NULL && element->originalLeft == true) {
                freeElement(element->leftElement);
                element->leftElement = NULL;
            }
            if (element->rightElement != NULL && element->originalRight == true) {
                freeElement(element->rightElement);
                element->rightElement = NULL;
            }
        }

        element->parentElement = NULL;
        element->neighborElement = NULL;
        free(element->booleanFunction.vector);
        free(element);
    }
    else 
        printf("NULL pointer\n");
}

void freeElementNew(ELEMENT **element) {

    printf("VectorSize = %d\n", (*element)->booleanFunction.vectorSize);
    //ELEMENT **parentElement = &((*element)->parentElement);
    //char elementSide = (*element)->side;

    if ((*element)->leftElement == (*element)->rightElement) {
        if ((*element)->leftElement != NULL) {
            freeElementNew( &((*element)->leftElement) );
            //element->leftElement = NULL;
            //element->rightElement = NULL;
        }
    }
    else {
        if ((*element)->leftElement != NULL) {
            freeElementNew( &((*element)->leftElement) );
            //element->leftElement = NULL;
        }
        if ((*element)->rightElement != NULL) {
            freeElementNew( &((*element)->rightElement) );
            //element->rightElement = NULL;
        }
    }

    /*if (elementSide == 'r') {
        (*parentElement)->rightElement = NULL;
    }
    if (elementSide == 'l') {
        (*parentElement)->leftElement = NULL;
    }*/

    (*element)->parentElement = NULL;
    (*element)->neighborElement = NULL;

    free((*element)->booleanFunction.vector);
    free((*element));
    *element = NULL;
}


char *decToBin(int size, int decNum) {

    int switchNum;
    char *binNum = calloc(size, sizeof(char));

    for (int i = 0; decNum > 0; i++) {
        binNum[i] = (decNum % 2);
        decNum = (decNum / 2);
    }

    for (int i = 0; i < size/2; i++) {
        switchNum = binNum[i];
        binNum[i] = binNum[(size-1)-i];
        binNum[(size-1)-i] = switchNum;
    }

    //vrati prekonvertovane 10-tkove cislo v bin tvare
    return binNum;
}

char *decToBinNew(int size, int decNum) {

    int bit;
    int pos = 0;
    char *binNum = malloc(size * sizeof(char));

    for (int i = size-1; i >= 0; i--){
        bit = decNum >> i;

    if (bit & 1)
        binNum[pos] = 1;
    else
        binNum[pos] = 0;

    pos++;
    }

    return binNum;

}

void BDD_test(int variableCount, int BDDCount) {

    BDD *bdd;
    BF *bf;

    clock_t finalTime, time;
    double finalDuration, duration;
    int vectorSize, deletedElements;
    char *input, *resultsTable, result;
    float bddReduction, avgBddReduction = 0;


    /// TEST ///
    finalTime = clock();

    for (int i = 0; i < BDDCount; i++) {

        /// BDD_create ///
        time = clock();

        bf = createBooleanFunction(variableCount);
        bdd = BDD_create(bf);
        free(bf->vector);
        free(bf);

        time = clock() - time;
        duration = ((double)time) / CLOCKS_PER_SEC;
        //printf("\nBDD_create pre pocet premennych (%d) trval %f sekund\n", variableCount, duration);


        /// BDD_use (pred BDD_reduce) ///
        time = clock();

        vectorSize = bdd->firstElement->booleanFunction.vectorSize;
        resultsTable = calloc(vectorSize, sizeof(char));

        for (int j = 0; j < vectorSize; j++) {
            input = decToBin(bdd->variableCount, j);
            result = BDD_use(bdd, input);
            resultsTable[j] = result;
            free(input);
        }

        time = clock() - time;
        duration = ((double)time) / CLOCKS_PER_SEC;
        //printf("BDD_use (pred BDD_reduce) pre pocet premennych (%d) a pre vsetky kombinacie vstupu trval %f sekund\n", variableCount, duration);


        /// BDD_reduce ///
        time = clock();

        deletedElements = BDD_reduce(bdd);
        bddReduction = (((float) deletedElements) / (bdd->elementCount + deletedElements)) * 100;
        avgBddReduction += bddReduction;
        //printf("BDD diagram(%d) bol zredukovany o %.2f%%\n", i, bddReduction);

        time = clock() - time;
        duration = ((double)time) / CLOCKS_PER_SEC;
        //printf("BDD_reduce pre pocet premennych (%d) trval %f sekund\n", variableCount, duration);


        /// BDD_use (po BDD_reduce) ///
        time = clock();

        vectorSize = bdd->firstElement->booleanFunction.vectorSize;

        for (int j = 0; j < vectorSize; j++) {
            input = decToBin(bdd->variableCount, j);
            result = BDD_use(bdd, input);
            if (result != resultsTable[j])
                printf("Vysledky sa nezhoduju!\n");
            free(input);
        }
        free(resultsTable);

        time = clock() - time;
        duration = ((double)time) / CLOCKS_PER_SEC;
        //printf("BDD_use (po BDD_reduce) pre pocet premennych (%d) a pre vsetky kombinacie vstupu trval %f sekund\n\n", variableCount, duration);


        /// BDD_free ///
        BDD_free(bdd);
        //printf("IDEM\n");
    }

    finalTime = clock() - finalTime;
    finalDuration = ((double)finalTime) / CLOCKS_PER_SEC;
    printf("Cely test pre pocet premennych (%d) a %d BDD diagramov trval %f sekund\n", variableCount, BDDCount, finalDuration);
    printf("Priemerna miera redukovanosti BDD diagramov bola %.2f%%\n", avgBddReduction / BDDCount);
    /// KONIEC TESTU ///
}


int main() {
    srand(time(NULL));
    BDD_test(13, 2000);
    printf("\nSuccessful end\n");
    return 0;
}
