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
    assert(regex("a") == 1);
    assert(regex("b") == 0);
    assert(regex("c") == 0);

    assert(regex("bbbb") == 0);
    assert(regex("cccc") == 0);

    assert(regex("bc") == 1);
    assert(regex("bcab") == 0);

    assert(regex("bcabab") == 0);
    assert(regex("bcccccababab") == 0);

    assert(regex("bcabc") == 1);
    assert(regex("bcabca") == 1);
    assert(regex("bcabcaa") == 0);
    assert(regex("bcabcab") == 0);

    printf("All tests passed!\n");
    
    return 0;
}
