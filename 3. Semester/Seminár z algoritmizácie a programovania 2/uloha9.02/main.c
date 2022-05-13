#include <stdio.h>
#include <string.h>
#define MAX 50000

int longestPal(char* str)
{
    int maxLength = 1;
    int start = 0;
    int len = strlen(str);
    int i, low, high;

    for (i = 1; i < len; i++) {

        low = i - 1;
        high = i;

        while (low >= 0 && high < len && str[low] == str[high]) {
            if (high - low + 1 > maxLength) {
                start = low;
                maxLength = high - low + 1;
            }
            low--;
            high++;
        }

        low = i - 1;
        high = i + 1;

        while (low >= 0 && high < len && str[low] == str[high]) {
            if (high - low + 1 > maxLength) {
                start = low;
                maxLength = high - low + 1;
            }
            low--;
            high++;
        }
    }

    return maxLength;
}

int main()
{
    char str[MAX];

    while ( scanf("%s", str) == 1 ){
        if (strlen(str) == MAX){
            return 0;
        }
        printf("%d\n", longestPal(str));
    }

    return 0;
}
