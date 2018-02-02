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

#### A*
> f(n) = g(n) + h(n)，g(n) 为从起点到n的已消耗距离
    > - if admissible, h(n) <= h*(n) where h*(n) is the true cost from n. （预测不可能大于真实情况）


### Hill-climbing

### Simulated annealing
> 1.     首先便是初始化工作，设置初始温度（temperature）,获得一个随机的解决方案，并将其设置为当前解决方案（cur_sol）。
2.     如果温度大于0，处理cur_sol（perturb处理，比如随机调换方案中步骤执行顺序等），得到新方案（new_sol）；如果温度小于0，结束算法。
3.     比较当前方案和新方案的消耗（deltaE = cost(new_sol) – cost(cur_sol)）。
4.     如果deltaE小于0则表明新方案比当前方案优秀，将新方案设置为当前方案，降温，则继续执行步骤2。
5.     根据公式`p = exp(-detaE/T)`。计算p将其与以小于1的正随机数比较。如果p大于随机数，正将新方案设置为当前方案，降温继续执行步骤2；如果p小于随机数，则降温直接执行步骤2。
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
-------------------
presentation (state): keep track of / coord

The constraints restrict the domain 
SA is optimal
GA == tsp

admissible heuristic: 自己想个标准:
h(n) == missing numbers/ numbers of div need to be moved/ straight line/ manhattan




## logic
-------------------
### Entail
KB |= a
(充分 |= 必要)

### Inference
KB |–i a  = sentence a can be derived from KB using procedure i 
- Sound: whenever KB |–i a then KB |= a is true,   does not infer false statements
- Complete: whenever KB |= a then KB |–i a,   derives any sentence that is entailed,  a plan where every precondition is achieved

### Propositional Logic
symbol, syntax, semantic 
> A => B = (¬A) V B
¬(A => B) = A ^ (¬B)
A <==>B = (A => B) ^ (B => A)

We saw that propositional logic is limited because it only makes the ontological commitment that the world consists of facts. 

### First-order logic
proof: 等号左右两边式子推导

CNF: ^ （conjunction） / V (disjunction), 最外面只有^ 且只有一个variable
INF: =>, 任意/存在

#### English to FOL (E, V, ^ , => ... )
KB |= a  <==>  KB => a  , not (KB ^ 非a )
存在用^, 任意用 =>
exactly one cook: ∃!𝑥 isCook(x)


proof
1. model checking
2. inference rules

Ontology: like database relations



### Forward Chaining 
1. acts like a breadth-first search at the top level, with depth-first sub-searches. 
2. sound and complete, complete for horn KB
3. data-driven reasoning

### Backward Chaining
1. a depth-first search: in any knowledge base of realistic size, many search paths will result in failure
2. not complete due to infinite loops. but sound
3. goal-directed reasoning


### prolog
1. using backward chain
2. No Occurs-Check-> traded soundness for efficiency
3. incomplete not sound, no check for infinite recursion

### Horn Form
1. not more than 1 positive sentence
2. Every Horn clause is not a Definite clause


### Atomic Sentence
no variables, only constant



## Logic & Plan 题目
-------------------

### planning schema:
Action(Paint(b, c), 
Precond: Blank(b) ∧ Wet(brush, c)，
Effect: ~ Blank(b) ∧ Color(b, c)) 
- Delete literal： 删precond中的东西

if one effect conflicts other's precond, may need another state...

### resolution:
反证：引用例子: x1/John
or 用已有的互推，推出答案

### convert to CNF:
1. A => B = (¬A) V B : Eliminate ⇒, replace 𝜶 ⇒ 𝜷, with ¬𝜶 ∨ 𝜷
2. Reduce negation
3. 把存在的值用a，b代替 ： ∃𝒙 𝑨(𝒙) ∧ 𝑫(𝒙) = 𝑨(𝑲) ∧ 𝑫(𝑲) = 𝑨(𝑲), 𝑫(𝑲) – using Skolemization Constant (举例)
4. 去掉存在，把存在的值变成 f (x), x关于任意
5. 去掉任意
6. A V (B ^ C) = (A V B) ^ (A V C) -- (Distributive law)

### Backward Chaining：
从结果往回推： 引用例子: {x/USC, y/UCLA}

### Forward Chaining:
结合rules 正推 引用例子
6,7 | NicerCampus(USC, UCLA) | x/USC, y/UCLA

### Inference Rule
And-elimination： 并集中的一个
And-introduction: 合并
Modus Ponens on 左右：顺推
modus tollens on 左右：倒退

### fuzzy logic
and: min


### 判断题：
- Successor-state axioms solve the representational frame problem.
- all things that stay the same from one situation to the next: frame problem
- The completeness theorem states that any sentence entailed by a set of sentences can be proven from that set.
- Soundness of an inference algorithm means that the algorithm doesn’t reach bogus conclusions.
- Entailment can be used to derive true conclusions.
- All sentences in propositional logic can be converted to CNF.
- Some sentences in propositional logic cannot be converted to Horn Form.
- Planning graphs work only for propositional planning problems— ones with no variables.
- Reification represents a category as an object
- tautology is a sentence that is necessary true in all models.
- An upper ontology can be used for knowledge sharing.
- Generalized Modus Ponens is a sound inference rule, not complete, horn form
- POP is sound, complete, and systematic
- Algorithms exist that return YES to every entailed sentence, but no algorithm exists that also returns NO to every nonentailed sentence


## Bayesian
-----------------------------------
Independent: P(a,b)=P(a) * p(b)
p(a|b) * p(b) = p(b|a) * p(a)
p(d|a) = p(b|a) * p(d|b)

- P(B | H+, L-, E+) 
= αP(B, H+, L-, E+) 
= α(P(B, H+, L-, Q+, E+) + P(B, H+, L-, Q-, E+))
= α(<P(B+) P(H+) P(L- | H+) P(Q+ | B+, H+, L-) P(E+ | Q+),
P(B-) P(H+) P(L- | H+) P(Q+ | B-, H+, L-) P(E+ | Q+)> + <P(B+) P(H+) P(L- | H+) P(Q- | B+, H+, L-) P(E+ | Q-), P(B-) P(H+) P(L- | H+) P(Q- | B-, H+, L-) P(E+ | Q-)>)

- P(a|b,c)
= P(a,b,c) / P(b,c)


## Machine Learning
-----------------------------------
### Decision Tree
Information Gain = 总Entropy - (n/m * 此entropy + m-n/m * 此entropy)
split on first: which has highest information gain

### Neural Networks
network output: 正为1，负为0
number of weights w/bias: (input + 1 )* hidden + (hidden + 1) * output
number of weights without bias: 不加1

### Candidate Elimination
negative example & true negative: 找 S 反例
positive example & true positive: 找 G 正例
negative example & false positive： 找 全 正例
positive example & false negative： 找 全 反例


## Reinforcement Learning
-----------------------------------
V = 最大γ 乘积和周围所有比较 (可能加一个reward action的值)
循环的话：V = pγR + (1-p)γV


## 判断题：
- Bayesian network cover every boolean functions
- Naive Bayes is not a linear classifier
- A single perceptron (finite number training steps) cannot compute the XOR function
- supervised learning is labeled
- reinforcement learning: we do not need to know the transition probabilities before we start, not know the utility function
- NPL: Cocke-Younger-Kasami
- incremental strategy: most vars are 0