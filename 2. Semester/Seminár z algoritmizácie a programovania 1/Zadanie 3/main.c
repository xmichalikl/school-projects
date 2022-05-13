// Rekurzivny postup hladania min. poctu vlozenia znakov pre vytvorenie palindromu
#include <stdio.h>
#include <string.h>
#include <ctype.h>
#define MAX 1000

int min(int a, int b) // funkcia na zistenie minima
{
    if  (a < b) // ked je vacsie b, vrati hodnotu a
        return a;
    else    // inak vrati hodnotu b
        return b;
}

int najdiMinPocet(char str[], int l, int h) // rekurzivna funkcia na zistenie min. poctu vlozenia znakov pre palindrom
{
    /*str[l] = toupper(str[l]);
    str[h] = toupper(str[h]);

    printf("Kontrolujem %s [%d][%d] ", str, l, h);

    str[l] = tolower(str[l]);
    str[h] = tolower(str[h]);*/

	// Zakladne pripady
	if (l == h){    // pokial sa prvy a posledny znak rovnaju, tak vrati 0
        //printf("Return 0 ");
        return 0;
        }

	if (l == h - 1){  // pokial sa prvy a predposledny znak rovanju
        if (str[l] == str[h]){  // pokial sa znak na prvom indexe rovna znaku na poslednom indexe, tak vrati 0
            //printf("Return 0 ");
            return 0;
            }
        else{
           // printf("Return 1 ");
            return 1;  // inak vrati 1
            }
    }

	if (str[l] == str[h]){  // pokial sa rovnaju znaky na prvom indexe a poslednom indexe
        //printf("\n -A- ");
        return  najdiMinPocet(str, l + 1, h - 1); //tak sa znovu zavola rekurzivne funkcia s posunutym prvym idexom l+1 a posledym indexom h-1
    }

    else{
        //printf("\n -B- ");
        return (min(najdiMinPocet(str, l, h - 1), najdiMinPocet(str, l + 1, h)) + 1); // inak sa najskor znovu zavola rekurzivne funkcia s posunutym prvym idexom l+1 a potom s posunutym poslednym indexom h-1
    }   // nakoniec sa postupne na zaklade vratenych 1 alebo 0 scita vysledok
}

int main() // Funkcia main
{
    int i;
	char str[MAX];

    while( (scanf("%s", str)) == 1 ){ // nekonecny cyklus na nacitanie retazcov

        if (strlen(str) > MAX){ // podmienka pri prekroceni MAX
            for (i=0; i<strlen(str); i++)   // vynulovanie pola str[]
                str[i] == '\0';
            printf("Priliz dlhy retazec\n");
            continue;
        }

        printf("%d", najdiMinPocet(str, 0, strlen(str)-1)); // volanie funkcie najdiMinPocet a nasledny vypis min. poctu doplnenia znakov
        printf("\n");

        for (i=0; i<strlen(str); i++)   // vynulovanie pola str[]
            str[i] == '\0';
    }
	return 0;
}
