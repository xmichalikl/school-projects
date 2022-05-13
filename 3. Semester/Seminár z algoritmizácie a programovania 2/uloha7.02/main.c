#include <stdio.h>
#include <stdlib.h>

typedef struct vrchol {
    int nazov;
    int vzdialenost;
    struct vrchol *dalsiVrchol;
} VRCHOL;

int zistiVzdialenost(VRCHOL **pole, int akt, int pred, int ciel, int pocitadlo){

    VRCHOL *v = pole[akt-1];
    int hodnota = 0;

    if (akt == ciel)
        return pocitadlo;

    while ( v != NULL ){

        if (v->nazov == pred){
            v = v->dalsiVrchol;
            continue;
        }

        hodnota = zistiVzdialenost(pole, v->nazov, akt, ciel, pocitadlo+v->vzdialenost);

        if (hodnota != -1)
            return hodnota;

        v = v->dalsiVrchol;
    }

    return -1;
}

void pridajVrchol(VRCHOL **pole, int prvyVrchol, int druhyVrchol, int vzdialenost){

    VRCHOL *pridajPrvy = malloc(1 * sizeof(VRCHOL));
    VRCHOL *pridajDruhy = malloc(1 * sizeof(VRCHOL));

    pridajPrvy->nazov = prvyVrchol;
    pridajPrvy->vzdialenost = vzdialenost;
    pridajPrvy->dalsiVrchol = pole[druhyVrchol-1];
    pole[druhyVrchol-1] = pridajPrvy;


    pridajDruhy->nazov = druhyVrchol;
    pridajDruhy->vzdialenost = vzdialenost;
    pridajDruhy->dalsiVrchol = pole[prvyVrchol-1];
    pole[prvyVrchol-1] = pridajDruhy;
}


int main()
{
    int i, N, M, A, B;
    int prvyVrchol, druhyVrchol, vzdialenost;

    VRCHOL **pole = NULL;

    scanf("%d %d %d %d", &N, &M, &A, &B);
    pole = malloc(N * sizeof(VRCHOL*));

    for (i=0; i<N; i++)
        *(pole+i) = NULL;

    for (i=0; i<M; i++){
        scanf("%d", &prvyVrchol);
        scanf("%d", &druhyVrchol);
        scanf("%d", &vzdialenost);
        pridajVrchol(pole, prvyVrchol, druhyVrchol, vzdialenost);
    }

    /*for (i=0; i<N; i++){
        VRCHOL* pom = pole[i];
        printf("\n");
        while (pom != NULL){
            printf("%d %d %d\n", i+1, pom->nazov, pom->vzdialenost);
            pom = pom->dalsiVrchol;
        }
    }*/

    printf("%d\n", zistiVzdialenost(pole, A, 0, B, 0));

    for (i=0; i<N; i++)
        free(pole[i]);
    free(pole);

    return 0;
}
