#include <stdio.h>
#include <stdlib.h>
#define MAX 500

int jePalindrom(char slovo[], int dlzkaSlova){ // funkcia na zistenie palindromu

    char slovoOpacne[MAX];

    char slovoP[MAX];
    char slovoOpacneP[MAX];

    strcpy(slovoOpacne, slovo);
    strrev(slovoOpacne);

    if (strcmp(slovo, slovoOpacne) == 0)    // porovnanie 2 retazcov
        return 1;   // ak sa rovnaju, funkcia vrati 1
    else
        return 0;   // inak vrati 0

}


void najdiPalindrom(char slovo[], int dlzkaSlova){ // funkcia, ktora hlada palindrom

    int y, x, i, j, k, p = 0;
    int velkost = 0;
    int pocet = 0;
    char porovnaj[MAX];
    char slovoKopia[MAX];

    for(k=0; k<MAX; k++){
        porovnaj[k] == '\0';
        slovoKopia[k] == '\0';
    }

    strcpy(slovoKopia, slovo);

    for(y=dlzkaSlova; y>=0; y--){   // prechadzanie slova od predu aj od zadu a vytvaranie vsetkych moznosti vyskytu palindromu
        slovoKopia[y] = '\0';
        for(x=0; x<2; x++){
            for(j=0; j<y; j++){
                for(i=j; i<y; i++){
                    porovnaj[p] = slovoKopia[i];
                    p++;
                }
                porovnaj[p] = '\0';

                //printf("%s\n", porovnaj);

                if ( jePalindrom(porovnaj, strlen(porovnaj)) == 1 && strlen(porovnaj) > 1){ // overenie, ci je dany retazec palindrom, ak ano porovna sa jeho
                    if (strlen(porovnaj) > velkost){                                        // velkost s doterajsou najvacsou velkostou
                        velkost = strlen(porovnaj);
                    }
                    //printf("%s je palindrom\n", porovnaj);
                }
                p = 0;

                for(k=0; k<MAX; k++)
                    porovnaj[k] == '\0';
            }
            strrev(slovoKopia); // pretocenie retazca
        }
    }

   strcpy(slovoKopia, slovo);

    for(k=0; k<MAX; k++){
        porovnaj[k] == '\0';
    }

    for(y=dlzkaSlova; y>=0; y--){   // to iste ako hore, prechadzanie slova od predu aj od zadu a vytvaranie vsetkych moznosti vyskytu palindromu
        slovoKopia[y] = '\0';
        for(x=0; x<2; x++){
            for(j=0; j<y; j++){
                for(i=j; i<y; i++){
                    porovnaj[p] = slovoKopia[i];
                    p++;
                }
                porovnaj[p] = '\0';

                //printf("%s\n", porovnaj);

                if ( jePalindrom(porovnaj, strlen(porovnaj)) == 1 && strlen(porovnaj) == velkost){ // vypis palindromov s najvacsou velkostou
                        if (pocet == 0)
                            printf("%d %s ", velkost, porovnaj);
                        else
                            printf("%s ", porovnaj);
                        pocet++;
                }

                p = 0;

                for(k=0; k<MAX; k++)
                    porovnaj[k] == '\0';
            }
            strrev(slovoKopia);
        }
    }
}


int main()
{
    int i;
    char slovo[MAX];

    for(i=0; i<MAX; i++)
        slovo[i] == '\0';

    while( (scanf("%s", slovo)) == 1 ){ // nekonecny cyklus na nacitanie retazcov

        najdiPalindrom(slovo, strlen(slovo));
        printf("\n");

        for(i=0; i<strlen(slovo); i++)
            slovo[i] == '\0';
    }

    return 0;
}
