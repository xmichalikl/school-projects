#include <stdio.h>
#include <stdlib.h>

int cifsucet(int x){
    if (x == 0)
        return 0;
    return ( (x%10) + cifsucet( (x-(x%10)) / 10) );
}

int main()
{
    int n, x;
    scanf("%d", &n);
    printf("%d\n", cifsucet(n));
    return 0;
}
