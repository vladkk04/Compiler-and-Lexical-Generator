#include "regex.h"

bool regex(char *str)
{
    int i = 0;
    int lenght = strlen(str);

    int countA;

    bool returnRegex = false;

    for(int j = 0; j < lenght; j++)
    {
        if(str[j] != 'a' && str[j] != 'b' && str[j] != 'c')
        {
            return false;
        }
    }
    
    if(str[0] == 'a' && lenght == 1)
    {
        return true;
    }
    else if(str[0] == 'a' && lenght > 1)
    {
        return false;
    }
    
    
    if(str[i] == 'b')
    {
        i++;

        while(str[i] == 'c')
        {
            i++;
            returnRegex = true;
        }

        while((str[i] == 'a' || str[i] == 'b') && i <= lenght)
        {
            returnRegex = false;
            
            i++;

            int afterC = (lenght - 1) - i;
            
            if(str[i] == 'c' && afterC > 1 && str[i + 1] == 'a')
            {
                return false;
            }
            else if(str[i] == 'c' && afterC > 0 && str[i + 1] == 'a')
            {
                return true;
            }
            else if(str[i] == 'c' && afterC == 0)
            {
                return true;
            }
        }
    }
    return returnRegex;
}

int main() 
{
    char str[64];
    printf("Enter input string: ");
    scanf("%s", str);
    int lenght = strlen(str);
    printf("Strlen: %d\n", lenght);
    printf("Stav: %d", regex(str));

    return 0;
}
