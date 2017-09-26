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
> put successors in front of the queue -> stack

### Depth-limited
> depth-first search with depth limit l 

### Iterative deepening
> combine dfs & bfs, Depth-limited 的 l 不断增加

### Bidirectional search
> from start and goal search, to the middle

![Searches](./Searches Comparasion.png)

## Informed  Search
-------------------
### Best First
> insert successors in decreasing order of desirability

#### Greedy search
> 评价函数f(n), 选择最小的f(n)
	> - f(n) = h(n), h(n) -> estimate of cost from n to goal
	> - 距离的话，为n到goal的 straight-line distance  
![Alt text](./Screen Shot 2017-09-24 at 4.46.14 PM.png)


#### A*
> f(n) = g(n) + h(n)，g(n) 为从起点到n的已消耗距离
	> - if admissible, h(n) <= h*(n) where h*(n) is the true cost from n. （预测不可能大于真实情况）
![Alt text](./Screen Shot 2017-09-24 at 4.46.26 PM.png)


### Hill-climbing

### Simulated annealing
> 1. 首先便是初始化工作，设置初始温度（temperature）,获得一个随机的解决方案，并将其设置为当前解决方案（cur_sol）。
2. 如果温度大于0，处理cur_sol（perturb处理，比如随机调换方案中步骤执行顺序等），得到新方案（new_sol）；如果温度小于0，结束算法。
3. 比较当前方案和新方案的消耗（deltaE = cost(new_sol) – cost(cur_sol)）。
4. 如果deltaE小于0则表明新方案比当前方案优秀，将新方案设置为当前方案，降温，则继续执行步骤2。
5. 根据公式`p = exp(-detaE/T)`。计算p将其与以小于1的正随机数比较。如果p大于随机数，正将新方案设置为当前方案，降温继续执行步骤2；如果p小于随机数，则降温直接执行步骤2。
这个流程可以简单的理解：不断获取新方案，和当前比较，如果优于当前方案，取新方案为当前方案；如果劣于当前方案，则给予它一个机会成为当前方案，这个机会的概率取决于当前的温度和它与当前方案之间的差距。


## Game Playing
-------------------

### Minimax
![Alt text](./minimax.png)

1. 没办法cover all the trees时候, cut-off, 按自定义分算 (棋盘上剩的子)
2. a-b pruning
> Same basic idea as minimax, but prune (cut away) branches of the tree that we know will not contain the solution
>  a: Best choice so far for MAX  
>  b: Best choice so far for MIN 
![Alt text](./a-b_minimax.png)

3. for Nondeterministic games: 乘以chance


## Constraint Satisfaction Problems (CSP)
-------------------

### Attributes
- variables
- domain: values, one for each variable
- constraints: over value of variables

### Example
1. map coloring
	- variables: each area
	- domain: colors {}
	- constraints: adjacent regions must have different colors
2. sudoku
	- variables: each square
	- domain: 1-9
	- constraints: each column, each row, and each of the nine 3×3 sub-grids that compose the grid contain all of the digits from 1 to 9 

### Backtracking 
> DFS with one variable assigned per node

1. Tie-breaker first: Most constraining variable
2. AC-3: check if every node is arc-consistent --> **network arc-consistency** or **no solve**  
3. Forward checking: see what legal values left

### Local Search
> min-conflicts heuristic: choose value that violates the fewest constraints == 移动violate最多的


## Tips
<hr>
presentation (state): keep track of / coord

The constraints restrict the domain 
SA is optimal
GA == tsp

admissible heuristic: 自己想个标准:
h(n) == missing numbers/ numbers of div need to be moved/ straight line/ manhattan