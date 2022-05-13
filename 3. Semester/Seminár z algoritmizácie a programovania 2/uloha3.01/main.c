#include <stdio.h>
#include <stdlib.h>

#include <stdio.h>
#include <stdlib.h>

// Funkcia vrati index cisla x vo vzostupne usporiadanej postupnosti cisel.
// Vrati -1 ak sa x v postupnosti nenachadza.
int search(int cisla[], int n, int x){

  int lavy = 0;
	int pravy = n-1;

	while (lavy <= pravy){
      int stred = (lavy + pravy)/2;
      if (cisla[stred] < x)
          lavy = stred + 1;
      else
      if (cisla[stred] > x)
          pravy = stred - 1;
      else
          return stred;
	}
	return -1;
}

// ukazkovy test (spracovanie vstupu)
int main(void)
{
  int *a, n, i;

  // nacitanie n cisel do pola a
  scanf("%d", &n);
  a = malloc(n * sizeof(int));
  for (i = 0; i < n; i++)
    scanf("%d", &a[i]);

  // nacitanie cisel do konca vstupu
  while (scanf("%d", &i) > 0)
    printf("%d\n", search(a, n, i));
  return 0;
}
