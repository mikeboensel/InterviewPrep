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

#Assertions make for better code

assert 1==2, "This will clearly fail. We get this message w/ the failure."

## Different character sets
import string
string.ascii_lowercase #"abcdefghijklmnopqrstuvwxyz"
string.ascii_uppercase # "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
string.ascii_letters # "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
string.digits # "0123456789"
string.hexdigits #"0123456789abcdefABCDEF"
string.whitespace #' \t\n\r\x0b\x0c'

# Can also get int values for character ranges via Ordinals
lower_case_a = ord('a')
lower_case_z = ord('z') # So character set is all #s between them

# Or can do comparisons
c = 1
if 'a' <= c <= 'z':
    pass

## To duplicate a list (vs have a pointer to) (Can be very helpful to find things like min/max + index of that value via a sort)
r = [1,2,3]
v = list(v)
v.sort()
(v[0], r.index(v) ) #(minVal, indexItOccurredAt)

## Type hinting
from typing import List, Tuple

# Bools use capitals (True, False)

# Insane matrix unpacking (TODO:Figure this out)

def luckyNumbers (self, matrix: List[List[int]]) -> List[int]:
    mi = [min(row) for row in matrix]
    mx = [max(col) for col in zip(*matrix)]
    return [cell for i, row in enumerate(matrix) for j, cell in enumerate(row) if mi[i] == mx[j]]

# Some objects have a sort on them, but a safe bet is `sorted`
sorted("bac") # Strings don't have a sort()
sorted([3,1,2]) #Array does (could do ([3,1,2]).sort() ), but why learn cornercase methods?

# Sets/ Counter can't be used as dictionary keys. Can instead use FrozenSet
a = "abccc"
c = Counter(a)
f = frozenset(c.items())
m = {}
m[f] = a