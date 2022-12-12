# InterviewPrep
Various coding questions

# Useful tips
- When you have 2 classes of something (a list w/ some element guaranteed to be > 50% (so 2 classes, the majority element and everything else)) you can use a +1/-1 counting technique to determine a lot.

Ex: Find the majority element. Can track w/ a count + a element variable. @ zero you change the element out. 

Ex: Counting how many"balanced pairings" of 2 items in a String consisting of 2 values: "LLRR...". If L +1, if R -1, when you have it set to zero, you have a matched set.

- When checking something that has boundaries (like a grid) with recursion, can be more useful to just attempt +/- 1 in all directions and at the start of the called function check whether the boundaries are good:

```python3
def walk_maze(maze:List[List[int]]):
	return recurse(0,0, maze) or recurse(1,0, maze) or recurse(0,1, maze) or recurse(-1,-1, maze)

def recurse(x,y maze:List[List[int]]) -> bool:
	if x > len(maze) or y > len(maze[0]) or x < 0 or y < 0:
		return False
	# impl goes here. 
```