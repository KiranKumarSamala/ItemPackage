# Package Challenge

Goal is to determine which things to put into the package so that the total weight is less than or equal to the package limit and the total cost is as large as possible.

---

## Introduction
You want to send your friend a package with different things.
Each thing you put inside the package has such parameters as index number, weight and cost. The package has a weight limit. 

Constraints:
- You would prefer to send a package which weighs less in case there is more than one package with the same price.
- The sum of item weight should be less than package weight.
- Maximum weight of an item should be less than or equal to 100.
- Maximum cost of and item should be less than or equal to 100.
- There might only up to 15 items to select the sequence.

---

##Approach 
TDD approach is followed 

## Algorith
Backtracking with Memoization
 
## Design Pattern
 * Inversion of control / Dependency Injection -- PackingService and test cases 
 * Single-responsiblity Principle

  