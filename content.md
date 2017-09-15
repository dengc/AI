# AI

@(IT Studies)


-------------------

[TOC]

## Intro
-------------------

### Defination
><b>Think & act (behavior) like human/rationally

* Science - knowledge
* Engineering - problem solving


### Characteristics
1. Accessible: know the state
2. Deterministic: know how to solve problem
3. Episodic: depend on previous episode
4. Static: dynamic if the environment changes during deliberation 
5. Discrete vs. continuous: Chess vs. driving 
6. Hostile 


### Intelligent Agent
![ntelligent Agent.png](./Intelligent Agent.png)


## Uninformed  Search
-------------------

### Breadth-first
> put successors at the end of queue

### Uniform-cost
>insert in order of increasing path cost
* min cost first, then G

### Depth-first
> put successors in front of the queue

### Depth-limited
> depth-first search with depth limit l 

### Iterative deepening
> combine dfs & bfs, Depth-limited 的 l 不断增加

![Searches](./Searches Comparasion.png)

## Informed  Search
-------------------
### Best first
> 评价函数f(n), 选择最小的f(n)
	> - f(n) = h(n), h(n) -> estimate of cost from n to goal
	> - 距离的话，为n到goal的 straight-line distance  

### A*
> f(n) = g(n) + h(n)，g(n) 为从起点到n的已消耗距离
	> - if admissible, h(n) <= h*(n) where h*(n) is the true cost from n. （预测不可能大于真实情况）

Heuristics
Hill-climbing
Simulated annealing