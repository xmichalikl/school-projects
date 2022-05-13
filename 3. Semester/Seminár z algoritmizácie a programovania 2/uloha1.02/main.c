#include <stdio.h>
#include <stdlib.h>
#define LAST 1299709
#define MAX 100000

int main()
{
    int *cisla;
    int prvocisla[MAX];
    int K, i, pocet = 0, start = 2, nasobok = 0;

	cisla = (int*)malloc(LAST * sizeof(int));

    for (i=0; i<=LAST; i++)
        cisla[i] = i;

    for (i=start; i<=LAST; i++){
        if (cisla[i] == 0)
            continue;

        while (nasobok <= LAST){
            nasobok += i;
            if (nasobok == i || nasobok > LAST)
                continue;
            cisla[nasobok] = 0;
        }
        prvocisla[pocet] = i;
        pocet++;
        nasobok = 0;
    }

    while (scanf("%d", &K) == 1){
        if (1 > K || K > 100000)
            continue;
        printf("%d\n", prvocisla[K-1]);
    }

    free(cisla);

    return 0;
}


