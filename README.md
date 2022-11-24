# InterviewPrep
Various coding questions

# Useful tips
- When you have 2 classes of something (a list w/ some element guaranteed to be > 50% (so 2 classes, the majority element and everything else)) you can use a +1/-1 counting technique to determine a lot.

Ex: Find the majority element. Can track w/ a count + a element variable. @ zero you change the element out. 

Ex: Counting how many"balanced pairings" of 2 items in a String consisting of 2 values: "LLRR...". If L +1, if R -1, when you have it set to zero, you have a matched set.