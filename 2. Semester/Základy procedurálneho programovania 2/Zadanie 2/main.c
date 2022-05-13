#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#define MAX 101

typedef struct meno { // struktura pre meno
    char krstne_meno[MAX];
    char priezvisko[MAX];
} MENO;

typedef struct herec { // struktura pre herca
    MENO meno;
    int rok_narodenia;
    struct herec *dalsi_herec;
} HEREC;

typedef struct film { // struktura pre film
    char nazov[MAX];
    int rok;
    MENO reziser;
    HEREC *herci;
    struct film *dalsi_film;
} FILM;


FILM *nacitaj(FILE *fr){

    int i, j;
    char c;

    int rok, narodenie;
    char nazov[MAX], reziserKr[MAX], reziserPr[MAX], herecKr[MAX], herecPr[MAX];

    FILM *zoznam;
    FILM **novy_film = &zoznam;

    HEREC **novy_herec;

    while ((c=getc(fr)) != EOF){

        *novy_film = malloc(sizeof(FILM));
        novy_herec = &((*novy_film)->herci);

        fseek(fr, -1, SEEK_CUR);

        for (i=0; i<MAX; i++){
            nazov[i] = '\0';
            reziserKr[i] = '\0';
            reziserPr[i] = '\0';
            herecKr[i] = '\0';
            herecPr[i] = '\0';
        }

        //nazov
        fgets(nazov, MAX, fr);
        nazov[strlen(nazov)-1] = '\0';
        // printf("%s\n", nazov);

        //rok
        fscanf(fr, "%d ", &rok);
        // printf("%d\n", rok);

        //reziser
        fscanf(fr, "%s ", &reziserKr);
        // printf("%s ", reziserKr);

        fscanf(fr, "%s ", &reziserPr);
        // printf("%s\n", reziserPr);

        //zapis nazov, rok, reziser
        strcpy((*novy_film)->nazov, nazov);
        (*novy_film)->rok = rok;
        strcpy((*novy_film)->reziser.krstne_meno,reziserKr);
        strcpy((*novy_film)->reziser.priezvisko,reziserPr);


        //herci
        while ((c=getc(fr)) == '*'){
            c=getc(fr);

            *novy_herec = malloc(sizeof(HEREC));

            fscanf(fr, "%s ", &herecKr);
             //printf("%s ", herecKr);

            fscanf(fr, "%s ", &herecPr);
             //printf("%s\n", herecPr);

            fscanf(fr, "%d ", &narodenie);
             //printf("%d\n", narodenie);

            // zapis hercov
            strcpy((*novy_herec)->meno.krstne_meno, herecKr);
            strcpy((*novy_herec)->meno.priezvisko, herecPr);
            (*novy_herec)->rok_narodenia = narodenie;

            novy_herec = &((*novy_herec)->dalsi_herec);

            for (j=0; j<MAX; j++)
                herecKr[j] = '\0';
                herecPr[j] = '\0';
        }

        (*novy_herec) = NULL;

        //printf("\n");

        novy_film = &((*novy_film)->dalsi_film);

        if (c != EOF)
            fseek(fr, -1, SEEK_CUR);
    }

    (*novy_film) = NULL;

    return zoznam;
}

void vypis(FILM *zoznam){

    FILM *akt = zoznam;
    HEREC *aktHerec;

    if (akt == NULL){
        printf("Nie je k dispozicii ziadny zoznam\n");
        return;
    }

    while (akt != NULL){
        printf("%s (%d) %s %s\n\tHraju: ", akt->nazov, akt->rok, akt->reziser.krstne_meno, akt->reziser.priezvisko);
        aktHerec = akt->herci;

        while (aktHerec != NULL){
            printf("%s %s (%d)", aktHerec->meno.krstne_meno, aktHerec->meno.priezvisko, aktHerec->rok_narodenia);
            aktHerec = aktHerec->dalsi_herec;

            if (aktHerec != NULL)
                printf(", ");
        }
        printf("\n");
        akt = akt->dalsi_film;
    }
}

FILM *pridaj(FILM *zoznam){

    int rok, narodenie, herec=0;
    char nazov[MAX], reziserKr[MAX], reziserPr[MAX], herecKr[MAX], herecPr[MAX];

    FILM *novy_film = malloc(sizeof(FILM));
    FILM *koniec = zoznam;

    novy_film->dalsi_film = NULL;

    HEREC **novy_herec = &(novy_film->herci);

    //nazov
    gets(nazov);
    strcpy(novy_film->nazov, nazov);

    //rok
    scanf("%d", &rok);
    novy_film->rok = rok;

    //reziser
    scanf("%s", &reziserKr);
    strcpy(novy_film->reziser.krstne_meno, reziserKr);
    scanf("%s", &reziserPr);
    strcpy(novy_film->reziser.priezvisko, reziserPr);

    //herci
    do{
        scanf("%s", &herecKr);
        if (!(strcmp(herecKr, "*")))
            break;
        scanf("%s", &herecPr);
        scanf("%d", &narodenie);

        *novy_herec = malloc(sizeof(HEREC));

        strcpy((*novy_herec)->meno.krstne_meno, herecKr);
        strcpy((*novy_herec)->meno.priezvisko, herecPr);
        (*novy_herec)->rok_narodenia = narodenie;

        novy_herec = &((*novy_herec)->dalsi_herec);
        //printf("Z: %s %s %d\n", herecKr, herecPr, narodenie);
    }
    while(1);

    (*novy_herec) = NULL;

    //ziadny zoznam
    if (koniec == NULL){
        zoznam = novy_film;
        return zoznam;
    }

    //pridanie na koniec zoznamu
    while( koniec->dalsi_film != NULL ){
        koniec = koniec->dalsi_film;
    }
    koniec->dalsi_film = novy_film;

    return zoznam;
}

FILM *vymaz(FILM *zoznam){

    char nazov[MAX];

    FILM **akt = &zoznam;
    FILM *posun;

    HEREC *dalsi;
    HEREC *vymaz;

    if (zoznam == NULL){
        printf("Nie je k dispozicii ziadny zoznam\n");
        return NULL;
    }

    gets(nazov);

    while( (*akt)!= NULL){

        if ( !(strcmp((*akt)->nazov, nazov)) ){
            posun = (*akt)->dalsi_film;

            dalsi = (*akt)->herci;
            vymaz = dalsi;

            //mazanie
            while(dalsi != NULL){
                dalsi = dalsi->dalsi_herec;
                free(vymaz);
                vymaz = dalsi;
            }

            free( (*akt) );
            (*akt) = posun;

            return zoznam;
        }
        akt = &((*akt)->dalsi_film);
    }

    return zoznam;
}

void filmy(FILM *zoznam){

    char meno[MAX], priezvisko[MAX];

    FILM *film = zoznam;
    HEREC *herec;

    if  (zoznam == NULL){
        printf("Nie je k dispozicii ziadny zoznam\n");
        return;
    }

    scanf("%s %s", &meno, &priezvisko);

    while(film != NULL){
        herec = film->herci;

        while(herec != NULL){
            //hladanie
            if ( !(strcmp(herec->meno.krstne_meno, meno) && strcmp(herec->meno.priezvisko, priezvisko)) ){
                printf("%s (%d)\n", film->nazov, film->rok);
                break;
            }
            herec = herec->dalsi_herec;
        }
        film = film->dalsi_film;
    }
}

void herci(FILM *zoznam){

    int kontrola = 0, pocet = 0;
    char film1[MAX], film2[MAX];

    FILM *film = zoznam;
    HEREC *herec1, *herec2, *herec2Z;

    if  (zoznam == NULL){
        printf("Nie je k dispozicii ziadny zoznam\n");
        return;
    }

    //fimy
    gets(film1);
    gets(film2);

    while(film != NULL){

        //hladanie filmov
        if ( !(strcmp(film->nazov, film1)) || !(strcmp(film->nazov, film2)) ){
            kontrola++;

            if (kontrola == 1)
                herec1 = film->herci;
            else
                herec2 = film->herci;
        }
        film = film->dalsi_film;
    }
    if (kontrola != 2){
        printf("Nenasli sa 2 filmy\n");
        return;
    }

    herec2Z = herec2;

    while(herec1 != NULL){
        herec2 = herec2Z;

        while(herec2 != NULL){

            //kontrola duplicit a vypis
            if ( !(strcmp(herec1->meno.krstne_meno, herec2->meno.krstne_meno)) && !(strcmp(herec1->meno.priezvisko, herec2->meno.priezvisko))){
                if (pocet > 0)
                    printf(", ");
                printf("%s %s (%d)", herec1->meno.krstne_meno, herec1->meno.priezvisko, herec1->rok_narodenia);
                pocet++;
                break;
            }
            herec2 = herec2->dalsi_herec;
        }
        herec1 = herec1->dalsi_herec;
    }
    printf("\n");
}

void rok(FILM *zoznam){

    int rok;
    bool a=false, b=false, c=false;

    FILM *film = zoznam;
    HEREC *herec, *herci_zoznam = NULL, **herec_ptr = &herci_zoznam;

    if  (zoznam == NULL){
        printf("Nie je k dispozicii ziadny zoznam\n");
        return;
    }

    scanf("%d", &rok);

    while(film != NULL){

        if (film->rok == rok){

            herec = film->herci;
            //vytvorenie pomocneho zoznamu
            while(herec != NULL){
                *herec_ptr = malloc(sizeof(HEREC));

                strcpy ((*herec_ptr)->meno.krstne_meno, herec->meno.krstne_meno);
                strcpy ((*herec_ptr)->meno.priezvisko, herec->meno.priezvisko);
                (*herec_ptr)->rok_narodenia = herec->rok_narodenia;

                (*herec_ptr)->dalsi_herec = NULL;
                herec_ptr = &((*herec_ptr)->dalsi_herec);
                herec = herec->dalsi_herec;
            }
        }
        film = film->dalsi_film;
    }

    HEREC **herec1 = &herci_zoznam;
    HEREC **herec2 = &herci_zoznam;
    HEREC *vymaz;

    while((*herec1) != NULL){

        herec2 = &herci_zoznam;
        while((*herec2) != NULL){

            if ( (*herec1) != (*herec2) ){
                //hladanie duplicit
                a = !strcmp((*herec1)->meno.krstne_meno,(*herec2)->meno.krstne_meno);
                b = !strcmp((*herec1)->meno.priezvisko,(*herec2)->meno.priezvisko);
                c = ((*herec1)->rok_narodenia == (*herec2)->rok_narodenia);

                //mazanie duplicity
                if ( a && b && c ){
                    vymaz = (*herec2);
                    (*herec2) = (*herec2)->dalsi_herec;
                    free(vymaz);
               }
            }
            if ((*herec2) != NULL)
                herec2 = &((*herec2)->dalsi_herec);
        }
        if ((*herec1) != NULL)
            herec1 = &((*herec1)->dalsi_herec);
    }

    //snaha o usporiadanie
    int posun = 0, pocet = 0;
    herec = herci_zoznam;
    while(herec != NULL){
        pocet++;
        if (herec->dalsi_herec == NULL){
            break;
        }
        herec = herec->dalsi_herec;
    }

    herec = herci_zoznam;
    while(herec != NULL){
        printf("%s %s (%d)\n", herec->meno.krstne_meno, herec->meno.priezvisko, herec->rok_narodenia);
        herec = herec->dalsi_herec;
    }

    HEREC *herec_p;
    herec = herci_zoznam;
    while(herec != NULL){
        herec_p = herec->dalsi_herec;
        free(herec);
        herec = herec_p;
    }

}

FILM *koniec(FILM *zoznam){

    FILM *film = zoznam;
    HEREC *herec, *herec_dalsi;

    //uvolnovanie pamate
    while(zoznam != NULL){
        herec = zoznam->herci;
        film = zoznam->dalsi_film;

        while(herec != NULL){
            herec_dalsi = herec->dalsi_herec;
            free(herec);
            herec = herec_dalsi;
        }
        free(zoznam);
        zoznam = film;
    }
    zoznam = NULL;

    return zoznam;
}

int porovnaj(char *slovo1, char *slovo2){ //pomocna funkcia na zistenie, ktore slovo je abecedne prve

    int i = 0;
    int kratsie;

    if (strlen(slovo1) < strlen(slovo2))
        kratsie = strlen(slovo1);
    else
        kratsie = strlen(slovo2);

    while( i < kratsie ){

        if (slovo1[i] > slovo2[i]){
            return 2;
        }
        if (slovo1[i] < slovo2[i]){
            return 1;
        }
        i++;
    }

    if (slovo1[kratsie] > slovo2[kratsie])
        return 2;
    if (slovo1[kratsie] < slovo2[kratsie])
        return 1;

    return 1;
}

int main()
{
    int i = 0;
    char c, prikaz[MAX];

    FILM *zoznam = NULL;
    FILE *fr;


    if ((fr = fopen("filmy.txt","r")) == NULL){
        printf("Subor filmy.txt sa nepodarilo otvorit.\n");
        return 1;
    }

    while ( strcmp(prikaz, "koniec") ){

        i = 0;

        while( (c=getchar()) != '\n' ){
            prikaz[i] = c;
            i++;
        }
        prikaz[i] = '\0';

        if ( !(strcmp(prikaz, "nacitaj")) ){
            if (zoznam == NULL)
                zoznam = nacitaj(fr);

            else {
                zoznam = koniec(zoznam);
                rewind(fr);
                zoznam = nacitaj(fr);
            }
        }

        if ( !(strcmp(prikaz, "vypis")) )
            vypis(zoznam);

        if ( !(strcmp(prikaz, "pridaj")) )
            zoznam = pridaj(zoznam);

        if ( !(strcmp(prikaz, "vymaz")) )
            zoznam = vymaz(zoznam);

        if ( !(strcmp(prikaz, "filmy")) )
            filmy(zoznam);

        if ( !(strcmp(prikaz, "herci")) )
            herci(zoznam);

        if ( !(strcmp(prikaz, "rok")) )
            rok(zoznam);

        if ( !(strcmp(prikaz, "koniec")) ){
            zoznam = koniec(zoznam);
            break;
            }

        else
            printf("Neplatny prikaz\n");

        for (int i=0; i<strlen(prikaz); i++)
            prikaz[i] = '\0';
    }

    fclose(fr);
    return 0;
}
