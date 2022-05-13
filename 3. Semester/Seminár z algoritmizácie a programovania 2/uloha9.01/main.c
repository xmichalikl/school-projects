#include <stdio.h>
#include <stdlib.h>

int min(int a, int b){

    if (a >= b)
        return b;
    else
        return a;
}

int main() {

    int N, i;
    int minC, minZ, minM;
    int minCN, minZN, minMN;
    int cervena, zelena, modra;


    while (scanf("%d", &N) == 1){

        if (N > 25)
            return;

        scanf("%d", &minC);
        scanf("%d", &minZ);
        scanf("%d", &minM);

        for (i=1; i<N; i++){
            scanf("%d", &cervena);
            scanf("%d", &zelena);
            scanf("%d", &modra);

            minCN = cervena + min(minZ, minM);
            minZN = zelena + min(minC, minM);
            minMN = modra + min(minC, minZ);

            minC = minCN;
            minZ = minZN;
            minM = minMN;

        }
        printf("%d\n", min(min(minC,minZ),min(minZ,minM)));
    }
    return 0;
}
