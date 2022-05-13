#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

int main()
{
    int N, i, krava /*, kravaPredosla*/;
    int max = 0, maxNove = 0, maxPredosle = 0;
    bool predosliPouzity = false;

    while (scanf("%d", &N) == 1) {

        for (i=0; i<N; i++){
            scanf("%d", &krava);

            if (predosliPouzity == false){
                    maxPredosle = max;
                    max += krava;
                    predosliPouzity = true;
                    //kravaPredosla = krava;
            }

            else if (predosliPouzity == true){

                if (max < maxPredosle + krava){
                    maxNove = maxPredosle + krava;
                    maxPredosle = max;
                    max = maxNove;

                    predosliPouzity = true;
                    //kravaPredosla = krava;
                }

                else {
                    predosliPouzity = false;
                    //kravaPredosla = krava;
                }
            }
        }

        printf("%d\n", max);

        predosliPouzity = false;
        max = 0;
        maxNove = 0;
        maxPredosle = 0;
    }

    return 0;
}
