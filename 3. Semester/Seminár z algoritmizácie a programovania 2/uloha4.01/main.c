#include <stdio.h>
#include <stdlib.h>

typedef struct strom {
    int cislo;
    struct strom *lavyList;
    struct strom *pravyList;
} STROM;

STROM *pridajCislo(STROM *koren, int cislo){

    STROM *konar = koren;
    STROM *novyList, *predoslyList;

    int hlbka = 0;

    if (koren == NULL){
        koren = malloc(sizeof(STROM));
        koren->cislo = cislo;
        koren->lavyList = NULL;
        koren->pravyList = NULL;
        printf("%d\n", hlbka);
        return koren;
    }

    else {
        while (konar != NULL){
            if (konar->cislo == cislo){
                printf("%d\n", hlbka);
                return koren;
            }

            if (konar->cislo > cislo){
                predoslyList = konar;
                konar = konar->lavyList;
                hlbka++;
            }
            else {
                predoslyList = konar;
                konar = konar->pravyList;
                hlbka++;
            }
        }
        novyList = malloc(sizeof(STROM));
        novyList->cislo = cislo;
        novyList->lavyList = NULL;
        novyList->pravyList = NULL;

        if (predoslyList->cislo > cislo)
            predoslyList->lavyList = novyList;
        else
            predoslyList->pravyList = novyList;

        printf("%d\n", hlbka);
        return koren;
    }
    return koren;
}

int main()
{
    STROM *koren = NULL;
    int cislo;

    while (scanf("%d", &cislo) == 1){
        koren = pridajCislo(koren, cislo);
    }
    return 0;
}
