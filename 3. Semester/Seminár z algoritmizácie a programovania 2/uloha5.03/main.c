#include <stdio.h>
#include <stdlib.h>

void uprav_zlomok(int *citatel, int *menovatel){

    int i;
    //("Citatel = %d\n", citatel);
    //printf("Menovatel = %d\n", menovatel);
    for (i=2; i<=(*citatel)*(*menovatel); i++)
        if (*citatel % i == 0 && *menovatel % i == 0){
            //printf("Citatel = %d, menovatel = %d\n", citatel/i, menovatel/i );
            *citatel = *citatel/i;
            *menovatel = *menovatel/i;
            i--;
        }
}

int main()
{
    int x, y;
    scanf("%d %d", &x, &y);
    uprav_zlomok(&x,&y);
    printf("Zakladny tvar zlomku: %d/%d\n", x, y);
    return 0;
}
