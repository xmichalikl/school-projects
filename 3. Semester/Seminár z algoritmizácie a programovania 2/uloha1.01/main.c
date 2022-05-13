#include <stdio.h>
#include <stdlib.h>

int main()
{
    int N, T;
    int i, j;

    int prvyDen, druhyDen, zisk = 0;

    scanf("%d", &T);

    for (i=0; i<T; i++){
        scanf("%d", &N);
        if (N <= 0){
            printf("zisk %d\n", zisk);
            continue;
        }

        scanf("%d", &prvyDen);

        for (j=0; j<N-1; j++){
            scanf("%d", &druhyDen);

            if (prvyDen < druhyDen){
                zisk = zisk + (druhyDen - prvyDen);
                prvyDen = druhyDen;
                continue;
            }
            else {
                prvyDen = druhyDen;
                continue;
                }
        }
        printf("zisk %d\n", zisk);
        zisk = 0;
    }

    return 0;
}
