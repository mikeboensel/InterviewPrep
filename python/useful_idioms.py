x = "0111"
# Parse binary string to Int
x_int = int(x,2)

# Int back to binary string (Left padded out by Zeroes to 32 places)
print("{:>032b}".format(x))

#Maps have useful ways of getting items(), values(), and keys()
# Can apply filter() or map() to them
map(lambda x: x//2, {'a':1}.items())

# If wanting to work from the back of an array, string, etc. (Ex: when adding numbers represented as strings - "1234")
# Can save the hassle of having bounds checks, iterator variable, etc. by treating as a Q. Simpler than reversing
s = "1234"
l = list(s) # String doesn't have pop() method, so convert
while len(l): # Can get cute as 0 is considered Falsey, non-zero Truthy (giving us logic of len(l) != 0)
    l.pop()


# Conditional IF assignment

n = 123 if s == "123" else 0

# Counting instances of an element in a list
from collections import Counter
from typing import Dict
z:Dict[int, int] = Counter([1,2,3,3,3])

for x in z.items(): # Iterating over the Counter
    print(x)


#REGEX
##Useful regexes \w+ - word characters (alphanumeric + _)
## Non-word characters \W+

import re

split = re.split("[ ]+", "The   BLUE dog")

# Looping over things with index + value
for i,v in enumerate("abc"):
    pass

res = re.findall(r"\w+", "This will take out: punctionation!")