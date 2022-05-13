#include <stdio.h>
#include <stdlib.h>

#define MAX 1000

void nacitanie_do_pola (char povodny[], FILE *subor_sifra){

    if (fgets(povodny,MAX,subor_sifra) == NULL)
        printf("Spravu sa nepodarilo nacitat\n");
}

void vypis_povodnej_spravy (char povodny[]){

    if (povodny[0]=='\0'){
        printf("Sprava nie je nacitana\n");

        return;
    }

    printf("%s\n",povodny);
}

void uprava_textu (char povodny[], char upraveny[]){

    int pozicia_p=0, pozicia_u=0;
    char pismeno;

    if (povodny[0]=='\0'){
        printf("Sprava nie je nacitana\n");

        return;
    }

    while ((pismeno=povodny[pozicia_p]) !='\0'){

        pismeno = toupper(pismeno);

        if (pismeno>='A' && pismeno<='Z'){
            upraveny[pozicia_u] = pismeno;
            pozicia_u++;
        }

        pozicia_p++;
    }

    upraveny[pozicia_u] = '\0';
}

void vypis_upravenej_spravy (char upraveny[]){

    if (upraveny[0]=='\0'){
        printf("Nie je k dispozicii upravena sprava\n");

        return;
    }

    printf("%s\n",upraveny);
}

void vypis_slova (char povodny[]){


    int k, povodny_pozicia=0, slovo_pozocia=0, i;
    char slovo[100];

    if (povodny[0]=='\0'){
        printf("Sprava nie je nacitana\n");

        return;
    }

    scanf("%d",&k);

    if (k<1 || k>100)
        return;

    while (povodny[povodny_pozicia] != '\0'){

        while (povodny[povodny_pozicia] != ' ' && povodny[povodny_pozicia] != '\0' && povodny[povodny_pozicia] != '\n'){

            slovo[slovo_pozocia]=povodny[povodny_pozicia];
            povodny_pozicia++;
            slovo_pozocia++;
        }

        if (slovo_pozocia==k)
            printf("%s\n",slovo);

        for (i=0;i<100;i++)
            slovo[i]='\0';

        slovo_pozocia=0;

        if (povodny[povodny_pozicia] != '\0')
        povodny_pozicia=povodny_pozicia+1;
    }
}

void histogram(char upraveny[]){

    int pocet_pismen[26], upraveny_pozicia=0, i, j, k;
    float percenta_pismen[26], najvacie_percento=0;


    if (upraveny[0]=='\0'){
        printf("Nie je k dispozicii upravena sprava\n");

        return;
    }


    for (i=0;i<26;i++){
        pocet_pismen[i]=0;
        percenta_pismen[i]=0;
    }


    while (upraveny[upraveny_pozicia]!= '\0'){

        pocet_pismen[upraveny[upraveny_pozicia] - 'A']++;

        upraveny_pozicia++;
    }

    for (i=0; i<26; i++){
        percenta_pismen[i] = ((float)pocet_pismen[i] / upraveny_pozicia) *100;

        if (percenta_pismen[i]>najvacie_percento)
        najvacie_percento=percenta_pismen[i];
    }


    for (j=90; j>=0; j=j-10){

        if (j>=najvacie_percento)
            continue;

        for (k=0; k<26; k++){

            if (percenta_pismen[k]>j)
                putchar('*');

            else putchar(' ');
        }

        putchar('\n');
    }

    for (i=0; i<26; i++)
        putchar('A'+i);

    putchar('\n');
}

void cezarova_sifra(char upraveny[]){

    int n, i;
    char c;


     if (upraveny[0]=='\0'){
        printf("Nie je k dispozicii upravena sprava\n");

        return;
    }


    scanf("%d", &n);

    if (n<1 || n>25)
        return;

    for (i=0; upraveny[i] != '\0'; i++){

        c = upraveny[i] - n;

        if (c<'A')
            c = c+26;

        putchar(c);
    }

    putchar('\n');
}


int main()

{
    int i;
    char prikaz, povodny[MAX], upraveny[MAX];

    FILE *subor_sifra;


    if ((subor_sifra = fopen("sifra.txt","r")) == NULL){
        printf("Subor sifra.txt sa nepodarilo otvorit.\n");
        return 1;
    }

    for (i=0;i<MAX;i++){
        povodny[i]='\0';
        upraveny[i]='\0';
    }

    printf("n - nacitanie povodneho textu\n");
    printf("v - vypis povodneho textu\n");
    printf("u - uprava povodneho textu\n");
    printf("s - vypis upraveneho textu\n");
    printf("d - vypis slov danej dlzky\n");
    printf("h - vypis histogramu\n");
    printf("c - rozsifrovanie\n");
    printf("k - koniec programu\n\n");


    while ((prikaz=getchar()) != 'k'){

        switch(prikaz){
        case 'n': nacitanie_do_pola(povodny,subor_sifra); break;
        case 'v': vypis_povodnej_spravy(povodny); break;
        case 'u': uprava_textu(povodny, upraveny); break;
        case 's': vypis_upravenej_spravy(upraveny); break;
        case 'd': vypis_slova(povodny); break;
        case 'h': histogram(upraveny); break;
        case 'c': cezarova_sifra(upraveny); break;
        }
    }

    if (fclose(subor_sifra) == EOF){
        printf("Subor sifra.txt sa nepodarilo zatvorit.\n");
        return 1;
    }


    return 0;
}
