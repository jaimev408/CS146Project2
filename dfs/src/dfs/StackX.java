package dfs;

import java.util.Stack;

class Vertex
{
	public int label; // label (e.g. ‘A’)
	public boolean wasVisited;
	public boolean top;
	public boolean left;
	public boolean right;
	public boolean bottom;
	
	// ------------------------------------------------------------
	public Vertex(int label) // constructor
	{
		this.label = label;
		wasVisited = false;
		top = true;
		left = true;
		right = true;
		bottom = true;
		
	}
	// ------------------------------------------------------------
} // end class Vertex
////////////////////////////////////////////////////////////////

class Graph
{
	private int maxVerts;
	private Vertex vertexList[]; // list of vertices
	private int adjMat[][]; // adjacency matrix
	private int nVerts; // current number of vertices
	private Stack theStack;
	
	// -----------------------------------------------------------
	public Graph(int squareDimensions) // constructor
	{
		maxVerts = (squareDimensions*squareDimensions);
		vertexList = new Vertex[maxVerts];
		// adjacency matrix
		adjMat = new int[maxVerts][maxVerts];
		nVerts = 0;
		
		//Make the Vertices
		setVertices();
		setAdjMat();
		
		theStack = new Stack();
	}
	// end constructor
	
	public void displayMaze()
	{
		String[] maze = new String[(int)(Math.sqrt(maxVerts) * 2) + 1];
		int mazeRowCounter = 0;
		
		for (int i = 0; i < maxVerts; i++)
		{
			if (i % Math.sqrt(maxVerts) == 0 && i != 0 )
			{
				maze[mazeRowCounter] += "+";
				
				mazeRowCounter += 2;
			}
			
			if (vertexList[i].top)
			{
				if (maze[mazeRowCounter] == null)
				{
					maze[mazeRowCounter] = "+-";
				}
				else
				{
					maze[mazeRowCounter] += "+-";
				}
			}
			
			else 
			{
				if (maze[mazeRowCounter] == null)
				{
					maze[mazeRowCounter] = "+ ";
				}
				else
				{
					maze[mazeRowCounter] += "+ ";
				}
			}
			
			if (vertexList[i].left)
			{
				if (maze[mazeRowCounter+1] == null)
				{
					maze[mazeRowCounter+1] = "| ";
				}
				else
				{
					maze[mazeRowCounter+1] += "| ";
				}
			}
		}
		
		maze[mazeRowCounter] += "+";
		
//		for (int i = 0; i < maxVerts; i++)
//		{
//			
//			//test if vertex is in top row
//			if (i - Math.sqrt(maxVerts) < 0)
//			{
//				if (maze[0] == null)
//				{
//					maze[0] = "+-";
//				}
//				else
//				{
//					maze[0] += "+-";
//				}
//				
//			}
//			else if (i - Math.sqrt(maxVerts) == 0)
//			{
//				maze[0] += "+";
//			}
//			
//			if (i % Math.sqrt(maxVerts) == 0)
//			{
//				maze[i % 3]
//			}
//			
//		}
		
		for (String s : maze)
		{
			System.out.println(s);
		}
		
		
	}
	
	//Return the current number of Vertices
	public int getCurrentVerts()
	{
		return nVerts;
	}
	
	public int getVertexListSize()
	{
		return vertexList.length;
	}

	public void displayVertex(int v)
	{
		System.out.print(vertexList[v].label);
	}
	
	private void setVertices()
	{
		 for (int i = 0; i < maxVerts; i++)
		 {
			 vertexList[nVerts++] = new Vertex(i);
		 }
	}
		
	private void setAdjMat()
	{
		for (int m = 0; m <= maxVerts-1; m++)
		{
			for (int n = 0; n <= maxVerts-1; n++)
			{
				if (m == n)
				{
					adjMat[m][n] = 0;
				}
				else if ((n == m + 1) && ((m+1) % Math.sqrt(maxVerts) != 0))
				{
					adjMat[m][n] = 1;
				}
				else if (n == m + Math.sqrt(maxVerts))
				{
					adjMat[m][n] = 1;
				}
				else if ((n == m - 1) && (m % Math.sqrt(maxVerts) != 0 ))
				{
					adjMat[m][n] = 1;
				}
				else if (n == m - Math.sqrt(maxVerts))
				{
					adjMat[m][n] = 1;
				}
				else
				{
					adjMat[m][n] = 0;
				}
			}
		}
	}
	
	public int[][] getAdjMat()
	{
		return adjMat;
	}
		

	public void displayVertices()
	{
		
		for (Vertex x : vertexList)
		{
			System.out.print(x.label);
			System.out.print("\t");
			
			if ((x.label+1) % Math.sqrt(maxVerts) == 0 && x.label != 0)
			{
				System.out.println("");
			}
		}

	}		
	
	public void displayAdjList()
	{
		int counter = 0;
		
		for (int[] a : adjMat)
		{
			for (int b : a)
			{
				System.out.print(b);
				counter++;
				
				if (counter % maxVerts == 0)
				{
					System.out.println("");
				}
			}
		}
	}

	public void dfs() 											// depth-first search
	{ 															// begin at vertex 0
		vertexList[0].wasVisited = true; 						// mark it
		displayVertex(0); 										// display it	
		theStack.push(0); 										// push it
		while( !theStack.isEmpty() ) 							// until stack empty,
		{
			// get an unvisited vertex adjacent to stack top
			int v = getAdjUnvisitedVertex( (int) theStack.peek() );
			if(v == -1) // if no such vertex,
				theStack.pop();
			else // if it exists,
			{
				vertexList[v].wasVisited = true; // mark it
				displayVertex(v); // display it
				theStack.push(v); // push it
			}
		} // end while
		// stack is empty, so we’re done
		for(int j=0; j<nVerts; j++) // reset flags
			vertexList[j].wasVisited = false;
	} 
	// end dfs
	// ------------------------------------------------------------
	// returns an unvisited vertex adj to v
		
	public int getAdjUnvisitedVertex(int v)
	{
		for(int j=0; j<nVerts; j++)
			if(adjMat[v][j]==1 && vertexList[j].wasVisited==false)
				return j;
		return -1;
	} 
	
	public static void adjListTester(int r)
	{
		int size = r*r;
		int[][] myArray = new int[size][size];
		
		for (int m = 0; m <= size-1; m++)
		{
			for (int n = 0; n <= size-1; n++)
			{
				if (m == n)
				{
					myArray[m][n] = 0;
				}
				else if ((n == m + 1) && ((m+1) % r != 0))
				{
					myArray[m][n] = 1;
				}
				else if (n == m + r)
				{
					myArray[m][n] = 1;
				}
				else if ((n == m - 1) && (m % r != 0 ))
				{
					myArray[m][n] = 1;
				}
				else if (n == m - r)
				{
					myArray[m][n] = 1;
				}
				else
				{
					myArray[m][n] = 0;
				}
			}
		}
		
		int counter = 0;
		
		for (int[] a : myArray)
		{
			for (int b : a)
			{
				System.out.print(b);
				counter++;
				
				if (counter % size == 0)
				{
					System.out.println("");
				}
			}
		}
	}
		
	// end getAdjUnvisitedVertex()
	// ------------------------------------------------------------
}
// end class Graph
////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////

class DFSApp
{
	public static void main(String[] args)
	{
		Graph theGraph = new Graph(3);

		System.out.println(theGraph.getCurrentVerts());
		
		System.out.println("");
		theGraph.displayVertices();
		
		System.out.println("");
		theGraph.displayAdjList();
		
		System.out.println("");
		
		Graph.adjListTester(3);
		
		System.out.println("");
		
		theGraph.dfs();
		
		System.out.println("");
		System.out.println("");
		
		theGraph.displayMaze();
		
	} // end main()
} // end class DFSApp
	////////////////////////////////////////////////////////////////
