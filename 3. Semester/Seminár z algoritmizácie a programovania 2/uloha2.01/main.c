#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

int main()
{
    int N, i, max = 0;
    int *postup;
    bool *hracky;

    scanf("%d", &N);

    postup = (int*)malloc(N * sizeof(int));

    for (i=0; i<N; i++){
        scanf("%d", &postup[i]);
        if (max < postup[i])
            max = postup[i];
    }

    hracky = (bool*)calloc(max+1 , sizeof(bool));

    for (i=0; i<N; i++){
        if (hracky[postup[i]] == false)
            hracky[postup[i]] = true;
    }

    /*for (i=0; i<=max; i++)
        printf("%d ", hracky[i]);*/

    for (i=N-1; i>=0; i--){
        //printf("%d ", postup[i]);
        if (hracky[postup[i]] == true){
            hracky[postup[i]] = false;
            printf("%d ", postup[i]);
        }
    }

    free(postup);
    free(hracky);

    return 0;
}
