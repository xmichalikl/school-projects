#include <stdio.h>
#include <stdlib.h>
// TURING INAK

int vypocitajVelkost(int pole[], int cislo){

    int vysledok = 0;
    int max, i, j;

    for (j=2; j<=cislo; j++){
        max = j-1;

        for (i=max; i>=0; i--){
            vysledok += pole[i] * pole[max-i];
        }
        pole[j] = vysledok;
        vysledok = 0;
    }

    printf("%d\n", pole[cislo]);

    return *pole;
}

int main()
{
    int pole[33];
    int cislo, i;
    int zname = 1;

    for (i=0; i<33; i++)
        pole[i] = 0;

    pole[0] = 1;
    pole[1] = 1;

    while( scanf("%d", &cislo) == 1 ){
        if (cislo > 32 || cislo < 0)
            continue;
        if (cislo > zname){
            *pole = vypocitajVelkost(pole, cislo);
            zname = cislo;
            }
        else
            printf("%d\n", pole[cislo]);
    }

    return 0;
}
