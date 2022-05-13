#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#define MAX 100

typedef struct kamarati {
    char meno[MAX];
    char priezvisko[MAX];
    struct kamarati *dalsi_kamarat;
} KAMARATI;

void vypisZoznam(KAMARATI *k){

    printf("*************\n");

    while (k != NULL){
        printf("%s %s\n", k->meno, k->priezvisko);
        k = k->dalsi_kamarat;
    }

    printf("*************\n");
}

KAMARATI *predosly(KAMARATI *zaciatokZoznamu, KAMARATI *hladaj){

    KAMARATI *zoznam = zaciatokZoznamu;

    if (zaciatokZoznamu == hladaj)
        return NULL;

    while (zoznam->dalsi_kamarat != hladaj){
        zoznam = zoznam->dalsi_kamarat;
    }
    return zoznam;
}

KAMARATI *vloz(KAMARATI *zaciatokZoznamu, char meno[], char priezvisko[]){

    KAMARATI *zoznam = zaciatokZoznamu;
    KAMARATI *novyKamarat, *predoslyKamarat;;

    //printf("Vkladam %s %s\n", meno, priezvisko);

    if (zaciatokZoznamu == NULL){
        zaciatokZoznamu = malloc(sizeof(KAMARATI));
        strcpy(zaciatokZoznamu->meno, meno);
        strcpy(zaciatokZoznamu->priezvisko, priezvisko);
        zaciatokZoznamu->dalsi_kamarat = NULL;
        return zaciatokZoznamu;
    }

    else {
        while (zoznam != NULL){
                if (strcmp(meno, zoznam->meno) < 1 ){
                    predoslyKamarat = predosly(zaciatokZoznamu, zoznam);
                    novyKamarat = malloc(sizeof(KAMARATI));
                    strcpy(novyKamarat->meno, meno);
                    strcpy(novyKamarat->priezvisko, priezvisko);

                    if (predoslyKamarat == NULL){
                        novyKamarat->dalsi_kamarat = zoznam;
                        zaciatokZoznamu = novyKamarat;
                        return zaciatokZoznamu;
                    }

                    else {
                        novyKamarat->dalsi_kamarat = zoznam;
                        predoslyKamarat->dalsi_kamarat = novyKamarat;
                        return zaciatokZoznamu;
                    }
                }
            zoznam = zoznam->dalsi_kamarat;
        }
        predoslyKamarat = predosly(zaciatokZoznamu, zoznam);
        novyKamarat = malloc(sizeof(KAMARATI));
        strcpy(novyKamarat->meno, meno);
        strcpy(novyKamarat->priezvisko, priezvisko);
        predoslyKamarat->dalsi_kamarat = novyKamarat;
        novyKamarat->dalsi_kamarat = NULL;
        return zaciatokZoznamu;
    }
    return zaciatokZoznamu;
}

void vypis (KAMARATI *zaciatokZoznamu, int pozicia){

    KAMARATI *zoznam = zaciatokZoznamu;
    int prvok = 1;

    while (zoznam != NULL){
        if (prvok == pozicia){
            printf("%s %s\n", zoznam->meno, zoznam->priezvisko);
            break;
        }
        zoznam = zoznam->dalsi_kamarat;
        prvok++;
    }
    if (zoznam == NULL)
        printf("Vstup neobsahuje prvok %d\n", pozicia);
}

int main()
{
    KAMARATI *zaciatokZoznamu = NULL;
    KAMARATI *zoznam;

    char meno[MAX], priezvisko[MAX], c;
    int pocetCisel = 0, cislo;//, cislaVelkost = MAX, i;
	//int *poleCisel = (int*)malloc(cislaVelkost * sizeof(int));

	//printf("%s\n", zaciatokZoznamu);

    while(1){
        scanf("%s", meno);
        if (meno[0] >= '0' && meno[0] <= '9' ){
            cislo = atoi(meno);
            //poleCisel[0] = cislo;
            pocetCisel++;
            //printf("ajajja\n");
            break;
        }
        c=getchar();
        if (c==' '){
            scanf("%s", priezvisko);
            zaciatokZoznamu = vloz(zaciatokZoznamu, meno, priezvisko);
            continue;
        }
        else {
            //printf("Vkladam %s %s\n", meno, priezvisko);
            zaciatokZoznamu = vloz(zaciatokZoznamu, meno, "");
            continue;
        }
    }
    //vypisZoznam(zaciatokZoznamu);
    //poleCisel[0] = cislo;

    vypis(zaciatokZoznamu,cislo);
    while (scanf("%d", &cislo) == 1){
        vypis(zaciatokZoznamu, cislo);
        //poleCisel[pocetCisel] = cislo;
        //pocetCisel++;
    }

    while(zaciatokZoznamu != NULL){
        //printf("%s %s\n", zaciatokZoznamu->meno, zaciatokZoznamu->priezvisko);
        zoznam = zaciatokZoznamu->dalsi_kamarat;
        free(zaciatokZoznamu);
        zaciatokZoznamu = zoznam;
    }

    return 0;
}
