// Lukáš Michalík - SEMAP - Zadanie è.1
#include <stdio.h>
#include <stdlib.h>
#define MAX 1000

//Funkcia na výpis obsahu polí do štandardného výstupu
void vypisPole(int pocet, int sluzba[], char vojaci[]){

    int i;

    printf("\n");
    for(i=0; i<pocet; i++){
       printf("%c", vojaci[i]); //Vypíše všetky prvky pola vojaci
       printf("%d ", sluzba[i]);    //Vypíše všetky prvky pola sluzba
    }
}

//Funkcia na usporidanie poradia v poli sluzba
char sort(int pocet, int sluzba[], char vojaci[]){

    int i, j, index1, index2, vacsie;
    char c;

    c = 'A'; //Zaèiatoèná pozícia cyklu abecedy

    while(c <= 'Z'){ //Prebehne cez celú abecedu

        for(i=0; i<pocet; i++){ //Prebehne cez celé pole vojaci

            if (vojaci[i] == c){ //Porovnanie prvku v poli vojaci s písmenom v abecede
                index1 = i; //Pokia¾ nájde zhodu, uloží sa do doèasnej premennej idenx1

                for(j=i+1; j<pocet; j++){ //Prebehne cez zvyšné prvky v poli vojaci

                    if (vojaci[j] == c){ //Porovnanie prvku v poli vojaci s písmenom v abecede
                        index2 = j; //Pokia¾ nájde zhodu, uloží sa do doèasnej premennej idenx2

                        if (sluzba[index1] > sluzba[index2]){ //Porovnanie premenných index1 a index2
                            vacsie = sluzba[index1];    //Pokia¾ je predchádzajúci prvok index 1 väèší, uloží sa do premennej väèšie
                            sluzba[index1] = sluzba[index2];    //Výmena prvkov
                            sluzba[index2] = vacsie;            //Výmena prvkov
                        }
                    }
                }
            }
        }
    c++;    //Posun v abecede
    }

    return sluzba, vojaci;
}

//Funkcia na vynulovanie polí
char vynulujPole(int sluzba[], char vojaci[]){

    int i;

    for(i=0; i<MAX; i++){
        vojaci[i] = '\0';   //Nuluje všetky prvky pola vojaci
        sluzba[i] = 0;  //Nuluje všetky prvky pola sluzba
    }

    return sluzba, vojaci;
}

//Funkcia na naèítanie údajov do polí
char nacitajPole(int sluzba[], char vojaci[]){

    int i;
    char c;

    for(i=0; i<MAX; i++){
        vojaci[i] = getchar();  //Naèíta 1. typ vojaka
        scanf("%d", &sluzba[i]); //Naèíta 1. hodnotu skúsenosti
        c = getchar();  //Naèíta znak, buï medzeru alebo enter
        if (c == ' ')   //Pokia¾ je to medzera, cyklus pokraèuje
            continue;
        if (c == '\n')  //Pokia¾ je to enter, cyklus skonèí
            break;
    }
    return sluzba, vojaci;
}


int main()
{
    int sluzba[MAX];
    char vojaci[MAX];
    int i, pocet = 0;

    vynulujPole(sluzba, vojaci);
    nacitajPole(sluzba, vojaci);

    for (i=0; vojaci[i] != '\0'; i++) //Spoèítanie prvkov v poli vojaci
        pocet++;

    sort(pocet, sluzba, vojaci);
    vypisPole(pocet, sluzba, vojaci);

    return 0;
}
