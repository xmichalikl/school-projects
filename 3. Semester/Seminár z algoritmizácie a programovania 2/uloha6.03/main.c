#include <stdio.h>
#include <stdlib.h>

int nacitaj(double *x, double *y){

    return scanf("%lf %lf", &(*x), &(*y)) == 2;
}

int main()
{
    double x, y;

    if (!nacitaj(&x, &y))
    {
        printf("Zly vstup\n");
        return 0;
    }

    printf("vysledok x = %lf\n", x);
    printf("vysledok y = %lf\n", y);

    return 0;
}
