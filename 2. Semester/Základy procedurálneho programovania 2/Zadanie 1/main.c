// Projekt c.1 -- Lukáš Michalík
#include <stdio.h>
#include <stdlib.h>
#define MAX 100
#define N 3

char **nacitajOsemsmerovku(char **pole, FILE *fr, int m, int n){

    int x = -1;
    int y = -1;
    int p = 0;

    char c;

    while ((c=fgetc(fr)) != EOF && p <= m){
        if (c >= 'A' && c <= 'Z'){
            pole[y][x] = c;
            x++;
        }
        if (c == '\n' && p == m)
            break;
        if (c == '\n'){
            ++p;
            x=0;
            y++;
        }
        else
            continue;
    }

    return pole;
}

int najdiSlovo(int *index[], int indexP[][2], char slovo[], int velkostSlova, int posY, int posX, int y, int x){

    int poziciaZ[2];
    int poziciaP[2];
    int slovoP = 1;
    int najdeneSlovo = 0;
    int i = 0;

    int prveP = slovo[slovoP];
    int druheP = slovo[slovoP+1];

    poziciaZ[0] = y;
    poziciaZ[1] = x;

    poziciaP[0] = y + posY;
    poziciaP[1] = x + posX;

    while (i<((indexP[druheP-'A'][0])/2)){

        if ( (index [druheP-'A'][i+i] == poziciaP[0]) && (index [druheP-'A'][i+i+1] == poziciaP[1]) ){
            poziciaP[0] += posY;
            poziciaP[1] += posX;
            slovoP++;
            druheP = slovo[slovoP+1];
            i = 0;
        }
        else
            i++;

        if ((velkostSlova-1) == slovoP){
            najdeneSlovo = 1;
            break;
        }
    }

    if (najdeneSlovo == 1)
        return 1;
    else
        return 0;

}

void urciSmer(FILE *fr, char **pole, int *index[], int indexP[][2], int m, int n){

    int i, y, x, posY, posX, vymazY, vymazX, dlzkaSlova;
    int pocetSlov = 0;

    char slovo[MAX];


    while (fscanf(fr, "%s", slovo) == 1){

        dlzkaSlova = strlen(slovo);

        for (y=0; y<m; y++){

            for (x=0; x<n; x++){

                if (slovo[1] == toupper(pole[y][x])){

                    // HORE
                    if (y+1<m && slovo[0] == toupper(pole[y+1][x])){
                        posY = -1; posX = 0;
                        if (y+posY>=0 && najdiSlovo(index, indexP, slovo, dlzkaSlova, posY, posX, y, x) == 1){
                            //printf("Nasiel som slovo %s\n", slovo);
                            vymazY = y+1; vymazX = x;
                            vyskrkniSlovo(pole, slovo, dlzkaSlova, posY, posX, vymazY, vymazX, m, n);
                            pocetSlov++;
                        }
                    }

                    // VPRAVO HORE
                    if (y+1<m && x-1>=0 && slovo[0] == toupper(pole[y+1][x-1])){
                        posY = -1; posX = 1;
                        if (y+posY>=0 && x+posX<n && najdiSlovo(index, indexP, slovo, dlzkaSlova, posY, posX, y, x) == 1){
                            //printf("Nasiel som slovo %s\n", slovo);
                            vymazY = y+1; vymazX = x-1;
                            vyskrkniSlovo(pole, slovo, dlzkaSlova, posY, posX, vymazY, vymazX, m, n);
                            pocetSlov++;
                        }
                    }

                    // DOPRAVA
                    if (x-1>=0 && slovo[0] == toupper(pole[y][x-1])){
                        posY = 0; posX = 1;
                        if (x+posX<n && najdiSlovo(index, indexP, slovo, dlzkaSlova, posY, posX, y, x) == 1){
                            //printf("Nasiel som slovo %s\n", slovo);
                            vymazY = y; vymazX = x-1;
                            vyskrkniSlovo(pole, slovo, dlzkaSlova, posY, posX, vymazY, vymazX, m, n);
                            pocetSlov++;
                        }
                    }

                    // VPRAVO DOLE
                    if (y-1>=0 && x-1>=0 && slovo[0] == toupper(pole[y-1][x-1])){
                        posY = 1; posX = 1;
                        if (y+posY<m && x+posX<n && najdiSlovo(index, indexP, slovo, dlzkaSlova, posY, posX, y, x) == 1){
                            //printf("Nasiel som slovo %s\n", slovo);
                            vymazY = y-1; vymazX = x-1;
                            vyskrkniSlovo(pole, slovo, dlzkaSlova, posY, posX, vymazY, vymazX, m, n);
                            pocetSlov++;
                        }
                    }

                    // DOLE
                    if (y-1>=0 && slovo[0] == toupper(pole[y-1][x])){
                        posY = 1; posX = 0;
                        if (y+posY<m && najdiSlovo(index, indexP, slovo, dlzkaSlova, posY, posX, y, x) == 1){
                            //printf("Nasiel som slovo %s\n", slovo);
                            vymazY = y-1; vymazX = x;
                            vyskrkniSlovo(pole, slovo, dlzkaSlova, posY, posX, vymazY, vymazX, m, n);
                            pocetSlov++;
                        }
                    }

                    // VLAVO DOLE
                    if (y-1>=0 && x+1<n && slovo[0] == toupper(pole[y-1][x+1])){
                        posY = 1; posX = -1;
                        if (y+posY<m && x+posX>=0 && najdiSlovo(index, indexP, slovo, dlzkaSlova, posY, posX, y, x) == 1){
                            //printf("Nasiel som slovo %s\n", slovo);
                            vymazY = y-1; vymazX = x+1;
                            vyskrkniSlovo(pole, slovo, dlzkaSlova, posY, posX, vymazY, vymazX, m, n);
                            pocetSlov++;
                        }
                    }

                    // DOLAVA
                    if (x+1<n && slovo[0] == toupper(pole[y][x+1])){
                        posY = 0; posX = -1;
                        if (x+posX>=0 && najdiSlovo(index, indexP, slovo, dlzkaSlova, posY, posX, y, x) == 1){
                            //printf("Nasiel som slovo %s\n", slovo);
                            vymazY = y; vymazX = x+1;
                            vyskrkniSlovo(pole, slovo, dlzkaSlova, posY, posX, vymazY, vymazX, m, n);
                            pocetSlov++;
                        }
                    }

                    // VLAVO HORE
                    if (y+1<m && x+1<n && slovo[0] == toupper(pole[y+1][x+1])){
                        posY = -1; posX = -1;
                        if (y+posY>=0 && x+posX<n && najdiSlovo(index, indexP, slovo, dlzkaSlova, posY, posX, y, x) == 1){
                            //printf("Nasiel som slovo %s\n", slovo);
                            vymazY = y+1; vymazX = x+1;
                            vyskrkniSlovo(pole, slovo, dlzkaSlova, posY, posX, vymazY, vymazX, m, n);
                            pocetSlov++;
                        }
                    }
                }
            }
        }
        if (pocetSlov == 0)
            printf("Slovo %s sa nepodarilo naist\n\n", slovo);

        for (i=0; i<MAX; i++)
            slovo[i] = '\0';

        pocetSlov = 0;
    }
}

void vyskrkniSlovo(char **pole, char slovo[], int dlzkaSlova, int posY, int posX, int vymazY, int vymazX, int m, int n){

    int i, y, x;

    for (i=0; i<dlzkaSlova; i++){
        pole[vymazY][vymazX] = tolower(slovo[i]);
        vymazY += posY;
        vymazX += posX;
    }

    for (y=0; y<m; y++){
        for (x=0; x<n; x++)
            printf("%c", pole[y][x]);
        printf("\n");
    }
    printf("\n");
}

int main()
{
    int typ, m, n, i, x, y, abc = 0, abcP = 0, abcV = 0, abcZ = 0;
    int *index[26], indexP[26][2];
    int tajnicka = 0;

    char **pole, c;

    FILE *fr;

    if ((fr = fopen("osemsmerovka.txt", "r")) == NULL){
        printf("Subor sa nepodarilo otvorit\n");
        return 0;
    }

    for (i=0; i<26; i++)
        index[i] = NULL;

    fscanf(fr, "%d", &typ);
    fscanf(fr, "%d", &m);
    fscanf(fr, "%d", &n);


    if (m <= 0 || n <= 0 || m > 100 || n > 100){
        printf("Zle rozmery\n");
        return 0;
    }


    /*
    if (typ == 8){
        printf("NEVIEM VYRIESIT\n");
        return 0;
    }*/


//  Alokovanie pamate pre dynamicke pole index
    pole = (char **) malloc(m * sizeof(char *));
    for (i=0; i<m; i++){
        pole[i] = (char *) malloc(n * sizeof(char));
    }

//  Nacitavanie do 2-rozmerneho pola
    pole = nacitajOsemsmerovku(pole, fr, m, n);


// Nacitavanie vyskytov pismen do dynamickeho pola index

    c = 'A';

    while (c <= 'Z'){
        for (y=0; y<m; y++){
            for (x=0; x<n; x++){
                if (pole[y][x] == c){
                    if (abcZ == 0)
                        index[abcP] = (int *) malloc(abcV * sizeof(int));
                    if (abcV <= abcZ + 2){
                        abcV = abcV + 2*N;
                        index[abcP] = (int *) realloc(index[abcP], sizeof(int)*abcV);
                        }
                    index[abcP][abc] = y;
                    abc++;
                    abcZ++;
                    index[abcP][abc] = x;
                    abc++;
                    abcZ++;
                }
            }
        }
    indexP[abcP][0] = abcZ;

    if (indexP[abcP][0] == 0)
        index[abcP] = NULL;

    if (indexP[abcP][0] > 0)
        indexP[abcP][1] = abcV;

    for (i=abcZ; i<abcV; i++)
        index[abcP][i] = -1;

    c++;
    abcP++;
    abc=0;
    abcZ=0;
    abcV=0;
    }

    urciSmer(fr, pole, index, indexP, m, n);

    for (y=0; y<m; y++){
        for (x=0; x<n; x++){
            if (pole[y][x] >= 'A' && pole[y][x] <= 'Z'){
                printf("%c", pole[y][x]);
                tajnicka++;
            }
        }
    }

    if (tajnicka == 0)
        printf("Tajnicka je prazdna\n");

// Uvolnenie pamate

    for (i=0; i<26; i++)
        free(index[i]);

    for (i=0; i<n; i++)
        free(pole[i]);

    free(pole);

    if (fclose(fr) == EOF){
        printf("Subor sa nepodarilo zatvorit\n");
        return 0;
    }

    return 0;
}
