# City Transportation Network Optimization - MST Analysis

**Assignment 3**: Design and Analysis of Algorithms  
**Topic**: Minimum Spanning Tree Algorithms

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-4.0+-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

---

## Table of Contents

1. [Technology Stack](#technology-stack)
2. [Overview](#overview)
3. [Features](#features)
4. [Project Structure](#project-structure)
5. [Installation & Setup](#installation--setup)
6. [Algorithm Details](#algorithm-details)
7. [Dataset Description](#dataset-description)
8. [Performance Analysis](#performance-analysis)
9. [Conclusion](#conclusion)
10. [References](#references)

---

## Technology Stack

- **Language**: Java 17
- **Build Tool**: Maven 3.8+
- **Dependencies**:
  - Gson 2.10.1 (JSON processing)
  - JUnit 5.10.0 (testing framework)
- **Version Control**: Git
- **IDE**: IntelliJ IDEA
- **Input Data Generator**: ChatGPT

---

## Overview

This project implements and compares **Prim's** and **Kruskal's** algorithms for finding the Minimum Spanning Tree (MST) in weighted undirected graphs. The application is designed to optimize city transportation networks by determining the minimum set of roads that connect all districts with the lowest construction cost.

### Problem Statement

Given a weighted undirected graph representing a city's transportation network where:
- **Vertices** represent districts/locations
- **Edges** represent roads with construction costs (weights)

The goal is to find the **Minimum Spanning Tree (MST)** - a subset of edges that:
1. Connects all vertices
2. Has no cycles
3. Minimizes total construction cost

### Objectives

1. Implement both Prim's and Kruskal's algorithms
2. Compare their performance across different graph sizes and densities
3. Analyze efficiency based on operation count and execution time
4. Determine which algorithm is preferable under different conditions

---

## Features

- ✅ **Prim's Algorithm** - Priority queue-based implementation with O(E log V) complexity
- ✅ **Kruskal's Algorithm** - Union-Find based implementation with O(E log E) complexity
- ✅ **MSF Support** - Both algorithms handle disconnected graphs (Minimum Spanning Forest)
- ✅ **Performance Metrics** - Operation counting, execution time tracking
- ✅ **JSON I/O** - Structured input/output for datasets and results
- ✅ **Comprehensive Testing** - JUnit 5 test suite with 27+ tests covering edge cases
- ✅ **CLI Interface** - Easy-to-use command-line interface with detailed output
- ✅ **Algorithm Comparison** - Side-by-side performance analysis with verification


---

## Project Structure

```
DAA-Assignment3/
├── src/
│   ├── data/
│   │   ├── input.json                               # Input dataset (9 graphs)
│   │   └── output.json                              # Algorithm results (generated)
│   ├── main/
│   │   └── java/
│   │       └── org/
│   │           └── harryfloppa/
│   │               ├── MyApplication.java           # CLI entry point
│   │               ├── algorithm/
│   │               │   ├── PrimAlgorithm.java       # Prim's implementation
│   │               │   └── KruskalAlgorithm.java    # Kruskal's implementation
│   │               ├── model/
│   │               │   ├── Edge.java                # Edge representation
│   │               │   ├── Graph.java               # Graph structure
│   │               │   └── MSTResult.java           # Result container
│   │               └── io/
│   │                   ├── InputReader.java         # JSON input parser
│   │                   └── OutputWriter.java        # JSON output writer
│   └── test/
│       └── java/
│           └── org/
│               └── harryfloppa/
│                   └── algorithm/
│                       ├── PrimAlgorithmTest.java           # Unit tests for Prim's
│                       ├── KruskalAlgorithmTest.java        # Unit tests for Kruskal's
│                       ├── AlgorithmComparisonTest.java     # Comparative tests
│                       └── MSFTest.java                     # MSF support tests
├── pom.xml                                          # Maven configuration
└── README.md                                        # This file
```

---

## Installation & Setup

### Prerequisites

- **Java 17** or higher ([Download](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html))
- **Maven 3.8+** ([Download](https://maven.apache.org/download.cgi))
- **Git** ([Download](https://git-scm.com/downloads))

### Clone Repository

```bash
git clone https://github.com/Garryflop/DAA-Assignment3.git
cd DAA-Assignment3
```

### Build Project

```bash
mvn clean compile
```

### Run Tests

```bash
mvn test
```

Expected output: **All 27+ tests pass**

### Run Application

**Option 1: Using Maven (default files)**
```bash
mvn exec:java -Dexec.mainClass="org.harryfloppa.MyApplication"
```

This will:
- Read from: `src/data/input.json`
- Write to: `src/data/output.json`


**Option 4: In IntelliJ IDEA**
- Open project in IntelliJ
- Navigate to `src/main/java/org/harryfloppa/MyApplication.java`
- Right-click → Run 'MyApplication.main()'

**Note**: The application uses these default file paths:
- Input: `src/data/input.json`
- Output: `src/data/output.json`

---

## Algorithm Details

### Prim's Algorithm

**Approach**: Greedy algorithm that grows the MST one vertex at a time, always selecting the minimum weight edge connecting the MST to a new vertex.

**Implementation Details**:
- Uses `PriorityQueue<EdgeWithNode>` for efficient minimum edge selection
- Maintains `HashSet<String>` for tracking visited vertices
- Starts from arbitrary vertex and expands MST incrementally
- For disconnected graphs, loops through all components (MSF support)

**Time Complexity**: O(E log V) with priority queue  
**Space Complexity**: O(V + E) for adjacency list and visited set

**Key Operations Counted**:
- Priority queue insertions/extractions
- Edge relaxations
- Visited vertex checks

**Best For**: Dense graphs where E ≈ V²

---

### Kruskal's Algorithm

**Approach**: Greedy algorithm that processes edges in ascending order of weight, adding edges that don't create cycles using Union-Find data structure.

**Implementation Details**:
- Sorts all edges by weight: `Collections.sort(edges)`
- Uses Union-Find (Disjoint Set Union) with:
  - **Path compression**: Flattens tree structure during find operations
  - **Union by rank**: Attaches smaller tree to larger tree
- HashMap-based implementation for String vertex identifiers
- Native MSF support (automatically handles multiple components)

**Time Complexity**: O(E log E) due to edge sorting  
**Space Complexity**: O(V + E) for Union-Find structure and edge list

**Key Operations Counted**:
- Edge sorting operations
- Union-Find operations (find + union)
- Cycle detection checks

**Best For**: Sparse graphs where E ≈ V

---

## Dataset Description

The `src/data/input.json` file contains **9 comprehensive test cases** designed to evaluate algorithm performance across different graph sizes and densities:

### Dataset Overview

| Graph ID | Category | Vertices | Edges | Density | Type | Description |
|----------|----------|----------|-------|---------|------|-------------|
| 1 | Small | 4 | 6 | 100% | Complete | Complete graph K₄ - maximum edges |
| 2 | Small | 5 | 7 | 70% | Dense | City districts network |
| 3 | Small | 6 | 8 | 53% | Moderate | Sparse network |
| 4 | Medium | 10 | 15 | 33% | Sparse | Zone network |
| 5 | Medium | 12 | 19 | 29% | Sparse | Hub system |
| 6 | Medium | 15 | 24 | 23% | Moderate | Regional network |
| 7 | Large | 20 | 32 | 17% | Sparse | Metropolitan network |
| 8 | Large | 25 | 40 | 13% | Very Sparse | City grid network |
| 9 | Large | 30 | 56 | 13% | Sparse | Interstate highway system |

**Density Formula**: `(2 × E) / (V × (V - 1))` × 100%

### Dataset Features

**Small Graphs (4–6 vertices)**:
- Purpose: Correctness verification, edge case testing
- Characteristics: High density (53%–100%)
- Use case: Debugging, algorithm validation

**Medium Graphs (10–15 vertices)**:
- Purpose: Balanced performance observation
- Characteristics: Moderate density (23%–33%)
- Use case: Comparing theoretical vs. practical performance

**Large Graphs (20–30 vertices)**:
- Purpose: Scalability testing
- Characteristics: Low density (13%–17%), sparse networks
- Use case: Observing asymptotic behavior, real-world scenarios

**Topology Variety**:
- Complete graphs (K₄)
- Linear chains
- Grid structures
- Hub-and-spoke networks
- Hierarchical tree-like structures

### Input Format

```json
{
  "graphs": [
    {
      "id": 1,
      "description": "Complete graph K4",
      "category": "small",
      "nodes": ["A", "B", "C", "D"],
      "edges": [
        {"from": "A", "to": "B", "weight": 1},
        {"from": "B", "to": "C", "weight": 2},
        {"from": "A", "to": "D", "weight": 3}
      ]
    }
  ]
}
```

---

## Performance Analysis

### 1. Summary of Input Data and Algorithm Results

The following table summarizes the experimental results for all 9 test cases:

| Graph | Vertices | Edges | Density | **Prim's** | | | **Kruskal's** | | | **Winner** |
|-------|----------|-------|---------|------------|------|---------|--------------|------|---------|-----------|
| | | | | Operations | Time (ms) | Cost | Operations | Time (ms) | Cost | (by Time) |
| **1** | 4 | 6 | 100% | 44 | 3.52 | 6 | 65 | 1.26 | 6 | Kruskal |
| **2** | 5 | 7 | 70% | 55 | 0.05 | 16 | 95 | 0.03 | 16 | Kruskal |
| **3** | 6 | 8 | 53% | 64 | 0.04 | 41 | 127 | 0.03 | 41 | Kruskal |
| **4** | 10 | 15 | 33% | 119 | 0.06 | 78 | 248 | 0.04 | 78 | Kruskal |
| **5** | 12 | 19 | 29% | 151 | 0.08 | 77 | 324 | 0.06 | 77 | Kruskal |
| **6** | 15 | 24 | 23% | 192 | 0.10 | 119 | 419 | 0.10 | 119 | **Tie** |
| **7** | 20 | 32 | 17% | 258 | 0.09 | 168 | 584 | 0.12 | 168 | Prim |
| **8** | 25 | 40 | 13% | 326 | 0.11 | 268 | 768 | 0.11 | 268 | **Tie** |
| **9** | 30 | 56 | 13% | 444 | 0.13 | 3330 | 956 | 0.14 | 3330 | Prim |

**Key Observations**:
- ✅ **Correctness**: Both algorithms produce identical MST costs for all graphs
- 📊 **Operation Count**: Prim's consistently performs fewer operations (44–444 vs 65–956)
- ⏱️ **Execution Time**: Results vary by graph size and JVM optimization
- 🏆 **Winner Distribution**: Kruskal (5 wins), Prim (2 wins), Tie (2)


### 2. Comparison: Prim's vs Kruskal's

#### 2.1 Efficiency by Operation Count

**Operation Count Analysis**:

| Graph Size | Prim's Ops | Kruskal's Ops | Difference | Advantage |
|------------|------------|---------------|------------|-----------|
| Small (4-6V) | 44-64 | 65-127 | -21 to -63 | **Prim's** (32-50% fewer) |
| Medium (10-15V) | 119-192 | 248-419 | -129 to -227 | **Prim's** (52-54% fewer) |
| Large (20-30V) | 258-444 | 584-956 | -326 to -512 | **Prim's** (53-54% fewer) |

**Analysis**:
- ✅ **Prim's performs significantly fewer operations** across all graph sizes
- ✅ The efficiency gap **increases with graph size** (from 32% to 54% fewer operations)
- ✅ On the largest graph (30V, 56E), Prim's does **512 fewer operations** than Kruskal's

**Why?**
- Prim's: O(E log V) → For our sparse graphs, log V grows slowly
- Kruskal's: O(E log E) → Edge sorting dominates, plus Union-Find overhead
- Operation counting includes: priority queue ops (Prim's) vs sorting + Union-Find (Kruskal's)

#### 2.2 Performance by Execution Time

**Execution Time Analysis**:

| Graph Category | Prim's Avg Time | Kruskal's Avg Time | Winner |
|----------------|-----------------|---------------------|--------|
| Small (1-3) | 1.20 ms | 0.44 ms | **Kruskal** (63% faster) |
| Medium (4-6) | 0.08 ms | 0.07 ms | **Tie** (negligible diff) |
| Large (7-9) | 0.11 ms | 0.12 ms | **Prim** (8% faster) |

**Detailed Breakdown**:

**Small Graphs (4-6 vertices)**:
- Graph 1: Kruskal **2.79× faster** (3.52ms vs 1.26ms)
- Graph 2: Kruskal **1.67× faster** (0.05ms vs 0.03ms)
- Graph 3: Kruskal **1.33× faster** (0.04ms vs 0.03ms)
- **Winner**: Kruskal (despite doing more operations!)

**Medium Graphs (10-15 vertices)**:
- Graph 4: Kruskal 1.5× faster (0.06ms vs 0.04ms)
- Graph 5: Kruskal 1.33× faster (0.08ms vs 0.06ms)
- Graph 6: **Tie** (0.10ms both)
- **Winner**: Kruskal/Tie (transitional range)

**Large Graphs (20-30 vertices)**:
- Graph 7: Prim **1.33× faster** (0.09ms vs 0.12ms)
- Graph 8: **Tie** (0.11ms both)
- Graph 9: Prim **1.08× faster** (0.13ms vs 0.14ms)
- **Winner**: Prim (asymptotic advantage emerges)

**Analysis**:
- ⚠️ **Small graphs**: Time results are **counterintuitive** - Kruskal is faster despite more operations
- 🔄 **Medium graphs**: Performance converges, differences become negligible
- ✅ **Large graphs**: Prim gains advantage as asymptotic complexity dominates

#### 2.3 Why Small Graph Times Are Misleading

**The Paradox**: On Graph 1, Kruskal does **47.7% more operations** (65 vs 44) but executes **64.2% faster** (1.26ms vs 3.52ms).

**Explanation**:

1. **Data Structure Overhead**:
   - Prim's `PriorityQueue`: Heap operations have higher constant factors
   - Kruskal's `ArrayList.sort()`: Highly optimized native implementation

2. **JVM Optimization**:
   - Small execution times (< 10ms) are in the **"noise zone"**
   - JVM warm-up effects dominate actual algorithmic differences
   - Garbage collection, method compilation affect microsecond-level timing

3. **Constant Factors**:
   - Priority queue: ~10-20 operations per heap operation
   - ArrayList sorting: Optimized Timsort with low constants
   - Union-Find: Simple array operations (despite more total operations)

4. **Scale Matters**:
   - At 4-6 vertices: Constant factors > Asymptotic complexity
   - At 20-30 vertices: Asymptotic complexity starts to matter
   - At 100+ vertices: O(E log V) vs O(E log E) difference becomes clear

**Conclusion**: **Execution time on small graphs is unreliable** for algorithm comparison. **Operation count is a better metric** for understanding algorithmic efficiency.

#### 2.4 Graph Density Impact

**Theoretical Prediction**:
- **Dense graphs** (E ≈ V²): Prim's O(E log V) should excel
- **Sparse graphs** (E ≈ V): Kruskal's O(E log E) should excel

**Our Results** (all graphs are sparse, E ≈ 2V):

| Density | Graph | Prim's Ops | Kruskal's Ops | Prim's Time | Kruskal's Time |
|---------|-------|------------|---------------|-------------|----------------|
| 100% | 1 | 44 | 65 | 3.52ms | 1.26ms |
| 70% | 2 | 55 | 95 | 0.05ms | 0.03ms |
| 53% | 3 | 64 | 127 | 0.04ms | 0.03ms |
| 13% | 8-9 | 326-444 | 768-956 | 0.11-0.13ms | 0.11-0.14ms |

**Observations**:
- Even at **100% density** (complete graph K₄), Prim's does fewer operations
- At **13% density** (sparse large graphs), Prim's advantage is even stronger
- Our dataset is **sparse-dominated** (13%-33% for medium/large graphs)
- This favors Kruskal's theoretically, but Prim's still does fewer operations

**Interpretation**:
- Operation count favors Prim's **regardless of density** in our dataset
- Execution time is inconsistent due to JVM effects and constant factors
- To truly test density impact, would need graphs with E ≈ V²/2 (near-complete graphs)

#### 2.5 Implementation Complexity

| Aspect | Prim's Algorithm | Kruskal's Algorithm |
|--------|------------------|---------------------|
| **Core Data Structure** | PriorityQueue | Union-Find (Disjoint Set) |
| **Implementation Lines** | ~101 lines | ~115 lines |
| **Complexity to Code** | Moderate | Moderate-High |
| **Edge Cases** | Disconnected graphs | Native support |
| **Dependencies** | Java Collections | Custom UnionFind class |
| **Debugging Difficulty** | Easier (sequential) | Harder (Union-Find logic) |
| **Maintenance** | Lower | Higher |

**Code Complexity Breakdown**:

**Prim's Algorithm**:
- ✅ Straightforward priority queue usage
- ✅ Easy to understand: "always pick minimum edge"
- ⚠️ Requires loop for disconnected graphs (MSF)
- ✅ Standard Java Collections (no custom data structures)

**Kruskal's Algorithm**:
- ⚠️ Requires Union-Find implementation (40+ lines)
- ⚠️ Path compression and union by rank add complexity
- ✅ Native disconnected graph support
- ⚠️ HashMap-based parent/rank tracking for String vertices

**Winner**: **Prim's** - simpler conceptually, easier to debug and maintain

### 3. Conclusions: Which Algorithm is Preferable?

#### 3.1 By Graph Density

| Density | Graph Type | Recommended | Reason |
|---------|------------|-------------|--------|
| **High (> 50%)** | Dense, near-complete | **Prim's** | Fewer operations, O(E log V) advantage when E is large |
| **Medium (20-50%)** | Moderate | **Either** | Performance similar, choose based on other factors |
| **Low (< 20%)** | Sparse | **Prim's** | Still fewer operations in practice; theory suggests Kruskal's but data shows otherwise |

**Note**: Our experimental data shows Prim's has operational advantage even on sparse graphs, contradicting pure theoretical analysis. This suggests:
- Priority queue implementation is highly efficient
- Constant factors in Union-Find are higher than expected
- Edge sorting overhead (O(E log E)) is significant

#### 3.2 By Edge Representation

| Representation | Recommended | Reason |
|----------------|-------------|--------|
| **Adjacency List** | **Prim's** | Natural fit for exploring neighbors from current MST |
| **Adjacency Matrix** | **Prim's** | Dense representation benefits dense-graph algorithm |
| **Edge List** | **Kruskal's** | Direct access to all edges for sorting |
| **Implicit/Generated** | **Kruskal's** | Can generate and sort edges on-the-fly |

#### 3.3 By Implementation Complexity

| Requirement | Recommended | Reason |
|-------------|-------------|--------|
| **Quick Prototyping** | **Prim's** | Simpler implementation, standard data structures |
| **Production System** | **Prim's** | Easier maintenance, fewer custom components |
| **Educational Purpose** | **Both** | Prim's easier to teach; Kruskal's teaches Union-Find |
| **Disconnected Graphs** | **Kruskal's** | Native MSF support without extra logic |

#### 3.4 By System Constraints

| Constraint | Recommended | Reason |
|------------|-------------|--------|
| **Memory Limited** | **Prim's** | Can build MST iteratively without storing all edges |
| **Real-time Processing** | **Kruskal's** | Lower execution time on small graphs (< 15 vertices) |
| **Batch Processing** | **Prim's** | Lower operation count reduces CPU cycles over many graphs |
| **Parallel Processing** | **Kruskal's** | Edge sorting and Union-Find are more parallelizable |

#### 3.5 By Graph Size

| Graph Size | Recommended | Winner Characteristic |
|------------|-------------|----------------------|
| **Small (< 10V)** | **Kruskal's** | Faster execution time (JVM optimization favors simpler ops) |
| **Medium (10-20V)** | **Either** | Performance converges, differences negligible |
| **Large (20-50V)** | **Prim's** | Fewer operations, asymptotic advantage emerges |
| **Very Large (> 50V)** | **Prim's** | O(E log V) scales better than O(E log E) on sparse graphs |

---

## Conclusion

### Overall Winner: **Prim's Algorithm** (with caveats)

**Prim's Algorithm is the recommended choice** for this city transportation network optimization problem based on:

✅ **Operational Efficiency**: Consistently performs **32-54% fewer operations** across all graph sizes  
✅ **Scalability**: Advantage grows with graph size (from 21 fewer ops on 4V to 512 fewer ops on 30V)  
✅ **Implementation Simplicity**: Easier to code, debug, and maintain  
✅ **Large Graph Performance**: Faster execution time on graphs with 20+ vertices  
✅ **Theoretical Foundation**: O(E log V) complexity suits sparse transportation networks

**However, Kruskal's Algorithm excels when**:

⚡ Processing **small graphs** (< 10 vertices) where execution speed matters  
🔌 Working with **disconnected graphs** (MSF) - native support without extra logic  
📋 Edges are provided as a **pre-sorted list** or **edge list representation**  
🔄 System supports **parallel processing** (edge sorting and Union-Find parallelize well)

### Practical Recommendation

For a **real-world city transportation network system**:

1. **Use Prim's** as the primary algorithm
   - Most cities have 20+ districts (large graphs)
   - Transportation networks are typically connected
   - Operational efficiency matters for batch processing

2. **Use Kruskal's** for special cases:
   - Small neighborhood sub-networks (< 10 nodes)
   - Multi-region planning (disconnected graphs)
   - When edge data comes pre-sorted

3. **Hybrid Approach** (optimal):
   ```
   if (vertices < 10) → Use Kruskal's
   else if (disconnected) → Use Kruskal's
   else → Use Prim's
   ```

### Key Takeaway

**Operation count is a more reliable metric than execution time** for small graphs due to:
- JVM warm-up effects
- Garbage collection interference
- Method compilation optimization
- Constant factor variations

---

## References

### Academic Sources

1.**Sedgewick, R., & Wayne, K.** (2011). *Algorithms* (4th ed.). Addison-Wesley Professional.  
   ISBN: 978-0321573513  
   Online: [https://algs4.cs.princeton.edu/43mst/](https://algs4.cs.princeton.edu/43mst/)


## License

This project is licensed under the MIT License.

## Acknowledgments

- Course: Design and Analysis of Algorithms
- Made with ❤️ by Harryfloppa