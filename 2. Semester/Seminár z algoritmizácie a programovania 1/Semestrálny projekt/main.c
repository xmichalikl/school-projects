//Lukas Michalik - Semestralny projekt SEMAP
#include <stdio.h>
#include <limits.h>

int **vytvorMesto(int N){ //funkcia na vytvorenie mesta (alokovanie potrebnej pamate)

    int i;
    int **pole;

    pole = (int **) malloc(N * sizeof(int *));
    for (i=0; i<N; i++)
        pole[i] = (int *) malloc(N * sizeof(int));

    return pole;
}

int **nacitajMesto(int **pole, int N){ //funkcia, ktora nacitava vzdialenosti (hodnoty matice) medzi domami

    int x, y;
    int cesta;

    for (y=0; y<N; y++){ //2 cykly, cez ktore sa nacitavaju jednotlive hodnoty
        for (x=0; x<N; x++){
            scanf("%d", &cesta);
            pole[y][x] = cesta;
        }
    }
    return pole;
}

int najdiNajkratsiuCestu(int **pole, int N, int min, int minM){ //"Brute Force" funkcia, ktora prebehne vsetky mozne kombinacie ciest a postupne si uklada najkratsiu z nich

    int krok = 0; //urcuje, kolko krokov Jakubko spravil

    for (int a=1; a<N; a++){
        krok++;

        for (int b=1; b<N; b++){
            krok++;

            if (b==a && krok != N){
                krok--;
                continue;
                }

            if (krok == N){
                min += pole[0][a] + pole[a][0];
                if (min < minM)
                    minM = min;
                min=0;
                }

            /////////////////////////PRINCIP VSADE ROVNAKY, PODLA VELKOSTI MATICE (napr.: N=10 -> 10 prejdenych cyklov)//////////////////////////////
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            for (int c=1; c<N; c++){ //spustenie cyklu, ktory predstavuje dalsi krok
                krok++;

                if ((c==a || c==b) && krok != N ){ //pokial sa nova pozicia rovna niektorej z predchadzajucej a zaroven sa krok nerovna velkosti matice, preskoci ju a krok sa odpocita
                    krok--;
                    continue;
                    }

                if (krok == N){ //ked Jakubko presiel dostatocny pocet krokov na  navstivenie vsetkych domov, tak
                        min += pole[0][a] + pole[a][b] + pole[b][0]; //min = suctu hodnot predchadzajucich krokov
                        if (min < minM) //pokial je nove min mensie, ako minM,
                            minM = min; //tak sa minM prepise na min
                        min=0; //a min sa vynuluje
                    }

            //nasledne sa vnori do dalsieho cyklu(dalsia pozicia)
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                for (int d=1; d<N; d++){
                    krok++;

                    if (d==a || d==b || d==c && krok != N){
                        krok--;
                        continue;
                    }

                    if (krok == N){
                        min += pole[0][a] + pole[a][b] + pole[b][c] + pole[c][0];
                        if (min < minM)
                            minM = min;
                        min=0;
                    }

                   for (int e=1; e<N; e++){
                        krok++;

                        if (e==a || e==b || e==c || e==d && krok != N){
                            krok--;
                            continue;
                        }

                        if (krok == N){
                            min += pole[0][a] + pole[a][b] + pole[b][c] + pole[c][d] + pole[d][0];
                            if (min < minM)
                            minM = min;
                            min=0;
                        }


                        for (int f=1; f<N; f++){
                            krok++;

                            if (f==a || f==b || f==c || f==d || f==e && krok != N){
                                krok--;
                                continue;
                            }

                            if (krok == N){
                                min += pole[0][a] + pole[a][b] + pole[b][c] + pole[c][d] + pole[d][e] + pole[e][0];
                                if (min < minM)
                                minM = min;
                                min=0;
                            }


                            for (int g=1; g<N; g++){
                                krok++;

                                if (g==a || g==b || g==c || g==d || g==e || g==f && krok != N){
                                    krok--;
                                    continue;
                                }

                                if (krok == N){
                                    min += pole[0][a] + pole[a][b] + pole[b][c] + pole[c][d] + pole[d][e] + pole[e][f] + pole[f][0];
                                    if (min < minM)
                                    minM = min;
                                    min=0;
                                }


                                for (int h=1; h<N; h++){
                                    krok++;

                                    if (h==a || h==b || h==c || h==d || h==e || h==f || h==g && krok != N){
                                        krok--;
                                        continue;
                                    }

                                    if (krok == N){
                                        min += pole[0][a] + pole[a][b] + pole[b][c] + pole[c][d] + pole[d][e] + pole[e][f] + pole[f][g] + pole[g][0];
                                        if (min < minM)
                                        minM = min;
                                        min=0;
                                    }


                                    for (int i=1; i<N; i++){
                                        krok++;

                                        if (i==a || i==b || i==c || i==d || i==e || i==f || i==g || i==h && krok != N){
                                            krok--;
                                            continue;
                                        }

                                        if (krok == N){
                                            min += pole[0][a] + pole[a][b] + pole[b][c] + pole[c][d] + pole[d][e] + pole[e][f] + pole[f][g] + pole[g][h] + pole[h][0];
                                            if (min < minM)
                                            minM = min;
                                            min=0;
                                        }


                                        for (int j=1; j<N; j++){
                                            krok++;

                                            if (j==a || j==b || j==c || j==d || j==e || j==f || j==g || j==h || j==i && krok != N){
                                                krok--;
                                                continue;
                                            }

                                            if (krok == N){
                                                min += pole[0][a] + pole[a][b] + pole[b][c] + pole[c][d] + pole[d][e] + pole[e][f] + pole[f][g] + pole[g][h] + pole[h][i] + pole[i][0];
                                                if (min < minM)
                                                minM = min;
                                                min=0;
                                            }


                                            for (int k=1; k<N; k++){
                                                krok++;

                                                if (k==a || k==b || k==c || k==d || k==e || k==f || k==g || k==h || k==i || k==j && krok != N){
                                                    krok--;
                                                    continue;
                                                }

                                                if (krok == N){
                                                    min += pole[0][a] + pole[a][b] + pole[b][c] + pole[c][d] + pole[d][e] + pole[e][f] + pole[f][g] + pole[g][h] + pole[h][i] + pole[i][j] + pole[j][0];
                                                    if (min < minM)
                                                    minM = min;
                                                    min=0;
                                                }


                                                for (int l=1; l<N; l++){
                                                    krok++;

                                                    if (l==a || l==b || l==c || l==d || l==e || l==f || l==g || l==h || l==i || l==j || l==k && krok != N){
                                                        krok--;
                                                        continue;
                                                    }

                                                    if (krok == N){
                                                        min += pole[0][a] + pole[a][b] + pole[b][c] + pole[c][d] + pole[d][e] + pole[e][f] + pole[f][g] + pole[g][h] + pole[h][i] + pole[i][j] + pole[j][k] + pole[k][0];
                                                        if (min < minM)
                                                        minM = min;
                                                        min=0;
                                                    }

                                                    for (int m=1; m<N; m++){
                                                        krok++;

                                                        if (m==a || m==b || m==c || m==d || m==e || m==f || m==g || m==h || m==i || m==j || m==k || m==l && krok != N){
                                                            krok--;
                                                            continue;
                                                        }

                                                        if (krok == N){
                                                            min += pole[0][a] + pole[a][b] + pole[b][c] + pole[c][d] + pole[d][e] + pole[e][f] + pole[f][g] + pole[g][h] + pole[h][i] + pole[i][j] + pole[j][k] + pole[k][l] + pole[l][0];
                                                            if (min < minM)
                                                            minM = min;
                                                            min=0;
                                                        }

                                                        for (int n=1; n<N; n++){
                                                            krok++;

                                                            if (n==a || n==b || n==c || n==d || n==e || n==f || n==g || n==h || n==i || n==j || n==k || n==l || n==m && krok != N){
                                                                krok--;
                                                                continue;
                                                            }

                                                            if (krok == N){
                                                                min += pole[0][a] + pole[a][b] + pole[b][c] + pole[c][d] + pole[d][e] + pole[e][f] + pole[f][g] + pole[g][h] + pole[h][i] + pole[i][j] + pole[j][k] + pole[k][l] + pole[l][m] + pole[m][0];
                                                                if (min < minM)
                                                                minM = min;
                                                                min=0;
                                                            }


                                                            for (int o=1; o<N; o++){
                                                                krok++;

                                                                if (o==a || o==b || o==c || o==d || o==e || o==f || o==g || o==h || o==i || o==j || o==k || o==l || o==m || o==n && krok != N){
                                                                    krok--;
                                                                    continue;
                                                                }

                                                                if (krok == N){
                                                                    min += pole[0][a] + pole[a][b] + pole[b][c] + pole[c][d] + pole[d][e] + pole[e][f] + pole[f][g] + pole[g][h] + pole[h][i] + pole[i][j] + pole[j][k] + pole[k][l] + pole[l][m] + pole[m][n] + pole[n][0];
                                                                    if (min < minM)
                                                                    minM = min;
                                                                    min=0;
                                                                }


                                                                for (int p=1; p<N; p++){
                                                                    krok++;

                                                                    if (p==a || p==b || p==c || p==d || p==e || p==f || p==g || p==h || p==i || p==j || p==k || p==l || p==m || p==n || p==o && krok != N){
                                                                        krok--;
                                                                        continue;
                                                                    }

                                                                    if (krok == N){
                                                                        min += pole[0][a] + pole[a][b] + pole[b][c] + pole[c][d] + pole[d][e] + pole[e][f] + pole[f][g] + pole[g][h] + pole[h][i] + pole[i][j] + pole[j][k] + pole[k][l] + pole[l][m] + pole[m][n] + pole[n][o] + pole[o][0];
                                                                        if (min < minM)
                                                                        minM = min;
                                                                        min=0;
                                                                    }


                                                                    for (int q=1; q<N; q++){
                                                                        krok++;

                                                                        if (q==a || q==b || q==c || q==d || q==e || q==f || q==g || q==h || q==i || q==j || q==k || q==l || q==m || q==n || q==o || q==p && krok != N){
                                                                            krok--;
                                                                            continue;
                                                                        }

                                                                        if (krok == N){
                                                                            min += pole[0][a] + pole[a][b] + pole[b][c] + pole[c][d] + pole[d][e] + pole[e][f] + pole[f][g] + pole[g][h] + pole[h][i] + pole[i][j] + pole[j][k] + pole[k][l] + pole[l][m] + pole[m][n] + pole[n][o] + pole[o][p] + pole[p][0];
                                                                            if (min < minM)
                                                                            minM = min;
                                                                            min=0;
                                                                        }


                                                                        for (int r=1; r<N; r++){
                                                                            krok++;

                                                                            if (r==a || r==b || r==c || r==d || r==e || r==f || r==g || r==h || r==i || r==j || r==k || r==l || r==m || r==n || r==o || r==p || r==q && krok != N){
                                                                                krok--;
                                                                                continue;
                                                                            }

                                                                            if (krok == N){
                                                                                min += pole[0][a] + pole[a][b] + pole[b][c] + pole[c][d] + pole[d][e] + pole[e][f] + pole[f][g] + pole[g][h] + pole[h][i] + pole[i][j] + pole[j][k] + pole[k][l] + pole[l][m] + pole[m][n] + pole[n][o] + pole[o][p] + pole[p][q] + pole[q][0];
                                                                                if (min < minM)
                                                                                minM = min;
                                                                                min=0;
                                                                            }


                                                                            for (int s=1; s<N; s++){
                                                                                krok++;

                                                                                if (s==a || s==b || s==c || s==d || s==e || s==f || s==g || s==h || s==i || s==j || s==k || s==l || s==m || s==n || s==o || s==p || s==q || s==r && krok != N){
                                                                                    krok--;
                                                                                    continue;
                                                                                }

                                                                                if (krok == N){
                                                                                    min += pole[0][a] + pole[a][b] + pole[b][c] + pole[c][d] + pole[d][e] + pole[e][f] + pole[f][g] + pole[g][h] + pole[h][i] + pole[i][j] + pole[j][k] + pole[k][l] + pole[l][m] + pole[m][n] + pole[n][o] + pole[o][p] + pole[p][q] + pole[q][r] + pole[r][0];
                                                                                    if (min < minM)
                                                                                    minM = min;
                                                                                    min=0;
                                                                                }


                                                                                for (int t=1; t<N; t++){
                                                                                    krok++;

                                                                                    if (t==a || t==b || t==c || t==d || t==e || t==f || t==g || t==h || t==i || t==j || t==k || t==l || t==m || t==n || t==o || t==p || t==q || t==r || t==s && krok != N){
                                                                                        krok--;
                                                                                        continue;
                                                                                    }

                                                                                    if (krok == N){
                                                                                        min += pole[0][a] + pole[a][b] + pole[b][c] + pole[c][d] + pole[d][e] + pole[e][f] + pole[f][g] + pole[g][h] + pole[h][i] + pole[i][j] + pole[j][k] + pole[k][l] + pole[l][m] + pole[m][n] + pole[n][o] + pole[o][p] + pole[p][q] + pole[q][r] + pole[r][s] + pole[s][0];
                                                                                        if (min < minM)
                                                                                        minM = min;
                                                                                        min=0;

                                                                                    krok--;
                                                                                    }
                                                                                krok--;
                                                                                }
                                                                            krok--;
                                                                            }
                                                                        krok--;
                                                                        }
                                                                    krok--;
                                                                    }
                                                                krok--;
                                                                }
                                                            krok--;
                                                            }
                                                        krok--;
                                                        }
                                                    krok--;
                                                    }
                                                krok--;
                                                }
                                            krok--;
                                            }
                                        krok--;
                                        }
                                    krok--;
                                    }
                                krok--;
                                }
                            krok--;
                            }
                        krok--;
                        }
                    krok--;
                    }
                krok--;
                }
            krok--;
            }
        krok--;
        }
    krok--;
    }

    return minM;
}

int main()
{

    int N, x, y, i, j, cesta;
    int min = 0, minM = INT_MAX; // vynulovanie min a minM;
    int **pole;


    while( (scanf("%d", &N)) == 1 ){ //"nekonecny" cyklus, na opatovne nacitanie matic

        if (N > 20 || N <= 0){ //pokial je zadany zly rozmer, preskoci ho
            printf("Zly rozmer\n\n");
            continue;
        }

        if (N == 1){
            printf("Jakubko zostane doma\n\n");
            continue;
        }

        pole = vytvorMesto(N); // volanie funkcie vytvorMesto na alokovanie potrebnej pamate

        nacitajMesto(pole, N); //volanie funkcie nacitajMesto na vlozenie dlzok ciest medzi kazdym domom

        minM = najdiNajkratsiuCestu(pole, N, min, minM); //volanie funkcie najdiNajkratsiuCestu, ktora zisti najkratsiu cestu

        printf("Najkratsia cesta je za %d min\n\n", minM); //vypis najkratsej cesty

        min = 0; minM = INT_MAX; // opatovne vynulovanie min a minM

        for (i=0; i<N; i++) // uvolnenie alokovanej casti pamate
            free(pole[i]);
        free(pole);
    }

    return 0;
}
