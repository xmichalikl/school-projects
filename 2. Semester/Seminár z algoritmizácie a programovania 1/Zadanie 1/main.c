// Luk� Michal�k - SEMAP - Zadanie �.1
#include <stdio.h>
#include <stdlib.h>
#define MAX 1000

//Funkcia na v�pis obsahu pol� do �tandardn�ho v�stupu
void vypisPole(int pocet, int sluzba[], char vojaci[]){

    int i;

    printf("\n");
    for(i=0; i<pocet; i++){
       printf("%c", vojaci[i]); //Vyp�e v�etky prvky pola vojaci
       printf("%d ", sluzba[i]);    //Vyp�e v�etky prvky pola sluzba
    }
}

//Funkcia na usporidanie poradia v poli sluzba
char sort(int pocet, int sluzba[], char vojaci[]){

    int i, j, index1, index2, vacsie;
    char c;

    c = 'A'; //Za�iato�n� poz�cia cyklu abecedy

    while(c <= 'Z'){ //Prebehne cez cel� abecedu

        for(i=0; i<pocet; i++){ //Prebehne cez cel� pole vojaci

            if (vojaci[i] == c){ //Porovnanie prvku v poli vojaci s p�smenom v abecede
                index1 = i; //Pokia� n�jde zhodu, ulo�� sa do do�asnej premennej idenx1

                for(j=i+1; j<pocet; j++){ //Prebehne cez zvy�n� prvky v poli vojaci

                    if (vojaci[j] == c){ //Porovnanie prvku v poli vojaci s p�smenom v abecede
                        index2 = j; //Pokia� n�jde zhodu, ulo�� sa do do�asnej premennej idenx2

                        if (sluzba[index1] > sluzba[index2]){ //Porovnanie premenn�ch index1 a index2
                            vacsie = sluzba[index1];    //Pokia� je predch�dzaj�ci prvok index 1 v���, ulo�� sa do premennej v��ie
                            sluzba[index1] = sluzba[index2];    //V�mena prvkov
                            sluzba[index2] = vacsie;            //V�mena prvkov
                        }
                    }
                }
            }
        }
    c++;    //Posun v abecede
    }

    return sluzba, vojaci;
}

//Funkcia na vynulovanie pol�
char vynulujPole(int sluzba[], char vojaci[]){

    int i;

    for(i=0; i<MAX; i++){
        vojaci[i] = '\0';   //Nuluje v�etky prvky pola vojaci
        sluzba[i] = 0;  //Nuluje v�etky prvky pola sluzba
    }

    return sluzba, vojaci;
}

//Funkcia na na��tanie �dajov do pol�
char nacitajPole(int sluzba[], char vojaci[]){

    int i;
    char c;

    for(i=0; i<MAX; i++){
        vojaci[i] = getchar();  //Na��ta 1. typ vojaka
        scanf("%d", &sluzba[i]); //Na��ta 1. hodnotu sk�senosti
        c = getchar();  //Na��ta znak, bu� medzeru alebo enter
        if (c == ' ')   //Pokia� je to medzera, cyklus pokra�uje
            continue;
        if (c == '\n')  //Pokia� je to enter, cyklus skon��
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

    for (i=0; vojaci[i] != '\0'; i++) //Spo��tanie prvkov v poli vojaci
        pocet++;

    sort(pocet, sluzba, vojaci);
    vypisPole(pocet, sluzba, vojaci);

    return 0;
}
