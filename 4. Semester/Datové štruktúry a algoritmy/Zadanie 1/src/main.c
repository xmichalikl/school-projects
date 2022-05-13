#include <stdio.h>
#include <stdbool.h>

char* memoryPtr = NULL;

void* memory_alloc(int size);
int   memory_free(void* valid_ptr);
int   memory_check(void* ptr);
void  memory_init(void* ptr, int size);
void  memory_print();

void memory_print() {

    for (int i = 0; i < (*(int*)(memoryPtr)); i++) {
        if (i % 4 == 0)
            printf("\n");
        if (i == 0 || i == 668)
            printf("mem[%d] = %d\n", i, *(int*)(memoryPtr + i));
        else
            printf("mem[%d] = %d\n", i, *(memoryPtr + i));
    }
}

void mergeBlocks(char* firstBlock, char* secondBlock) {

    int* write;
    int header = (3 * sizeof(int));
    int footer = (3 * sizeof(int));

    int nextH = sizeof(int);
    int prevH = sizeof(int);

    int firstBlockSize = *(int*)(firstBlock - header) * (-1);
    int secondBlockSize = *(int*)(secondBlock - header) * (-1);
    int newBlockSize = (firstBlockSize + header + footer + secondBlockSize);
    bool lastBlock = false;

    //kontrola posledneho bloku
    if (*(int*)(secondBlock - prevH - nextH) == 0) {
        printf("is last\n");
        lastBlock = true;
    }

    //zmena velkosti v hlavicke a pate
    write = (int*)(firstBlock - header);
    *(write) = -newBlockSize;

    write = (int*)(secondBlock + secondBlockSize);
    *(write) = -newBlockSize;


    //zmena offsetov
    if (lastBlock == true) {
        write = (int*)(firstBlock - prevH - nextH); //nextH
        *(write) = *(int*)(secondBlock + secondBlockSize + nextH);
    }

    else {

        int nextBlockSize = (*(int*)(secondBlock - header + *(int*)(secondBlock - prevH - nextH)));
        if (nextBlockSize < 0) {
            nextBlockSize = -nextBlockSize;
        }

        //nova hlavicka a pata
        write = (int*)(firstBlock - prevH - nextH); //nextH
        *(write) = *(int*)(secondBlock + secondBlockSize + nextH) + *(int*)(firstBlock - prevH - nextH);

        write = (int*)(secondBlock + secondBlockSize + nextH); //nextF
        *(write) = *(int*)(firstBlock - prevH - nextH);

        //nasledujuci blok
        write = (int*)(secondBlock + secondBlockSize + footer + nextH + prevH); //nextPrevH
        *(write) = *(int*)(secondBlock - prevH) + *(int*)(secondBlock + secondBlockSize + footer + nextH + prevH);

        write = (int*)(secondBlock + secondBlockSize + footer + header + nextBlockSize + nextH + prevH); //nextPrevF
        *(write) = *(int*)(secondBlock + secondBlockSize + footer + nextH + prevH);
    }

    //nova pata
    write = (int*)(secondBlock + secondBlockSize + nextH + prevH); //prevF
    *(write) = *(int*)(firstBlock - prevH);

    //mazanie strednej hlavicky a paty
    for (int i = 0; i < (header + footer); i++) {
        *(char*)(firstBlock + firstBlockSize + i) = 0;
    }
}

int checkNextBlock(char* checkP, int blockSize) { //volnost nasled. bloku

    int header = (3 * sizeof(int));
    int footer = (3 * sizeof(int));

    //kontorla konca pamate
    if (checkP + blockSize + footer == memoryPtr + *(memoryPtr)) {
        printf("Last block!\n");
        return 0;
    }
    else if (*(int*)(checkP + blockSize + footer) < 0) {
        printf("Next block is free!\n");
        return 1;
    }
    else {
        printf("Next block is not free!\n");
        return 0;
    }
}

int checkPrevBlock(char* checkP) {  //volnost predch. bloku

    int header = (3 * sizeof(int));
    int footer = (3 * sizeof(int));

    //kontorla zaciatku pamate
    if (checkP - header == memoryPtr + (2 * sizeof(int))) {
        printf("First block!\n");
        return 0;
    }
    else if (*(int*)(checkP - header - footer) < 0) {
        printf("Prev block is free!\n");
        return 1;
    }
    else {
        printf("Prev block is not free!\n");
        return 0;
    }
}

int memory_free(void* valid_ptr) {

    char* char_valid_ptr = (char*)valid_ptr;
    int* write;
    int header = (3 * sizeof(int));
    int footer = (3 * sizeof(int));

    int nextH = sizeof(int);
    int prevH = sizeof(int);

    int blockSize = *(int*)(char_valid_ptr - header);
    bool successfulFree = false;
    bool merge = false;

    //vynulovanie bloku 
    for (int i = 0; i < blockSize; i++) {
        *(char*)(char_valid_ptr + i) = 0;
    }

    //zmena oznacenia
    write = (int*)(char_valid_ptr - header);
    *(write) = *(write) * -1;

    write = (int*)(char_valid_ptr + blockSize);
    *(write) = *(write) * -1;

    //kontrola predchadzajuceho bloku a pripadne spojenie blokov
    if ( checkPrevBlock(char_valid_ptr) == 1 ) {

        int offset = *(int*)(char_valid_ptr - prevH);
        char* prevBlock = char_valid_ptr + offset;

        //spojenie blokov
        mergeBlocks(prevBlock, char_valid_ptr);
        valid_ptr = prevBlock;
        blockSize = *(int*)(char_valid_ptr - header);
        blockSize = -blockSize;

        //znizenie poctu blokov
        write = (int*)(memoryPtr + sizeof(int));
        *(write) -= 1;

        successfulFree = true;
        merge = true;
        printf("Prev merging was successful!\n");

    }

    //kontrola nasledujuceho bloku a pripadne spojenie blokov
    if ( checkNextBlock(char_valid_ptr, blockSize) == 1 ) {

        int offset = *(int*)(char_valid_ptr - prevH - nextH);
        char* nextBlock = char_valid_ptr + offset;

        //spojenie blokov
        mergeBlocks(char_valid_ptr, nextBlock);
        blockSize = *(int*)(char_valid_ptr - header);
        blockSize = -blockSize;

        //znizenie poctu blokov
        write = (int*)(memoryPtr + sizeof(int));
        *(write) -= 1;

        successfulFree = true;
        merge = true;
        printf("Next merging was successful!\n");
    }

    if (merge == false) {
        successfulFree = true;
        printf("No merging!\n");
    }

    //uspesne uvolnenie
    if (successfulFree == true) {
        printf("Memory free successfull!\n\n");
        return 0;
    }

    //neuspesne uvolnenie
    if (successfulFree == false) {
        printf("Memory free ERROR!\n\n");
        return 1;
    }

}

int memory_check(void* ptr) {

    //posun na zaciatok pamate
    char* checkP = (char*)(memoryPtr + (2 * sizeof(int)));

    //posun na zaciatok hlavicky
    ptr = (void*)((char*)ptr - (3 * sizeof(int)));

    int* memStart = (int*)(memoryPtr + (2 * sizeof(int)));
    int* memEnd = (int*)(memoryPtr + *(int*)(memoryPtr));

    //kontrola rozsahu pamate
    if ((int*)(ptr) < (int*)(memStart) || (int*)(memEnd) < (int*)(ptr)) {
        printf("Pointer is invalid!\n");
        return 0;
    }

    //prehladavanie blokov pamate
    for (int i = 0; i < *(memoryPtr + sizeof(int)); i++) {

        //pointer je platny
        if (ptr == checkP && *(checkP) > 0) {
            printf("Pointer is valid\n");
            return 1;
        }

        //pointer nie je platny, posun na dalsi blok
        else {
            checkP += *(checkP + sizeof(int));
        }
    }

    //pointer nie je platny
    printf("Pointer is invalid\n");
    return 0;
}

void* memory_alloc(int size) {

    char* checkP = (char*)(memoryPtr + (2 * sizeof(int)));
    int* write = (int*)(checkP);
    void* result = NULL;

    bool successfulAllocation = false;
    bool lastBlock = false;
    int reqSize = size + 4 + (6 * sizeof(int));

    //prechadzanie jednotlivych blokov pamate
    for (int i = 0; i < *(int*)(memoryPtr + sizeof(int)); i++) {

        int blockSize = *(int*)(checkP + 0);
        int header = (3 * sizeof(int));
        int footer = (3 * sizeof(int));

        //pozadovana velkost == velkost bloku 
        if (blockSize < 0 && -(blockSize) == size) {

            //zmena oznacenia 
            write = (int*)(checkP + 0);
            *(write) = size;

            write = (int*)(checkP + header + size);
            *(write) = size;

            //oznacenie alokovanej casti pamate
            for (int j = 0; j < size; j++)
                *(checkP + header + j) = -2;

            //potvrdenie uspesnej alokacie
            printf("Perfect fit!\n");
            successfulAllocation = true;
            result = checkP + header;
            break;
        }


        //kontrola schopnosti alokacie bloku
        else if (blockSize < 0 && (-blockSize) > reqSize) {


            int newFreeSize = (-blockSize) - (header + size + footer);
            int sizeH = sizeof(int);
            int nextH = sizeof(int);
            int prevH = sizeof(int);


            /*if ( *(int*)(memoryPtr+sizeof(int)) > 1) {
                //printf("lastBlock = %d\n", *(int*)(memoryPtr+2*sizeof(int) + 324));
                printf("lastBlock = %d\n", *(int*)(checkP));

            }*/

            //kontrola posledneho bloku
            if (*(int*)(checkP + nextH) == 0) {
                lastBlock = true;
            }

            int nextBlockSize = *(int*)(checkP + *(int*)(checkP + nextH));
            if (nextBlockSize < 0) {
                nextBlockSize = -nextBlockSize;
            }

            //upravena hlavicka pre novy obsadeny blok
            write = (int*)(checkP + 0);
            *(write) = size;

            write = (int*)(checkP + nextH); //nextH
            *(write) = header + size + footer;


            if (lastBlock == false) {
                //nasledujuci blok
                //write = (int*)(checkP+header+blockSize+footer+nextH+prevH); //nextPrevH
                write = (int*)(checkP + header + size + header + footer + newFreeSize + footer + nextH + prevH); //nextPrevH
                *(write) = -(header + newFreeSize + footer);

                write = (int*)(checkP + header + size + header + footer + newFreeSize + footer + header + nextBlockSize + nextH + prevH); //nextPrevF
                *(write) = -(header + newFreeSize + footer);


                //nova hlavicka pre novy volny blok
                write = (int*)(checkP + header + size + footer + 0);
                *(write) = -newFreeSize;

                write = (int*)(checkP + header + size + footer + nextH);    //nextH
                *(write) = header + newFreeSize + footer;

                write = (int*)(checkP + header + size + footer + nextH + prevH);  //prevH
                *(write) = -(header + size + footer);


                //upravena pata pre novy volny blok
                write = (int*)(checkP + header + size + footer + header + newFreeSize + 0);
                *(write) = -newFreeSize;

                write = (int*)(checkP + header + size + footer + header + newFreeSize + nextH);    //nextH
                *(write) = header + newFreeSize + footer;

                write = (int*)(checkP + header + size + footer + header + newFreeSize + nextH + prevH);   //prevH
                *(write) = -(header + size + footer);
            }

            else {
                //nova hlavicka pre novy volny blok
                write = (int*)(checkP + header + size + footer + 0);
                *(write) = -newFreeSize;

                write = (int*)(checkP + header + size + footer + nextH);    //nextH
                *(write) = *(int*)(checkP + header + size + footer + header + newFreeSize + nextH);

                write = (int*)(checkP + header + size + footer + nextH + prevH);  //prevH
                *(write) = -(header + size + footer);


                //upravena pata pre novy volny blok
                write = (int*)(checkP + header + size + footer + header + newFreeSize + 0);
                *(write) = -newFreeSize;

                write = (int*)(checkP + header + size + footer + header + newFreeSize + nextH + prevH);   //prevH
                *(write) = -(header + size + footer);
            }


            //nova pata pre novy obsadeny blok
            write = (int*)(checkP + header + size + 0);
            *(write) = size;

            write = (int*)(checkP + header + size + nextH);   //nextF
            *(write) = header + size + footer;

            write = (int*)(checkP + header + size + nextH + prevH); //prevF
            *(write) = *(checkP + nextH + prevH);


            //oznacenie alokovanej casti pamate
            for (int j = 0; j < size; j++)
                *(checkP + header + j) = -2;

            //zvysenie poctu blokov
            write = (int*)(memoryPtr + sizeof(int));
            *(write) += 1;

            //potvrdenie uspesnej alokacie
            successfulAllocation = true;
            result = checkP + header;
            break;
        }

        //posunutie na dasli blok pamate
        else {
            //checkP += *(checkP + sizeof(int));
            checkP += *(int*)(checkP + sizeof(int));
        }
    }

    //vratenie platneho ukazovatela na alokovany blok pamate
    if (successfulAllocation == true) {
        printf("Allocation successful!\n\n");
        return result;
    }

    //vratenie NULL, neuspesna alokacia     
    else {
        printf("Allocation ERROR!\n\n");
        result = NULL;
        return result;
    }

}

void  memory_init(void* ptr, int size) {

    int globalH = 2 * sizeof(int);
    int header = 3 * sizeof(int);
    int footer = 3 * sizeof(int);

    //kontrola schopnosti inicializacie
    if (size < globalH + header + footer + 8) {
        printf("Initialization ERROR!\n\n");
    }

    else {
        //vynulovanie pamate
        memoryPtr = (char*)(ptr);
        for (int i = 0; i < size; i++) {
            *(memoryPtr + i) = 0;
        }

        //globalna hlavicka pamate
        int* sizeMem = (int*)(memoryPtr);
        *(sizeMem) = size;

        int* freeBlock = (int*)(memoryPtr + sizeof(int));
        *(freeBlock) = 1;


        //hlavicka prvotneho volneho bloku
        int* sizePtrH = (int*)(memoryPtr + globalH);
        *(sizePtrH) = -1 * (size - 8 * sizeof(int));

        int* nextFreeH = (int*)(memoryPtr + sizeof(int) + globalH);
        *(nextFreeH) = 0;

        int* prevFreeH = (int*)(memoryPtr + 2 * sizeof(int) + globalH);
        *(prevFreeH) = 0;


        //pata prvotneho volneho bloku
        int* sizePtrF = (int*)(memoryPtr + (size)-(3 * sizeof(int)));
        *(sizePtrF) = *(sizePtrH);

        int* nextFreeF = (int*)(memoryPtr + (size)-(2 * sizeof(int)));
        *(nextFreeF) = *(nextFreeH);

        int* prevFreeF = (int*)(memoryPtr + (size)-(sizeof(int)));
        *(prevFreeF) = *(prevFreeH);

        printf("Initialization successful!\n\n");
    }
}


int main() {
    printf("\n\n\n");


    //TEST 1
    /*char memory[50];
    memory_init(memory, 50);
    char* pointer1 = (char*) memory_alloc(8);
    char* pointer2 = (char*) memory_alloc(8);
    char* pointer3 = (char*) memory_alloc(8);
    char* pointer4 = (char*) memory_alloc(8);
    char* pointer5 = (char*) memory_alloc(8);
    char* pointer6 = (char*) memory_alloc(8);*/

    //TEST 2 
    /*char memory[100];
    memory_init(memory, 100);
    char* pointer1 = (char*) memory_alloc(8);
    char* pointer2 = (char*) memory_alloc(8);
    memory_free(pointer1);
    char* pointer4 = (char*) memory_alloc(8);
    char* pointer5 = (char*) memory_alloc(8);
    memory_free(pointer4);
    char* pointer6 = (char*) memory_alloc(8);*/

    //TEST 3 
    /*char memory[200];
    memory_init(memory, 200);
    char* pointer1 = (char*) memory_alloc(8);
    char* pointer2 = (char*) memory_alloc(8);
    char* pointer3 = (char*) memory_alloc(8);
    char* pointer4 = (char*) memory_alloc(8);
    char* pointer5 = (char*) memory_alloc(8);
    char* pointer6 = (char*) memory_alloc(8);
    memory_free(pointer1);
    memory_free(pointer2);
    memory_free(pointer3);
    memory_free(pointer5);
    memory_free(pointer6);
    memory_free(pointer4);*/
    

    //TEST 4 
    /*char memory[200];
    memory_init(memory, 200);
    char* pointer1 = (char*) memory_alloc(24);
    char* pointer2 = (char*) memory_alloc(24);
    char* pointer3 = (char*) memory_alloc(24);
    memory_free(pointer1);
    memory_free(pointer2);
    memory_free(pointer3);
    char* pointer4 = (char*) memory_alloc(24);
    char* pointer5 = (char*) memory_alloc(24);
    char* pointer6 = (char*) memory_alloc(24);*/


    //TEST 5
    /*char memory[200];
    memory_init(memory, 200);
    char* pointer1 = (char*) memory_alloc(8);
    char* pointer2 = (char*) memory_alloc(16);
    char* pointer3 = (char*) memory_alloc(20);
    char* pointer4 = (char*) memory_alloc(24);
    char* pointer5 = (char*) memory_alloc(10);
    memory_free(pointer1);
    memory_free(pointer2);
    memory_free(pointer3);
    char* pointer6 = (char*) memory_alloc(24);*/


    //TEST 6 
    /*char memory[10000];
    memory_init(memory, 10000);
    char* pointer1 = (char*) memory_alloc(500);
    char* pointer2 = (char*) memory_alloc(1000);
    char* pointer3 = (char*) memory_alloc(2000);
    char* pointer4 = (char*) memory_alloc(3000);
    memory_free(pointer1);
    char* pointer5 = (char*) memory_alloc(1000);
    memory_free(pointer2);
    char* pointer6 = (char*) memory_alloc(4000);
    memory_free(pointer3);
    char* pointer7 = (char*) memory_alloc(4000);
    memory_free(pointer4);
    char* pointer8 = (char*) memory_alloc(4000);
   
    memory_free(pointer8);
    */


    //TEST 7 
    /*char memory[10000];
    memory_init(memory, 10000);
    char* pointer1 = (char*) memory_alloc(8);
    char* pointer2 = (char*) memory_alloc(16);
    char* pointer3 = (char*) memory_alloc(32);
    char* pointer4 = (char*) memory_alloc(64);
    char* pointer5 = (char*) memory_alloc(128);
    char* pointer6 = (char*) memory_alloc(256);
    char* pointer7 = (char*) memory_alloc(512);
    char* pointer8 = (char*) memory_alloc(1024);
    char* pointer9 = (char*) memory_alloc(2048);
    char* pointer10 = (char*) memory_alloc(4096);

    char* pointer11 = (char*) memory_alloc(2048);
    memory_free(pointer8);
    char* pointer12 = (char*) memory_alloc(2048);
    memory_free(pointer9);
    char* pointer13 = (char*) memory_alloc(2048);

    memory_free(pointer10);
    memory_free(pointer13);

    memory_free(pointer1);
    memory_free(pointer2);
    memory_free(pointer3);
    memory_free(pointer4);

    memory_free(pointer5);
    memory_free(pointer6);
    memory_free(pointer7);*/




    //TEST 8 
    /*char memory[100000];
    memory_init(memory, 100000);
    char* pointer1 = (char*) memory_alloc(10000);
    char* pointer2 = (char*) memory_alloc(20000);
    char* pointer3 = (char*) memory_alloc(30000);

    char* pointer4 = (char*) memory_alloc(40000);
   
    char* pointer5 = (char*) memory_alloc(50);
    char* pointer6 = (char*) memory_alloc(100);
    char* pointer7 = (char*) memory_alloc(500);
    char* pointer8 = (char*) memory_alloc(1000);
    
    memory_free(pointer1);
    memory_free(pointer2);
    memory_free(pointer3);

    memory_free(pointer5);
    memory_free(pointer6);
    memory_free(pointer7);
    memory_free(pointer8);*/



    //memory_print();
    printf("\n\n\n");
    return 0;
}